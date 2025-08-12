package com.qst.finance.controller.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

// id: number
// avatar: string
// createBy: string
// createTime: string
// updateBy: string
// updateTime: string
// status: '1' | '2' | '3' | '4' // 1: 在线 2: 离线 3: 异常 4: 注销
// userName: string
// userGender: string
// nickName: string
// userPhone: string
// userEmail: string
// userRoles: string[]
@Data
public class UserListItemVO {

    private Long id;

    private String avatar;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 1:在线 2:离线 3:异常 4:注销
     */
    private String status;

    private String userName;

    private String userGender;

    private String nickName;

    private String userPhone;

    private String userEmail;

    private List<String> userRoles;
}
