package com.qst.finance.controller.user.dto.response;

import lombok.Data;

/**
 * 登录返回参数
 */
@Data
public class LoginDto {

    private String token;

    private String refreshToken;
}