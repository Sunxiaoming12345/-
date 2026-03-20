package com.example.gateway.service.impl;

import com.example.gateway.dto.UserLoginInfoDTO;
import com.example.gateway.entity.Balance;
import com.example.gateway.entity.User;
import com.example.gateway.mapper.BalanceMapper;
import com.example.gateway.mapper.UserMapper;
import com.example.gateway.service.UserService;
import com.example.gateway.utils.JwtUtils;
import com.example.gateway.vo.UserLoginInfo;
import com.example.gateway.dto.UserRegisterDTO;
import com.example.gateway.utils.VerificationCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private VerificationCodeUtils verificationCodeUtils;
    
    @Autowired
    private BalanceMapper balanceMapper;
    
    @Override
    public UserLoginInfo login(UserLoginInfoDTO userLoginInfoDTO) {

        User user = userMapper.login(userLoginInfoDTO.getUsername(),userLoginInfoDTO.getPassword());
        if (user == null) {
            throw new RuntimeException("账号或密码错误");
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        claims.put("password",user.getPassword());
        String jwt =  JwtUtils.generateToken(claims);
        return new UserLoginInfo(user.getUsername(),user.getPassword(),jwt);
    }

    @Override
    public User register(UserRegisterDTO userRegisterDTO) {
        // 验证验证码
        if (!verificationCodeUtils.verifyCode(userRegisterDTO.getPhone(), userRegisterDTO.getCode())) {
            throw new RuntimeException("验证码错误");
        }
        
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(userRegisterDTO.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建User对象
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        user.setPhone(userRegisterDTO.getPhone());
        user.setName(userRegisterDTO.getUsername()); // 使用用户名作为默认名称
        
        // 设置创建时间和更新时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        // 插入用户数据
        userMapper.insert(user);
        
        // 初始化用户余额
        Balance balance = new Balance();
        balance.setUserId(user.getId());
        balance.setBalance(BigDecimal.ZERO);
        balance.setCreateTime(LocalDateTime.now());
        balance.setUpdateTime(LocalDateTime.now());
        balanceMapper.createBalance(balance);
        
        // 移除验证码
        verificationCodeUtils.removeCode(userRegisterDTO.getPhone());
        
        return user;
    }

    @Override
    public void sendVerificationCode(String phone) {
        // 生成验证码
        String code = verificationCodeUtils.generateCode();
        // 存储验证码
        verificationCodeUtils.storeCode(phone, code);
        // 模拟发送验证码，实际生产环境中应该调用短信服务
        System.out.println("向手机号 " + phone + " 发送验证码: " + code);
    }
}

