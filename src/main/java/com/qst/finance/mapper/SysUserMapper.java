package com.qst.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.finance.controller.user.dto.request.UserPageQueryDTO;
import com.qst.finance.controller.user.vo.UserListItemVO;
import com.qst.finance.entity.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {

    Page<UserListItemVO> selectUserPage(UserPageQueryDTO dto);

}