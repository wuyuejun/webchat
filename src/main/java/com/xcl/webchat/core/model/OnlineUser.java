package com.xcl.webchat.core.model;

import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import lombok.Data;

/**
 * @author :xiaochanglu
 * @Description :在线用户实体类
 * @date :2019/3/14 9:05
 */
@Data
public class OnlineUser {
    private String userId;
    private String name;
    /**
     * 1 为客服  2为用户
     */
    private Integer userType;
    private Session session;
    /**
     * 状态  1空闲  2繁忙
     */
    private Integer state;
    /**
     * 每各客服人员可以设置最大聊天人数
     */
    public static Integer maxNum = 5;
    /**
     * 初始化  设置容量大小为5  后续操作,向容器内放数据的时候,进行判断
     * 如果超过  maxNum  就往其它客服中放置
     */
    private List<OnlineUser> conectionClients = new ArrayList<OnlineUser>(maxNum);

    public OnlineUser(String userId, String name, Integer userType, Session session) {
        this.userId = userId;
        this.name = name;
        this.userType = userType;
        this.session = session;
    }

    @Override
    public String toString() {
        return "OnlineUser{" +
            "userId='" + userId + '\'' +
            ", name='" + name + '\'' +
            ", userType=" + userType +
            '}';
    }
}
