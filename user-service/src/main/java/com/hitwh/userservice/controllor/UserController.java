package com.hitwh.userservice.controllor;


import com.hitwh.userservice.entity.ResultInfo;
import com.hitwh.userservice.entity.User;
import com.hitwh.userservice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.netflix.discovery.shared.Pair;

@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     *
     * @param user 用户
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResultInfo login(@RequestBody User user, HttpServletResponse response) {
        Pair<User, String> existUser = userService.login(user.getUsername(), user.getPassword());
        ResultInfo info = new ResultInfo();
        if (null != existUser.first()) {
            info.setFlag(true);
            info.setData(existUser.first());
            response.setHeader("Authorization", "Bearer " + existUser.first());
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
        } else {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        return info;
    }

    /**
     * 用户注册
     *
     * @param user 用户
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResultInfo register(@RequestBody User user) {
        ResultInfo info = new ResultInfo();
        boolean flag = userService.register(user);
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("用户名已存在");
        }
        return info;
    }

    /**
     * 根据token获取在线用户
     *
     * @return 在线用户
     */
    @GetMapping("/getUser")
    public ResultInfo getUser(@RequestHeader(value = "Authorization", required = false) String token) {
        User user = token != null ? userService.getUserByToken(token) : null;
        ResultInfo info = new ResultInfo();
        if (token != null) {
            info.setFlag(user != null);
            info.setData(user);
        } else {
            info.setFlag(false);
            info.setErrorMsg("获取在线用户信息失败");
        }
        return info;
    }

    /**
     * 用户登出
     *
     * @return 登出结果
     */
    @GetMapping("/logout")
    public ResultInfo logout(@RequestHeader("Authorization") String token) {
        ResultInfo info = new ResultInfo();
        if (userService.logout(token)) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("登出失败");
        }
        return info;
    }

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    @GetMapping("/getAllUser")
    public ResultInfo getAllUser() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(userService.getUserList());
        resultInfo.setFlag(true);
        return resultInfo;
    }

}
