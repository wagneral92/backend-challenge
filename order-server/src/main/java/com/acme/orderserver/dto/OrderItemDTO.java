package com.acme.orderserver.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO implements Serializable {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @NotNull
    private BigDecimal unitPrice;

    @NotNull
    private int quantity;
}
