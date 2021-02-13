package com.lagou.service;

import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;

import java.util.List;

public interface RoleService {

    /*
        查询所有角色（条件）
     */
    public List<Role>  findAllRole(Role role);


    /*
    根据角色ID查询菜单信息
    */
    List<Integer> findMenuByRoleId(Integer roleId);

    /*
    为角色分配菜单
    */
    void RoleContextMenu(RoleMenuVo roleMenuVo);

    void deleteRole(Integer roleId);
}
