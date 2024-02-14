package com.yupi.usercenter;

import com.yupi.usercenter.result.Result;
import com.yupi.usercenter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.transform.Source;

@SpringBootTest
class UserCenterApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
    }

  //  @Test
    void userRegister()
    {
        String userAccount = "wwja";
        String userPassword = "123456";
        String checkPassword  = "123456";

        long id = userService.userRegister(userAccount, userPassword, checkPassword,"1");
        System.out.println(id);
    }
}
