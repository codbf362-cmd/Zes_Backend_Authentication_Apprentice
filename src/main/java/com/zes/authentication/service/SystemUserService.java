package com.zes.authentication.service;

import com.zes.authentication.request.SystemUserSignUpRequest;
import com.zes.authentication.request.SystemUserSearchRequest;
import com.zes.authentication.request.SystemUserUpdateRequest;
import com.zes.authentication.request.SystemUserDeleteRequest;
import com.zes.authentication.request.SystemUserDetailRequest;
import com.zes.authentication.structure.hs.login.HS_loginInfo;
import com.zes.authentication.structure.hs.login.HS_loginInfoRepository;
import com.zes.authentication.structure.hs.user.HS_userInfo;
import com.zes.authentication.structure.hs.user.HS_userInfoRepository;
import com.zes.authentication.util.ZES_Enum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

@Service
public class SystemUserService
{
    private final ZES_returnService ZES_gv_returnService;
    private final HS_loginInfoRepository HS_gv_loginInfoRepo;
    private final HS_userInfoRepository HS_gv_userInfoRepo;
    private final BCryptPasswordEncoder ZES_gv_BCryptEncoder;

    public SystemUserService(ZES_returnService zes_gv_returnService, HS_loginInfoRepository hs_gv_loginInfoRepo, HS_userInfoRepository hs_gv_userInfoRepo, BCryptPasswordEncoder zes_gv_BCryptEncoder)
    {
        this.ZES_gv_returnService = zes_gv_returnService;
        this.HS_gv_loginInfoRepo = hs_gv_loginInfoRepo;
        this.HS_gv_userInfoRepo = hs_gv_userInfoRepo;
        this.ZES_gv_BCryptEncoder = zes_gv_BCryptEncoder;
    }

    @Transactional
    public JSONObject ZES_systemUserSignUp(SystemUserSignUpRequest request)
    {
        try
        {
            Optional<HS_loginInfo> ZES_lv_loginInfo = HS_gv_loginInfoRepo.findById(request.getId());

            if (ZES_lv_loginInfo.isPresent())
            {
                return ZES_gv_returnService.ZES_returnToFormat(
                        ZES_Enum.ZES_SERVICE_ERROR, "사용할 수 없는 아이디입니다.", null
                );
            }

            HS_gv_loginInfoRepo.save(
                    HS_loginInfo.builder()
                            .id(request.getId())
                            .password(ZES_gv_BCryptEncoder.encode(request.getPassword()))
                            .companyCode(request.getCompanyCode())
                            .authority(request.getAuthority())
                            .modifiedDate(java.time.LocalDateTime.now().toString())
                            .statement("active")
                            .build()
            );

            HS_gv_userInfoRepo.save(
                    HS_userInfo.builder()
                            .id(request.getId())
                            .companyCode(request.getCompanyCode())
                            .authoritiesCode(request.getAuthority())
                            .name(request.getUserName())
                            .employeeNumber("")
                            .userPosition(request.getUserPosition())
                            .phoneNumber(request.getPhoneNumber())
                            .email(request.getEmail())
                            .profileImage(request.getProfileImage())
                            .notice("")
                            .modifiedDate("")   // 문자열 유지
                            .statement("active")
                            .build()

            );

            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SUCCESS, "success", null
            );
        } catch (Exception e) {
            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SERVICE_ERROR, "error", null
            );
        }
    }
    private JSONObject ZES_systemUserList(HS_userInfo userInfo)
    {
        try
        {
            JSONObject ZES_lv_response = new JSONObject();

            ZES_lv_response.put("companyCode", userInfo.getCompanyCode());
            ZES_lv_response.put("userName", userInfo.getName());
            ZES_lv_response.put("employeeNumber", userInfo.getEmployeeNumber());
            ZES_lv_response.put("userPosition", userInfo.getUserPosition());
            ZES_lv_response.put("authorityCode", userInfo.getAuthoritiesCode());
            ZES_lv_response.put("phone_number", userInfo.getPhoneNumber());
            ZES_lv_response.put("id", userInfo.getId());
            ZES_lv_response.put("notice", userInfo.getNotice());
            ZES_lv_response.put("profileImage", userInfo.getProfileImage());
            ZES_lv_response.put("email", userInfo.getEmail());

            return ZES_lv_response;
        }
        catch (Exception e)
        {
            System.out.println("Error => SystemUserService.ZES_systemUserList(HS_userInfo)");
            return ZES_gv_returnService.ZES_returnToFormat(ZES_Enum.ZES_SERVICE_ERROR, "error", null);
        }
    }

    @Transactional
    public JSONObject ZES_systemUserList(Integer page, Integer size, String companyCode)
    {
        System.out.println("ZES S/W TEAM => SystemUserService.ZES_systemUserList");
        try
        {
            page = (page == null) ? 1 : page;
            size = (size == null) ? 10 : size;

            Pageable ZES_lv_pageable = PageRequest.of(page - 1, size);

            Page<HS_userInfo> ZES_lv_pageResult =
                    (companyCode == null || companyCode.trim().isEmpty())
                            ? HS_gv_userInfoRepo.findAllByStatement("active", ZES_lv_pageable)
                            : HS_gv_userInfoRepo.findAllByStatementAndCompanyCode("active", companyCode.trim(), ZES_lv_pageable);

            int ZES_lv_totalRows = (int) ZES_lv_pageResult.getTotalElements();

            JSONObject ZES_lv_response = new JSONObject();
            JSONArray ZES_lv_array = new JSONArray();

            for (HS_userInfo row : ZES_lv_pageResult.getContent())
            {
                JSONObject ZES_lv_json = this.ZES_systemUserList(row);
                ZES_lv_array.add(ZES_lv_json);
            }

            ZES_lv_response.put("currentPage", page);
            ZES_lv_response.put("totalPage", (ZES_lv_totalRows / size) + (ZES_lv_totalRows % size > 0 ? 1 : 0));
            ZES_lv_response.put("row", ZES_lv_array);

            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SUCCESS,
                    "success",
                    ZES_lv_response
            );
        }
        catch (Exception e)
        {
            System.out.println("Error => SystemUserService.ZES_systemUserList");
            return ZES_gv_returnService.ZES_returnToFormat(ZES_Enum.ZES_SERVICE_ERROR, "server error", null);
        }
    }

    public JSONObject ZES_systemUserSearch(SystemUserSearchRequest request)
    {
        System.out.println("ZES S/W TEAM => SystemUserService.ZES_systemUserSearch");
        try
        {
            JSONObject response = new JSONObject();
            JSONArray array = new JSONArray();

            List<String> userIdList =
                    HS_gv_userInfoRepo.systemUserSearchIds(
                            request.getKeyword(),
                            request.getCompanyCode()
                    );

            if (userIdList != null && !userIdList.isEmpty())
            {
                PageRequest pageRequest = PageRequest.of(request.getPage() - 1, request.getSize());

                int start = (int) pageRequest.getOffset();
                int end = Math.min(start + pageRequest.getPageSize(), userIdList.size());

                Page<String> pageIds = new PageImpl<>(
                        userIdList.subList(start, end),
                        pageRequest,
                        userIdList.size()
                );

                for (String id : pageIds.toList())
                {
                    Optional<HS_userInfo> userOpt =
                            HS_gv_userInfoRepo.findByIdAndStatement(id, "active");
                    if (userOpt.isPresent())
                    {
                        JSONObject row = ZES_systemUserList(userOpt.get());
                        array.add(row);
                    }
                }
            }

            int totalRows = (userIdList == null) ? 0 : userIdList.size();

            response.put("row", array);
            response.put("total_page", (totalRows / request.getSize()) + (totalRows % request.getSize() > 0 ? 1 : 0));

            return ZES_gv_returnService.ZES_returnToFormat(ZES_Enum.ZES_SUCCESS, "success", response);

        } catch (Exception e)
        {
            return ZES_gv_returnService.ZES_serverErrorException(SystemUserService.class.toString(), e);
        }
    }

    @Transactional
    public JSONObject ZES_SystemUserUpdate(SystemUserUpdateRequest request)
    {
        try
        {
            Optional<HS_userInfo> userOpt =
                    HS_gv_userInfoRepo.findByIdAndStatement(request.getId(), "active");

            if (!userOpt.isPresent())
            {
                return ZES_gv_returnService.ZES_returnToFormat(
                        ZES_Enum.ZES_SERVICE_ERROR,
                        "존재하지 않는 사용자입니다.",
                        null
                );
            }

            HS_userInfo user = userOpt.get();

            if (request.getUserName() != null)
                user.setName(request.getUserName());

            if (request.getUserPosition() != null)
                user.setUserPosition(request.getUserPosition());

            if (request.getPhoneNumber() != null)
                user.setPhoneNumber(request.getPhoneNumber());

            if (request.getEmail() != null)
                user.setEmail(request.getEmail());

            if (request.getProfileImage() != null)
                user.setProfileImage(request.getProfileImage());

            if (request.getCompanyCode() != null)
                user.setCompanyCode(request.getCompanyCode());

            HS_gv_userInfoRepo.save(user);

            if (request.getPassword() != null && !request.getPassword().isEmpty())
            {
                Optional<HS_loginInfo> loginOpt =
                        HS_gv_loginInfoRepo.findByIdAndStatement(request.getId(), "active");

                if (loginOpt.isPresent())
                {
                    HS_loginInfo login = loginOpt.get();
                    login.setPassword(ZES_gv_BCryptEncoder.encode(request.getPassword()));
                    HS_gv_loginInfoRepo.save(login);
                }
            }

            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SUCCESS,
                    "사용자 정보가 수정되었습니다.",
                    null
            );
        }
        catch (Exception e)
        {
            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }
    }

    @Transactional
    public JSONObject ZES_systemUserDelete(SystemUserDeleteRequest request)
    {
        try
        {
            Optional<HS_userInfo> userOpt =
                    HS_gv_userInfoRepo.findByIdAndStatement(request.getId(), "active");

            if (!userOpt.isPresent())
            {
                return ZES_gv_returnService.ZES_returnToFormat(
                        ZES_Enum.ZES_SERVICE_ERROR,
                        "존재하지 않는 사용자입니다.",
                        null
                );
            }
            HS_userInfo user = userOpt.get();
            user.setStatement("inactive");
            HS_gv_userInfoRepo.save(user);

            Optional<HS_loginInfo> loginOpt =
                    HS_gv_loginInfoRepo.findByIdAndStatement(request.getId(), "active");

            if (loginOpt.isPresent())
            {
                HS_loginInfo login = loginOpt.get();
                login.setStatement("inactive");
                HS_gv_loginInfoRepo.save(login);
            }

            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SUCCESS,
                    "사용자가 삭제되었습니다.",
                    null
            );
        }
        catch (Exception e)
        {
            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }
    }

    @Transactional
    public JSONObject ZES_systemUserDetail(SystemUserDetailRequest request)
    {
        try
        {
            Optional<HS_userInfo> userOpt =
                    HS_gv_userInfoRepo.findByIdAndStatement(request.getId(), "active");

            if (!userOpt.isPresent())
            {
                return ZES_gv_returnService.ZES_returnToFormat(
                        ZES_Enum.ZES_SERVICE_ERROR,
                        "존재하지 않는 사용자입니다.",
                        null
                );
            }

            HS_userInfo user = userOpt.get();

            JSONObject data = new JSONObject();
            data.put("id", user.getId());
            data.put("name", user.getName());
            data.put("userPosition", user.getUserPosition());
            data.put("phoneNumber", user.getPhoneNumber());
            data.put("email", user.getEmail());
            data.put("profileImage", user.getProfileImage());
            data.put("companyCode", user.getCompanyCode());
            data.put("statement", user.getStatement());
            data.put("modifiedDate", user.getModifiedDate());

            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SUCCESS,
                    "조회 성공",
                    data
            );
        }
        catch (Exception e)
        {
            return ZES_gv_returnService.ZES_returnToFormat(
                    ZES_Enum.ZES_SERVER_ERROR,
                    e.getMessage(),
                    null
            );
        }
    }
}
