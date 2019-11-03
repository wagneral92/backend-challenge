package com.acme.paymentserver.serviceAgents.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {

    private Long Id;

    private String description;
}
