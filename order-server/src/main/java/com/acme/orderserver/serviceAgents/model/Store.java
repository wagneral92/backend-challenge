package com.acme.orderserver.serviceAgents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    private Long id;

    private String name;

    private String address;
}
