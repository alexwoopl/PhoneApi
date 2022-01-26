package org.alexwoo.phoneapi.handler;

import lombok.AllArgsConstructor;
import org.alexwoo.phoneapi.entrypoint.model.ActivateRequest;
import org.alexwoo.phoneapi.repository.PhoneNumbersRepo;
import org.alexwoo.phoneapi.repository.entity.PhoneNumbers;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@AllArgsConstructor
public class PhoneNumbersHandler {

    private PhoneNumbersRepo repo;

    @NotNull
    public List<String> getAllNumbers() {
        return StreamSupport.stream(repo.findAll().spliterator(),false)
                                                    .map(PhoneNumbers::getPhoneNumber)
                                                    .collect(Collectors.toList());
    }

    @NotNull
    public List<String> getNumbers(String customerId) {
        return repo.findByCustomerId(customerId).stream()
                .map(PhoneNumbers::getPhoneNumber)
                .collect(Collectors.toList());
    }

    public void activateNumber(ActivateRequest activateRequest) {
        PhoneNumbers phoneNumber = repo.findByCustomerIdAndPhoneNumber(activateRequest.getCustomerId(), activateRequest.getPhoneNumber());
        if (null == phoneNumber) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer/PhoneNumber combination not found.");
        }
        phoneNumber.setStatus(PhoneNumberStatus.active.name());
        repo.save(phoneNumber);
    }
}
