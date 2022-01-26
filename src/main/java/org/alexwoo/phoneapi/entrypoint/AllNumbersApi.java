package org.alexwoo.phoneapi.entrypoint;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AllNumbersApi extends BaseEntryPoint {

    private PhoneNumbersHandler phoneNumbersHandler;

    @GetMapping("/getAllNumbers")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllNumbers(){
        log.info("getAllNumbers API called.");
        List<String> allNumbers = phoneNumbersHandler.getAllNumbers();
        log.info("getAllNumbers API returning " + allNumbers.size() + " results");
        return allNumbers;
    }


    @GetMapping("/getAllNumbers/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public List<String> getAllCustomerNumbers(@PathVariable("customerId") @GUID String customerId){
        log.info("getAllCustomerNumbers API called with parameter: " + customerId);
        List<String> numbers = phoneNumbersHandler.getNumbers(customerId);
        log.info("getAllCustomerNumbers API returning " + numbers.size() + " results");
        return numbers;
    }


}
