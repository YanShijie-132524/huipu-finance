package com.qst.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qst.finance.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    // 如果有中间表 sys_user_role(user_id, role_id) —— 常见命名
    @Select("""
        SELECT r.role_key
        FROM sys_role r
        JOIN sys_user_role ur ON ur.role_id = r.role_id
        WHERE ur.user_id = #{userId}
          AND (r.del_flag = '0' OR r.del_flag IS NULL)
    """)
    List<String> selectRoleKeysByUserId(@Param("userId") Long userId);
}