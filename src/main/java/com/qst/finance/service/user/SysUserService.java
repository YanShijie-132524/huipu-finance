package com.qst.finance.service.user;

import com.baomidou.mybatisplus.extension.service.IService;

import com.qst.finance.controller.user.dto.request.LoginParamDto;
import com.qst.finance.controller.user.dto.request.UserPageQueryDTO;
import com.qst.finance.controller.user.dto.response.LoginDto;
import com.qst.finance.controller.user.vo.UserInfoVO;
import com.qst.finance.controller.user.vo.UserListItemVO;
import com.qst.finance.entity.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录Service接口
     *
     * @param loginDto 登录参数
     * @return
     */
    LoginDto login(LoginParamDto loginDto);

    UserInfoVO getUserInfo();

    Page<UserListItemVO> page(UserPageQueryDTO dto);

}
