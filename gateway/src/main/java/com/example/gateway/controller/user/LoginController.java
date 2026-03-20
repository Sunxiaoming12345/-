package com.example.gateway.controller.user;

import com.example.gateway.dto.UserLoginInfoDTO;
import com.example.gateway.entity.User;
import com.example.gateway.service.UserService;
import com.example.gateway.vo.UserLoginInfo;
import com.example.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.gateway.dto.UserRegisterDTO;

@Slf4j
@RestController("userLoginController")
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserLoginInfo> login(@RequestBody UserLoginInfoDTO userLoginInfoDTO){
        UserLoginInfo userLoginInfo = userService.login(userLoginInfoDTO);
        log.info("user登录成功");
        return Result.success(userLoginInfo);
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody UserRegisterDTO userRegisterDTO){
        User registeredUser = userService.register(userRegisterDTO);
        log.info("user注册成功");
        return Result.success(registeredUser);
    }

    @PostMapping("/send-code")
    public Result<String> sendVerificationCode(@RequestParam String phone){
        userService.sendVerificationCode(phone);
        log.info("向手机号 {} 发送验证码", phone);
        return Result.success("验证码发送成功");
    }

}
