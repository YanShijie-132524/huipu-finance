package com.qst.finance.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.finance.controller.role.dto.vo.RoleListItemVO;
import com.qst.finance.entity.SysRole;
import com.qst.finance.mapper.SysRoleMapper;
import com.qst.finance.service.role.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname SysRoleServiceImpl
 * @Description TODO
 * @Date 2025/08/11 20:06
 * @Created by YanShijie
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    @Override
    public List<RoleListItemVO> getRoleList(String roleName) {
        return sysRoleMapper.getRoleList(roleName);
    }
}
