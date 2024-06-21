package com.hitwh.userservice.service.impl;

import com.hitwh.userservice.config.authentication.JWTUtil;
import com.hitwh.userservice.entity.User;
import com.hitwh.userservice.mapper.UserMapper;
import com.hitwh.userservice.service.UserService;
import com.netflix.discovery.shared.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisTemplate<String, Object> redisTemplate) {
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Pair<User, String> login(String username, String password) {
        User user = userMapper.getUserByUsernameAndPassword(username, password);

        String token = null;
        if (user != null) {
            token = JWTUtil.generateToken(username, String.valueOf(user.getId()));
            user.setToken(token);
            redisTemplate.opsForValue().set(username, user, 1, TimeUnit.HOURS);
        }

        return new Pair<>(user, token);
    }

    @Override
    public boolean register(User user) {
        return userMapper.addUser(user) != 0;
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public boolean deleteUser(int id) {
        return userMapper.deleteUserById(id) != 0;
    }

    @Override
    public User getUserByToken(String token) {
        token = token.replace("Bearer ", "");
        String username = JWTUtil.getUsernameFromToken(token);
        if (username == null)
            return null;
        User user = (User) redisTemplate.opsForValue().get(username);
        if (user != null && user.getToken().equals(token))
            return user;
        return null;
    }

    @Override
    public Boolean logout(String token) {
        token = token.replace("Bearer ", "");
        String username = JWTUtil.getUsernameFromToken(token);
        if (username == null)
            return false;
        return redisTemplate.delete(username);
    }

}
