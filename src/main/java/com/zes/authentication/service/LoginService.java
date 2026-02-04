package com.zes.authentication.service;

import com.zes.authentication.structure.hs.login.HS_loginInfo;
import com.zes.authentication.structure.hs.login.HS_loginInfoRepository;
import com.zes.authentication.util.ZES_Enum;
import com.zes.authentication.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService
{
    private final ZES_returnService ZES_gv_returnService;
    private final HS_loginInfoRepository HS_gv_loginInfoRepo;
    private final BCryptPasswordEncoder ZES_gv_BCryptEncoder;

    @Transactional(readOnly = true)
    public JSONObject ZES_login(String id, String password)
    {
        System.out.println("ZES S/W TEAM => LoginService.ZES_login");
        try
        {
            String ZES_lv_id = (id == null) ? "" : id.trim();

            Optional<HS_loginInfo> ZES_lv_loginOpt =
                    HS_gv_loginInfoRepo.findByIdAndStatement(ZES_lv_id, "active");

            if (!ZES_lv_loginOpt.isPresent())
            {
                return ZES_gv_returnService.ZES_returnToFormat(
                        ZES_Enum.ZES_SERVICE_ERROR,
                        "아이디 또는 비밀번호가 올바르지 않습니다.",
                        null
                );
            }

            HS_loginInfo ZES_lv_login = ZES_lv_loginOpt.get();

            boolean ZES_lv_matched =
                    ZES_gv_BCryptEncoder.matches(password, ZES_lv_login.getPassword());

            if (!ZES_lv_matched)
            {
                return ZES_gv_returnService.ZES_returnToFormat(
                        ZES_Enum.ZES_SERVICE_ERROR,
                        "아이디 또는 비밀번호가 올바르지 않습니다.",
                        null
                );
            }

            JSONObject ZES_lv_data = new JSONObject();
            ZES_lv_data.put("id", ZES_lv_login.getId());
            ZES_lv_data.put("companyCode", ZES_lv_login.getCompanyCode());
            ZES_lv_data.put("authority", ZES_lv_login.getAuthority());

            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SUCCESS,
                    "로그인 성공",
                    ZES_lv_data
            );
        }
        catch (Exception e)
        {
            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SYSTEM_ERROR,
                    "서버 오류",
                    null
            );
        }
    }
}
