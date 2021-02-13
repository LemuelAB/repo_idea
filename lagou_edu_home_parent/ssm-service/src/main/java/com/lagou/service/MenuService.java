package com.lagou.service;

import com.lagou.domain.Menu;

import java.util.List;

public interface MenuService {
    public List<Menu> findSubMenuListByPid(int pid);
    public List<Menu> findAllMenu();
    public Menu findMenuById(Integer id);

}
