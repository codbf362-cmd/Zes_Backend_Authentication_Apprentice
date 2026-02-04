package com.zes.authentication.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SystemUserUpdateRequest {

    @NotBlank
    private String id;
    private String password;
    private String userName;
    private String userPosition;
    private String phoneNumber;
    private String email;
    private String profileImage;
    private String companyCode;
}