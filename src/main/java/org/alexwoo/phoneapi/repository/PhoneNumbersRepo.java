package org.alexwoo.phoneapi.repository;

import org.alexwoo.phoneapi.repository.entity.PhoneNumbers;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhoneNumbersRepo extends CrudRepository<PhoneNumbers, String> {

    List<PhoneNumbers> findByCustomerId(String customerId);

    PhoneNumbers findByCustomerIdAndPhoneNumber(String customerId, String phoneNumber);

}
