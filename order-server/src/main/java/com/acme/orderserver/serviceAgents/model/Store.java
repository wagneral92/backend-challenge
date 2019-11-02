package com.acme.orderserver.serviceAgents.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Store implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;
}
