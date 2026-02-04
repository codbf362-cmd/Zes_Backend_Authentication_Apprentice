package com.zes.authentication.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SystemUserDetailRequest {

    @NotBlank
    private String id;
}
