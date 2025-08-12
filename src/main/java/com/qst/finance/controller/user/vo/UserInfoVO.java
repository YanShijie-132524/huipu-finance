package com.qst.finance.controller.user.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

// com.qst.finance.user.vo.UserInfoVO  —— 对应 Api.User.UserInfo
@Data
@Builder
public class UserInfoVO {
    private Long userId;
    private String userName;
    private List<String> roles;
    private List<String> buttons;
    private String avatar;
    private String email;
    private String phone;
}
