package com.qst.finance.service.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.finance.controller.menu.vo.AppRouteRecordVO;
import com.qst.finance.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单并拼成前端需要的树形路由
     */
    List<AppRouteRecordVO> listRouteTree();
}
