package com.qst.finance.service.menu.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.finance.controller.menu.vo.AppRouteRecordVO;
import com.qst.finance.controller.menu.vo.RouteMetaVO;
import com.qst.finance.entity.SysMenu;
import com.qst.finance.mapper.SysMenuMapper;
import com.qst.finance.service.menu.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname SysMenuServiceImpl
 * @Description TODO
 * @Date 2025/08/11 17:40
 * @Created by YanShijie
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    @Override
    public List<AppRouteRecordVO> listRouteTree() {
        // 1) 查出目录/菜单（M=目录 C=菜单；若你库里别的编码，自行调整）
        List<SysMenu> all = this.lambdaQuery()
                .in(SysMenu::getMenuType, Arrays.asList("M", "C"))
                .orderByAsc(SysMenu::getOrderNum, SysMenu::getMenuId)
                .list();

        // 2) 转成 id->VO
        Map<Long, AppRouteRecordVO> id2vo = all.stream()
                .collect(Collectors.toMap(SysMenu::getMenuId, this::toVO));

        // 3) 组装树
        List<AppRouteRecordVO> roots = new ArrayList<>();
        for (SysMenu m : all) {
            AppRouteRecordVO vo = id2vo.get(m.getMenuId());
            Long pid = Optional.ofNullable(m.getParentId()).orElse(0L);
            if (pid == 0L) {
                roots.add(vo);
            } else {
                AppRouteRecordVO parent = id2vo.get(pid);
                if (parent != null) parent.getChildren().add(vo);
                else roots.add(vo); // 父级丢失时兜底当根
            }
        }
        return roots;
    }

    private AppRouteRecordVO toVO(SysMenu m) {
        String routeName = "route_" + m.getMenuId();

        boolean hidden = "1".equals(m.getVisible());
        boolean keepAlive = Objects.equals(m.getIsCache(), 0);

        // ✅ 顶级父级菜单且 component 为空 → 指向 /index/index
        boolean isRoot = m.getParentId() == null || m.getParentId() == 0L;
        String component = m.getComponent();
        if (isRoot || !StringUtils.hasText(component) || "/".equals(component) || "/null".equalsIgnoreCase(component)) {
            component = "index/index";
        }

        RouteMetaVO meta = RouteMetaVO.builder()
                .title(m.getMenuName())
                .icon(m.getIcon())
                .hidden(hidden)
                .keepAlive(keepAlive)
                .build();

        return AppRouteRecordVO.builder()
                .id(m.getMenuId())
                .path("/" + m.getPath())
                .name(routeName)
                .component("/" + component) // <- 用处理后的 component
                .meta(meta)
                .build();
    }
}

