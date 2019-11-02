package com.acme.paymentserver.serviceAgents.model;

import lombok.Data;

@Data
public class Order {

    private Long id;

    private Long storeId;

    private String address;
}
