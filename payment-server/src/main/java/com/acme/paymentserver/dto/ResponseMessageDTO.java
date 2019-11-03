package com.acme.paymentserver.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessageDTO implements Serializable {

    private String message;
}
