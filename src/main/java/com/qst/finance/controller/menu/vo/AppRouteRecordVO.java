package com.qst.finance.controller.menu.vo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 对齐前端的 AppRouteRecord
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRouteRecordVO {
    private Long id;                         // menuId
    private String path;                     // 路由路径
    private String name;                     // 路由名（可用英文/拼音）
    private String component;                // 组件路径（如 "Layout" 或 "system/user/index"）
    private String redirect;                 // 可选：重定向
    private RouteMetaVO meta;                // meta
    @Builder.Default
    private List<AppRouteRecordVO> children = new ArrayList<>(); // 子节点
}
