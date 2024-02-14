package com.yupi.usercenter.mapper;

import com.yupi.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoxiaowu
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-02-02 16:03:21
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




