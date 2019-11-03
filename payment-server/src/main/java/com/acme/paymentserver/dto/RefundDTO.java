package com.acme.paymentserver.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO implements Serializable {

    @NotNull
    private Long orderId;
    private List<Long> items;
}
