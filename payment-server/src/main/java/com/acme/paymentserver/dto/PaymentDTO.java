package com.acme.paymentserver.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO implements Serializable {

    private Long id;

    @NotNull
    private Long orderId;

    @Size(min = 14, max = 16)
    @NotBlank
    private String credCardNumber;
}
