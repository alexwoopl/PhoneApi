package org.alexwoo.phoneapi;

import org.alexwoo.phoneapi.entrypoint.model.ActivateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts =  "/populate-db.sql")
@Sql(scripts = "/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
class PhoneApiApplicationSmokeTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {

	}

	/**
	Note to reviewers - These are lightweight smoke tests just to ensure the program is running and the API's single main function is working.
	They're not intended to be comprehensive as the unit tests should cover it.

	Usually I would not smoke/end to end test within the codebase. I would run a test suite on a "live" test environment.
	 **/

	@Test
	void whenGetAllNumbersIsCalled__ThenReturnsAllNumbersInDB() {
		ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("http://localhost:"+port+"/getAllNumbers", List.class);

		List<String> result = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertNotNull(result);
		assertEquals(6, result.size());
		assertTrue(result.contains("1234567890"));
		assertTrue(result.contains("983475983475"));
		assertTrue(result.contains("456784554"));
		assertTrue(result.contains("(84)4563456345634563456354"));
		assertTrue(result.contains("6478434"));

		assertNotEquals(result.indexOf("1234567890"), result.lastIndexOf("1234567890"));
	}

	@Test
	void whenGetAllCustomerNumbersIsCalled__ThenReturnsCustomersNumbers() {
		ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("http://localhost:"+port+"/getAllNumbers/b15ac5e6-d050-4a98-87e3-afc256fd3ff0", List.class);

		List<String> result = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		assertEquals(result.size(), 1);
		assertEquals(result.get(0), "(84)4563456345634563456354");
	}

	@Test
	void whenGetAllCustomerNumbersWithBadInput__Then400() {
		ResponseEntity<List> responseEntity =  this.restTemplate.getForEntity("http://localhost:"+port+"/getAllNumbers/NotAGuid", List.class);

		List<String> result = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		assertEquals("getAllCustomerNumbers.customerId: Invalid customerId given.", result.get(0));
	}

	@Test
	void whenPathNotFound__Then404() {
		ResponseEntity<String> responseEntity =  this.restTemplate.getForEntity("http://localhost:"+port+"/thisIsNotReal", String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	void whenActivateNumberIsCalled__ThenNumberIsActivated() {

		ActivateRequest payload = new ActivateRequest().setCustomerId("b15ac5e6-d050-4a98-87e3-afc256fd3ff0").setPhoneNumber("(84)4563456345634563456354");
		ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:"+port+"/activate", payload, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void whenActivateNumberIsCalledWithBadInput__Then400() {

		ActivateRequest payload = new ActivateRequest().setCustomerId("notaguid").setPhoneNumber("badnumber");
		ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:"+port+"/activate", payload, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}


}
