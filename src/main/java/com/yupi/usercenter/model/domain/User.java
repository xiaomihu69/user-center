package com.yupi.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName user
 */

@Data
public class User implements Serializable {
    private Long id;

    private String username;

    private String userAccount;

    private Integer age;

    private String email;

    private Integer gender;

    private String userPassword;

    private String phone;

    private Integer userStatus;

    private String avatarUrl;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

    private Integer userRole;

    private String planetCode;

    private static final long serialVersionUID = 1L;
}