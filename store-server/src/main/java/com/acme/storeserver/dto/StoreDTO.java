package com.acme.storeserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NotNull
public class StoreDTO implements Serializable {

    private Long Id;

    @NotBlank
    @Size(min = 2, max = 200)
    private String name;

    @NotNull
    @Size(min = 2, max = 255)
    private String address;
}
