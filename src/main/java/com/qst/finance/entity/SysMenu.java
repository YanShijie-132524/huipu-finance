package com.qst.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    @TableField("menu_name")
    private String menuName;

    @TableField("parent_id")
    private Long parentId;

    @TableField("order_num")
    private Integer orderNum;

    @TableField("path")
    private String path;

    @TableField("component")
    private String component;

    @TableField("is_frame")
    private Integer isFrame;

    @TableField("is_cache")
    private Integer isCache;

    @TableField("menu_type")
    private String menuType;

    @TableField("visible")
    private String visible;

    @TableField("status")
    private String status;

    @TableField("perms")
    private String perms;

    @TableField("icon")
    private String icon;

    @TableField("create_by")
    private String createBy;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    // 自动填充
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("remark")
    private String remark;
}
