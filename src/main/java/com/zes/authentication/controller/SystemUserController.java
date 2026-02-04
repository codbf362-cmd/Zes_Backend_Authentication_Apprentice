package com.zes.authentication.controller;

import com.zes.authentication.request.SystemUserSignUpRequest;
import com.zes.authentication.request.SystemUserSearchRequest;
import com.zes.authentication.request.SystemUserUpdateRequest;
import com.zes.authentication.request.SystemUserDeleteRequest;
import com.zes.authentication.request.SystemUserDetailRequest;
import com.zes.authentication.service.SystemUserService;
import com.zes.authentication.service.ZES_returnService;
import com.zes.authentication.util.ZES_Enum;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/system-user")
public class SystemUserController
{
    private final ZES_returnService ZES_gv_returnService;
    private final SystemUserService ZES_gv_systemUserService;

    @PostConstruct
    public void init()
    {
        System.out.println("SystemUserController LOADED");
    }

    @PostMapping("/signup")
    public JSONObject ZES_systemUserSignUp(
            @RequestBody @Valid SystemUserSignUpRequest request, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return ZES_gv_returnService.ZES_returnToFormat(ZES_Enum.ZES_VALID_ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }

        return ZES_gv_systemUserService.ZES_systemUserSignUp(request);
    }

    @GetMapping("/list")
    public JSONObject ZES_systemUserList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String companyCode)
    {
        return ZES_gv_systemUserService.ZES_systemUserList(page, size, companyCode);
    }

    @PostMapping("/search")
    public JSONObject ZES_systemUserSearch(@RequestBody @Valid SystemUserSearchRequest request, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return ZES_gv_returnService.ZES_returnToFormat(ZES_Enum.ZES_VALID_ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }
        return ZES_gv_systemUserService.ZES_systemUserSearch(request);
    }

    @PostMapping("/update")
    public JSONObject ZES_systemUserUpdate(@RequestBody @Valid SystemUserUpdateRequest request, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_VALID_ERROR,
                    bindingResult.getAllErrors().get(0).getDefaultMessage(),
                    null);
        }
        return ZES_gv_systemUserService.ZES_SystemUserUpdate(request);
    }

    @PostMapping("/delete")
    public JSONObject ZES_systemUserDelete(
            @RequestBody @Valid SystemUserDeleteRequest request, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return ZES_gv_returnService.ZES_returnToFormat(ZES_Enum.ZES_VALID_ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage(), null
            );
        }
        return ZES_gv_systemUserService.ZES_systemUserDelete(request);
    }

    @PostMapping("/detail")
    public JSONObject ZES_systemUserDetail(
            @RequestBody @Valid SystemUserDetailRequest request, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return ZES_gv_returnService.ZES_returnToFormat(ZES_Enum.ZES_VALID_ERROR, bindingResult.getAllErrors().get(0).getDefaultMessage(), null
            );
        }
        return ZES_gv_systemUserService.ZES_systemUserDetail(request);
    }
}



