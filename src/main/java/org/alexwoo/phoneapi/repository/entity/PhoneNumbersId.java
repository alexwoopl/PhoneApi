package org.alexwoo.phoneapi.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PhoneNumbersId implements Serializable {

    @Id
    private String customerId;
    @Id
    private String phoneNumber;
}
