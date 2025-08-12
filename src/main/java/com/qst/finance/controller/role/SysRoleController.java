package com.qst.finance.controller.role;

import com.qst.finance.common.R;
import com.qst.finance.controller.role.dto.vo.RoleListItemVO;
import com.qst.finance.service.role.SysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @GetMapping("/list")
    public R<List<RoleListItemVO>> getRoleList(@RequestParam("roleName") String roleName) {
        return R.success(sysRoleService.getRoleList(roleName));
    }

}
