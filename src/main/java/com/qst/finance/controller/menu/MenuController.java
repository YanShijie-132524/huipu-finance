package com.qst.finance.controller.menu;

import com.qst.finance.common.R;
import com.qst.finance.controller.menu.vo.AppRouteRecordVO;
import com.qst.finance.service.menu.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname MenuController
 * @Description TODO
 * @Date 2025/08/11 17:39
 * @Created by YanShijie
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {

    private final SysMenuService sysMenuService;

    @GetMapping("/list")
    public R<List<AppRouteRecordVO>> list() {
        return R.success(sysMenuService.listRouteTree());
    }

}
