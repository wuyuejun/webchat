package com.xcl.webchat.apply.user.dao;

import com.xcl.webchat.apply.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author :xiaochanglu
 * @Description :
 * @date :2019/3/16 9:55
 */
@Mapper
public interface UserDao {
    User selectByPrimaryKey(Integer user_id);
}
