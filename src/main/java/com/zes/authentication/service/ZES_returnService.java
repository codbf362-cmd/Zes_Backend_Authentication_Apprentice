package com.zes.authentication.service;

import com.zes.authentication.util.ZES_Enum;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ZES_returnService
{
    public JSONObject ZES_returnToFormat(ZES_Enum code, String message, Object data)
    {
        JSONObject res = new JSONObject();
        res.put("code", code.name());
        res.put("message", message);
        res.put("data", data);
        return res;
    }

    public JSONObject ZES_serverErrorException(String className, Exception e)
    {
        JSONObject res = new JSONObject();
        res.put("code", ZES_Enum.ZES_SERVICE_ERROR.name());
        res.put("message", "server error");
        res.put("errorClass", className);
        res.put("errorMessage", e.getMessage());
        return res;
    }

    public JSONObject ZES_validationError(String message)
    {
        JSONObject res = new JSONObject();
        res.put("code", ZES_Enum.ZES_VALID_ERROR.name());
        res.put("message", message);
        res.put("data", null);
        return res;
    }
}