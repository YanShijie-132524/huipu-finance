package com.qst.finance.controller.menu.vo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteMetaVO {
    /** 菜单标题 -> 前端 meta.title */
    private String title;
    /** 图标 -> 前端 meta.icon */
    private String icon;
    /** 是否隐藏 -> 前端 meta.hidden */
    private Boolean hidden;
    /** 是否缓存 -> 前端 meta.keepAlive */
    private Boolean keepAlive;
}
