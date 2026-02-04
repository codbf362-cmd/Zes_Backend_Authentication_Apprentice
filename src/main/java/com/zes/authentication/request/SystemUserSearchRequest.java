package com.zes.authentication.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SystemUserSearchRequest
{
    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    private String keyword;
    private String authority;
    private String companyCode;
}