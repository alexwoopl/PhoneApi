package org.alexwoo.phoneapi.entrypoint;

import lombok.AllArgsConstructor;
import org.alexwoo.phoneapi.handler.PhoneNumbersHandler;
import org.alexwoo.phoneapi.validate.GUID;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
public class AllNumbersApi extends BaseEntryPoint {

    private PhoneNumbersHandler phoneNumbersHandler;

    @GetMapping("/getAllNumbers")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllNumbers(){
        return phoneNumbersHandler.getAllNumbers();
    }


    @GetMapping("/getAllNumbers/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public List<String> getAllCustomerNumbers(@PathVariable("customerId") @GUID String customerId){
        return phoneNumbersHandler.getNumbers(customerId);
    }


}
