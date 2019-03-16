package com.xcl.webchat.apply.user.service;

import com.xcl.webchat.apply.user.dao.UserDao;
import com.xcl.webchat.apply.user.entity.User;
import com.xcl.webchat.core.database.config.TargetDataSource;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author :xiaochanglu
 * @Description :
 * @date :2019/3/16 10:08
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    @TargetDataSource(dataSource = "oneDataSource")
    public User getOne(Integer user_id){
        return userDao.selectByPrimaryKey(user_id);
    }

    @TargetDataSource(dataSource = "twoDataSource")
    public User getOtherOne(Integer user_id){
        return userDao.selectByPrimaryKey(user_id);
    }

}
