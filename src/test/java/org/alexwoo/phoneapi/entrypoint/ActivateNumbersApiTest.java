package org.alexwoo.phoneapi.entrypoint;

import org.alexwoo.phoneapi.entrypoint.model.ActivateRequest;
import org.alexwoo.phoneapi.handler.PhoneNumbersHandler;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings
class ActivateNumbersApiTest {

    @Mock
    private PhoneNumbersHandler phoneNumbersHandler;

    @InjectMocks
    private ActivateNumbersApi activateNumbersApi;

    @Test
    void whenActivateNumbersApi__ThenActivates() {

        ActivateRequest request = new ActivateRequest();

        activateNumbersApi.activateCustomerNumber(request);

        verify(phoneNumbersHandler, times(1)).activateNumber(request);
    }

    @Test
    void whenActivateNumbersApiHasError__ThenThrow() {
        ActivateRequest request = new ActivateRequest();
        doThrow(HibernateException.class).when(phoneNumbersHandler).activateNumber(request);

        assertThrows(HibernateException.class, () -> activateNumbersApi.activateCustomerNumber(request));
    }
}