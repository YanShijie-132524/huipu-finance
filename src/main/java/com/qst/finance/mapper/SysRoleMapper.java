package com.qst.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qst.finance.controller.role.dto.vo.RoleListItemVO;
import com.qst.finance.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("""
        SELECT r.role_key
        FROM sys_role r
        JOIN sys_user_role ur ON ur.role_id = r.role_id
        WHERE ur.user_id = #{userId}
          AND (r.del_flag = '0' OR r.del_flag IS NULL)
    """)
    List<String> selectRoleKeysByUserId(@Param("userId") Long userId);


    List<RoleListItemVO> getRoleList(String roleName);
}