package com.acme.paymentserver.serviceAgents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private Long id;

    private Long storeId;

    private String address;

    private LocalDateTime confirmationDate;

    private List<OrderItem> items;
}
