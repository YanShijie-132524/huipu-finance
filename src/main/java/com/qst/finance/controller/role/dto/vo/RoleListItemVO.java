package com.qst.finance.controller.role.dto.vo;

import lombok.Data;

import java.time.LocalTime;

/*
roleName: string
      status: boolean
      createBy: string
      createTime: string
      updateBy: string
      updateTime: string
      remark: string
 */
@Data
public class RoleListItemVO {

    private String roleName;

    private Boolean status;

    private String createBy;

    private LocalTime createTime;

    private String updateBy;

    private LocalTime updateTime;

    private String remark;

}
