package com.xcl.webchat.model;

import com.alibaba.fastjson.JSONObject;
import com.xcl.webchat.utils.DateUtils;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import javax.websocket.Session;
import lombok.extern.slf4j.Slf4j;

/**
 * @author :xiaochanglu
 * @Description :消息实体类
 * @date :2019/3/14 9:03
 */
@Slf4j
public class Message {
    /**
     * @Description  ：组装信息
     * @author       : xcl
     * @param        : [message, type, userList] [交互消息,消息类型,接收群体]
     * 消息类型  1上线通知  2下线通知  3在线列表  4普通消息  5个人信息
     * @return       : java.lang.String
     * @date         : 2019/3/14 9:08
     */
    public static String getMessage(Object fromUser,Object toUser,Integer type,Object message){
        JSONObject msg = new JSONObject();
        msg.put("fromUser",fromUser);
        msg.put("toUser",toUser);
        msg.put("type", type);
        msg.put("message",message);
        //当前的时间戳
        msg.put("time", DateUtils.dataToLong(DateUtils.now()));
        return msg.toString();
    }
    /**
     * @Description  ：组装信息
     * @author       : xcl
     * @param        : [type, message]
     * @return       : java.lang.String
     * @date         : 2019/3/14 11:11
     */
    public static String getMessage(Integer type,Object object){
        JSONObject msg = new JSONObject();
        msg.put("type", type);
        msg.put("message",object);
        //当前的时间戳
        msg.put("time", DateUtils.dataToLong(DateUtils.now()));
        return msg.toString();
    }


    /**
     * @Description  ：广播消息
     * @author       : xcl
     * @param        : [userType, message, onlineUsers]
     * [发送者的用户类型,消息,接收者(有区分,可能是客服,可能是用户)]
     * @return       : void
     * @date         : 2019/3/14 9:11
     */
    public static void broadcast(String message, Map<String, OnlineUser> onlineUsers){
        StringBuffer userStr = new StringBuffer();
        for(Entry<String, OnlineUser> one:onlineUsers.entrySet()){
            OnlineUser user = one.getValue();
            userStr.append(user.getName() + ",");
            try {
                user.getSession().getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("消息发送失败！" + e.getMessage());
                continue;
            }
//            System.out.println("========"+user.getName());
//            Session session = user.getSession();
//            if(session.isOpen()){
//                System.out.println("******"+user.getName());
//                new Thread(new messageSend(session,message)).start();
//            }
        }
        userStr.deleteCharAt(userStr.length()-1);
        //打印出信息  和  接收者
        log.info("[broadcast] message = " + message + ", onlineUsers = " + userStr.toString());
    }

    private static class messageSend implements Runnable{
        private Session session;
        private String message;

        public messageSend(Session session, String message) {
            this.session = session;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                System.out.println("******"+session.getId());
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("消息发送失败！" + e.getMessage());
            }
        }
    }
    /**
     * @Description  ：对特定用户发送消息
     * @author       : xcl
     * @param        : [message, onlineUser]
     * @return       : void
     * @date         : 2019/3/14 9:12
     */
    public static void singleSend(String message, OnlineUser onlineUser){
        try {
            onlineUser.getSession().getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("消息发送失败！" + e.getMessage());
        }
    }



}
