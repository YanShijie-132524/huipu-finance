package com.qst.finance.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.finance.common.R;
import com.qst.finance.controller.user.dto.request.LoginParamDto;
import com.qst.finance.controller.user.dto.request.UserPageQueryDTO;
import com.qst.finance.controller.user.dto.response.LoginDto;
import com.qst.finance.controller.user.vo.UserInfoVO;
import com.qst.finance.controller.user.vo.UserListItemVO;
import com.qst.finance.service.user.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2025/08/09 18:08
 * @Created by YanShijie
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final SysUserService sysUserService;

    @PostMapping("/login")
    public R<LoginDto> login(@RequestBody LoginParamDto loginParamDto) {
        log.error(loginParamDto.toString());
        return R.success(sysUserService.login(loginParamDto));
    }

    @GetMapping("/info")
    public R<UserInfoVO> info() {
        return R.success(sysUserService.getUserInfo());
    }

    @GetMapping("/list")
    public R<Page<UserListItemVO>> list(UserPageQueryDTO dto) {
        return R.success(sysUserService.page(dto));
    }

}
