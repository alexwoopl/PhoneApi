package org.alexwoo.phoneapi.entrypoint;

import org.alexwoo.phoneapi.handler.PhoneNumbersHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings
class AllNumbersApiTest {

    @Mock
    private PhoneNumbersHandler phoneNumbersHandler;

    @InjectMocks
    private AllNumbersApi allNumbersApi;

    @Test
    void whenGetAllNumbersIsEmpty__ThenReturnWithEmptyList() {

        when(phoneNumbersHandler.getAllNumbers()).thenReturn(Collections.emptyList());

        List<String> result = allNumbersApi.getAllNumbers();

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void whenGetAllNumbersIsPopulated__ThenReturnWithList() {
        List<String> testPhoneNumbers = Arrays.asList("test1","test2","test3");
        when(phoneNumbersHandler.getAllNumbers()).thenReturn(testPhoneNumbers);

        List<String> result = allNumbersApi.getAllNumbers();

        assertEquals(testPhoneNumbers, result);
    }

    @Test
    void whenGetAllNumbersHasException__ThenReturnThrowException() {

        when(phoneNumbersHandler.getAllNumbers()).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> allNumbersApi.getAllNumbers());
    }


    @Test
    void whenGetAllCustomerNumbersIsEmpty__ThenReturnWithEmptyList() {
        List<String> testPhoneNumbers = Collections.emptyList();
        when(phoneNumbersHandler.getNumbers(any())).thenReturn(testPhoneNumbers);

        List<String> result = allNumbersApi.getAllCustomerNumbers("test");

        assertEquals(testPhoneNumbers, result);
    }

    @Test
    void whenGetAllCustomerNumbersIsPopulated__ThenReturnWithList() {
        List<String> testPhoneNumbers = Arrays.asList("test1","test2","test3");
        when(phoneNumbersHandler.getNumbers(any())).thenReturn(testPhoneNumbers);

        List<String> result = allNumbersApi.getAllCustomerNumbers("test");

        assertEquals(testPhoneNumbers, result);
    }

    @Test
    void whenGetAllCustomerNumbersHasException__ThenReturnThrowException() {

        when(phoneNumbersHandler.getNumbers(any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> allNumbersApi.getAllCustomerNumbers("test"));
    }


}
