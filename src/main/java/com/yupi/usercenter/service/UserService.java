package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.result.Result;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author xiaoxiaowu
* @description 针对表【user】的数据库操作Service
* @createDate 2024-02-02 16:03:21
*/
public interface UserService extends IService<User> {

    public static final String USER_LOGIN_STATE = "userLoginState";

    /**
     *
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount , String userPassword , String checkPassword,String planetCode);

    User userLogin(String userAccount , String userPassword, HttpServletRequest request);

   User getSafetyUser(User user);

   //用户注销
   int userLogout(HttpServletRequest request);
}
