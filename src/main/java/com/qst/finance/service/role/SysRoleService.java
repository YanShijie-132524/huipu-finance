package com.qst.finance.service.role;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.finance.controller.role.dto.vo.RoleListItemVO;
import com.qst.finance.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    List<RoleListItemVO> getRoleList(String roleName);

}
