package com.acme.paymentserver.serviceAgents.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {

    private Long Id;

    private String description;
}
