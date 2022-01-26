package org.alexwoo.phoneapi.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "phone_numbers")
@IdClass(PhoneNumbersId.class)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PhoneNumbers {

    @Id
    private String customerId;
    @Id
    private String phoneNumber;
    private String status;
}
