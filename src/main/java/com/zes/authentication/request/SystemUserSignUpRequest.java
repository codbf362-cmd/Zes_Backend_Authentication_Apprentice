package com.zes.authentication.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SystemUserSignUpRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    private String companyCode;

    @NotBlank
    private String authority;

    @NotBlank
    private String userName;

    private String userPosition;
    private String phoneNumber;
    private String email;
    private String profileImage;
}