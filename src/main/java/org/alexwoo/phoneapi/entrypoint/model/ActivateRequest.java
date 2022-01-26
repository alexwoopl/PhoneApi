package org.alexwoo.phoneapi.entrypoint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.alexwoo.phoneapi.validate.GUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ActivateRequest {

    @GUID
    @NotNull
    private String customerId;
    @NotNull
    @Pattern(regexp="([0-9]| |-|\\+|\\(|\\))+") //^ $
    private String phoneNumber;

}