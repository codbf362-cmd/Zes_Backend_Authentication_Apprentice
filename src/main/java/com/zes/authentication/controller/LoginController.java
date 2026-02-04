package com.zes.authentication.controller;

import com.zes.authentication.service.SystemUserService;
import com.zes.authentication.service.ZES_returnService;
import com.zes.authentication.service.LoginService;
import com.zes.authentication.util.ZES_Enum;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login")
public class LoginController
{
    private final ZES_returnService ZES_gv_returnService;
    private final SystemUserService ZES_gv_systemUserService;
    private final LoginService ZES_gv_loginService;

    @PostMapping("")
    public JSONObject ZES_login(@RequestBody JSONObject request)
    {
        String id = (String) request.get("id");
        String password = (String) request.get("password");

        if (id == null || id.trim().isEmpty())
        {
            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_VALID_ERROR,
                    "아이디는 필수입니다.",
                    null
            );
        }

        if (password == null || password.trim().isEmpty())
        {
            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_VALID_ERROR,
                    "비밀번호는 필수입니다.",
                    null
            );
        }

        return ZES_gv_loginService.ZES_login(id, password);
    }
}
