package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.exception.UserInfoNullException;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.result.Result;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.utils.MD5Util;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.PipedReader;

/**
* @author xiaoxiaowu
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-02-02 16:03:21
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    public static final String USER_LOGIN_STATE = "userLoginState";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode))
        {
            throw new UserInfoNullException("用户有误");
        }
        if(userAccount.length()<4)
        {
            throw new UserInfoNullException("用户有误");
        }
        if(planetCode.length() > 5)
            throw new UserInfoNullException("用户有误");
        //账户不重复
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount,userAccount);
        Long count = this.count(queryWrapper);
        if(count>0)
            throw new UserInfoNullException("用户有误");
        //星球编号不能重复
        LambdaQueryWrapper<User> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getPlanetCode,planetCode);
        Long count1 = this.count(queryWrapper1);
        if(count1>0)
            throw new UserInfoNullException("用户有误");

        User user = new User();
        user.setUserPassword(MD5Util.encrypt(userPassword));
        user.setUserAccount(userAccount);
        user.setPlanetCode(planetCode);
        boolean save = this.save(user);
        if(!save )             throw new UserInfoNullException("用户有误");
       // int insert = userMapper.insert(user);
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword , HttpServletRequest request) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword))
        {
            throw new UserInfoNullException("用户信息不能存在空");
        }
        if(userAccount.length()<4)
        {
            return null;
        }
        //加密
        String pwd = MD5Util.encrypt(userPassword);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount" , userAccount);
        queryWrapper.eq("userPassword",pwd);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw new UserInfoNullException("用户不存在");

        }
        User safetyUser = getSafetyUser(user);
        //登录成功，记录用户的登录态 (后续继续研究一下)
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }

    @Override
    public User getSafetyUser(User user)
    {
        if(user == null) return null;
        //用户脱敏
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAge(user.getAge());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setPlanetCode(user.getPlanetCode());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }


}




