package org.alexwoo.phoneapi.handler;

import org.alexwoo.phoneapi.entrypoint.model.ActivateRequest;
import org.alexwoo.phoneapi.repository.PhoneNumbersRepo;
import org.alexwoo.phoneapi.repository.entity.PhoneNumbers;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings
class PhoneNumbersHandlerTest {

    @Captor
    ArgumentCaptor<PhoneNumbers> phoneNumbersCaptor;

    @Mock
    private PhoneNumbersRepo repo;

    @InjectMocks
    private PhoneNumbersHandler handler;

    @Test
    void whenGetAllNumbersWithEmptyRepo__ThenReturnEmptyList() {

        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<String> result = handler.getAllNumbers();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void whenGetAllNumbersWithExistingNumbers__ThenReturnListOfNumbers() {

        PhoneNumbers test1 = new PhoneNumbers().setPhoneNumber("test1").setCustomerId("cust1").setStatus("testStatus1");
        PhoneNumbers test2 = new PhoneNumbers().setPhoneNumber("test2").setCustomerId("cust2").setStatus("testStatus2");
        PhoneNumbers test3 = new PhoneNumbers().setPhoneNumber("test3").setCustomerId("cust3").setStatus("testStatus3");

        when(repo.findAll()).thenReturn(Arrays.asList(test1, test2, test3));

        List<String> result = handler.getAllNumbers();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("test1"));
        assertTrue(result.contains("test2"));
        assertTrue(result.contains("test3"));
    }

    @Test
    void whenGetAllNumbersThrowsException__ThenThrow() {

        when(repo.findAll()).thenThrow(HibernateException.class);

        assertThrows(HibernateException.class, ()-> handler.getAllNumbers());
    }


    @Test
    void whenGetNumbersWithNoResult__ThenReturnEmptyList() {

        when(repo.findByCustomerId(any())).thenReturn(Collections.emptyList());

        List<String> result = handler.getNumbers("test");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void whenGetNumbersWithExistingNumbers__ThenReturnListOfNumbers() {

        PhoneNumbers test1 = new PhoneNumbers().setPhoneNumber("test1").setCustomerId("cust1").setStatus("testStatus1");
        PhoneNumbers test2 = new PhoneNumbers().setPhoneNumber("test2").setCustomerId("cust2").setStatus("testStatus2");
        PhoneNumbers test3 = new PhoneNumbers().setPhoneNumber("test3").setCustomerId("cust3").setStatus("testStatus3");

        when(repo.findByCustomerId(any())).thenReturn(Arrays.asList(test1, test2, test3));

        List<String> result = handler.getNumbers("test");

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("test1"));
        assertTrue(result.contains("test2"));
        assertTrue(result.contains("test3"));
    }

    @Test
    void whenGetNumbersThrowsException__ThenThrow() {

        when(repo.findByCustomerId(any())).thenThrow(HibernateException.class);

        assertThrows(HibernateException.class, ()-> handler.getNumbers("test"));
    }

    @Test
    void whenActivateNumber__ThenActivates() {
        ActivateRequest activateRequest = new ActivateRequest().setCustomerId("test1").setPhoneNumber("test2");
        PhoneNumbers phoneNumber = new PhoneNumbers().setStatus(PhoneNumberStatus.inactive.name());
        when(repo.findByCustomerIdAndPhoneNumber(activateRequest.getCustomerId(), activateRequest.getPhoneNumber()))
                .thenReturn(phoneNumber);

        handler.activateNumber(activateRequest);

        verify(repo).save(phoneNumbersCaptor.capture());
        assertEquals(PhoneNumberStatus.active.name(), phoneNumbersCaptor.getValue().getStatus());

    }

    @Test
    void whenActivateNumberNotExists__ThenThrowNotFound() {
        ActivateRequest activateRequest = new ActivateRequest().setCustomerId("test1").setPhoneNumber("test2");
        when(repo.findByCustomerIdAndPhoneNumber(activateRequest.getCustomerId(), activateRequest.getPhoneNumber()))
                .thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> handler.activateNumber(activateRequest));
    }

    @Test
    void whenActivateNumberFailsToSave__ThenThrowException() {
        ActivateRequest activateRequest = new ActivateRequest().setCustomerId("test1").setPhoneNumber("test2");
        PhoneNumbers phoneNumber = new PhoneNumbers().setStatus(PhoneNumberStatus.inactive.name());
        when(repo.findByCustomerIdAndPhoneNumber(activateRequest.getCustomerId(), activateRequest.getPhoneNumber()))
                .thenReturn(phoneNumber);
        when(repo.save(any())).thenThrow(HibernateException.class);

        assertThrows(HibernateException.class, () -> handler.activateNumber(activateRequest));
    }
}