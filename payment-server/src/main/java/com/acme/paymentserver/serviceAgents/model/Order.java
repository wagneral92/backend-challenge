package com.acme.paymentserver.serviceAgents.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private Long id;

    private Long storeId;

    private String address;

    @JsonProperty("confirmationDate")
    private LocalDateTime confirmationDate;

    private List<OrderItem> items;
}
