package com.hitwh.userservice.service;



import com.hitwh.userservice.entity.User;
import com.netflix.discovery.shared.Pair;

import java.util.List;

public interface UserService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息和token
     */
    Pair<User, String> login(String username, String password);

    /**
     * 注册
     *
     * @param user 用户信息
     * @return 是否注册成功
     */
    boolean register(User user);

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    List<User> getUserList();

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    boolean deleteUser(int id);

    /**
     * 根据token获取用户
     *
     * @param token token
     * @return 用户
     */
    User getUserByToken(String token);

    /**
     * 退出登录
     *
     * @param token token
     * @return 是否退出成功
     */
    Boolean logout(String token);
}
