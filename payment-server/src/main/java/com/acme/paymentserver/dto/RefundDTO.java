package com.acme.paymentserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO implements Serializable {

    @NotNull
    private Long orderId;
    private List<Long> items;
}
