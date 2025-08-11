package com.qst.finance.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.finance.controller.user.dto.request.LoginParam;
import com.qst.finance.controller.user.dto.response.LoginDto;
import com.qst.finance.entity.SysUser;

public interface UserService extends IService<SysUser> {

    /**
     * 用户登录Service接口
     *
     * @param loginParam 登录参数
     * @return
     */
    LoginDto login(LoginParam loginParam);

}
