package com.yupi.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserLoginRequest;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import com.yupi.usercenter.result.Result;
import com.yupi.usercenter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yupi.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        long id = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        return Result.success(id);
    }
    @PostMapping("/login")
    public Result<User> login(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request) {

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        User user = userService.userLogin(userAccount, userPassword,request);

        return Result.success(user);
    }

    @PostMapping("/logout")
    public Result userLogout(HttpServletRequest request) {

        if(request == null ) return null;
        int i = userService.userLogout(request);
        return Result.success(i);
    }

@GetMapping("current")
public Result<User> getCurrentUser(HttpServletRequest request)
{
    Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
    User user = (User)userObj;

    if(user == null)return null;
    Long id = user.getId();
    //todo 校验用户是否合法
    User currentUser = userService.getById(id);
    User safetyUser = userService.getSafetyUser(currentUser);
    return Result.success(safetyUser);

}

    @GetMapping("/search")
    public Result<List<User>> searchUsers(String username, HttpServletRequest request)
    {
        //仅管理员可以查看
        if (!isAdmin(request))
            return Result.error("无管理员权限");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNotBlank(username))
        {
            queryWrapper.like("username",username);
        }

        List<User>userslist= userService.list(queryWrapper);
        //对list集合中每个user进行操作
        List<User> collect = userslist.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return Result.success(collect);
    }
    @PostMapping ("/delete")
    public Result deleteUser(@RequestParam long id , HttpServletRequest request)
    {
        //仅管理员可以删除
        if (!isAdmin(request))
            return Result.error("无管理员权限");
        if(id <=0 )
            return Result.error("id不能小于或等于0");
        boolean b = userService.removeById(id);
        if(b) return Result.success();
        return Result.error("删除失败");
    }

    private boolean isAdmin(HttpServletRequest request)
    {
        //仅管理员可以查看

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)userObj;
        if(user == null || user.getUserRole()!=ADMIN_ROLE)
        {
            return false;
        }
        return true;
    }
}
