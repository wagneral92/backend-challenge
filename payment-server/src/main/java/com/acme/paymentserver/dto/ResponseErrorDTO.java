package com.acme.paymentserver.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDTO implements Serializable {

    private List<String> erros;
}