package org.alexwoo.phoneapi.entrypoint;

import lombok.AllArgsConstructor;
import org.alexwoo.phoneapi.entrypoint.model.ActivateRequest;
import org.alexwoo.phoneapi.handler.PhoneNumbersHandler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Validated
public class ActivateNumbersApi extends BaseEntryPoint {

    private PhoneNumbersHandler phoneNumbersHandler;


    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void activateCustomerNumber(@RequestBody @Valid ActivateRequest activateRequest){
        phoneNumbersHandler.activateNumber(activateRequest);
    }



}
