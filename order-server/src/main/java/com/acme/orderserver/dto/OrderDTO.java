package com.acme.orderserver.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Long storeId;

    @NotNull
    @Size(min = 2, max = 255)
    private String address;

    private List<OrderItemDTO> items;
}
