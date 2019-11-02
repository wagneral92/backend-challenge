package com.acme.orderserver.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDTO implements Serializable {

    private List<String> erros;
}
