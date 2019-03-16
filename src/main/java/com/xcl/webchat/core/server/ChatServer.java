package com.xcl.webchat.core.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xcl.webchat.core.model.Message;
import com.xcl.webchat.core.model.OnlineUser;
import com.xcl.webchat.core.model.RequestModel;
import com.xcl.webchat.core.utils.DistibutionUtils;
import com.xcl.webchat.core.utils.JSONUtils;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author :xiaochanglu
 * @Description :webSocket服务
 * @date :2019/3/14 9:02
 */
@Component
@ServerEndpoint("/websocket")
@Slf4j
public class ChatServer {
    private static int clientNum = 0;
    /**
     * 所有在线用户
     */
    private static Map<String, OnlineUser> OnlineUsers = new ConcurrentHashMap<String, OnlineUser>();
    /**
     * 当前客服的人员集合
     */
    private static Map<String, OnlineUser> cunstomers = new ConcurrentHashMap<String, OnlineUser>();
    /**
     * 当前客户的集合
     */
    private static Map<String, OnlineUser> clients = new ConcurrentHashMap<String, OnlineUser>();
    /**
     * @Description  ：将连接 转换为 实体类
     * @author       : xcl
     * @param        : [session]
     * @return       : com.xcl.kf.model.OnlineUser
     * @date         : 2019/3/14 11:19
     */
    private OnlineUser checkType(Session session){
        //获取？号后边的所有值   例如:type=1
        String typeString = session.getQueryString();
        Map<String,String> maps = RequestModel.queryParameters(typeString);
        String userId = session.getId();
        String name = maps.get("name");
        Integer userType = Integer.valueOf(maps.get("type"));
        return new OnlineUser(userId,name,userType,session);
    }

    /**
     * @Description  ：连接成功调用的方法
     * @author       : xcl
     * @param        : [session]
     * @return       : void
     * @date         : 2019/3/14 20:14
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info("一个用户连接成功......");
        //每一个连接,就说明一个用户上线(可能是 客服 也可能是 用户)
        OnlineUser onlineUser = checkType(session);

        //发送给自己个人信息
        String str = JSONUtils.beanToString(onlineUser,new String[]{"session","conectionClients"});
        JSONObject obj = JSONUtils.stringTOJSON(str);
        Message.singleSend(Message.getMessage(5,obj),onlineUser);

        //当前上线的用户是  客服人员
        if(onlineUser.getUserType() == 1){
            cunstomers.put(onlineUser.getUserId(),onlineUser);
        }
        //当前上线的用户是  客户
        if(onlineUser.getUserType() == 2){
            //每连接进一个客户就记一数
            clientNum++;
            clients.put(onlineUser.getUserId(),onlineUser);
            //客户一旦上线,马上给所有客服推送消息,通知所有客服  有用户上线了
            String message = Message.getMessage(1,"客户("+onlineUser.getName()+")上线了!");
            Message.broadcast(message,cunstomers);
            //同时要客服那边要更新所有客户列表信息
            //在传给客服  客户列表的时候,可以进行分组,一个客服分配到几个用户
            boolean flag = DistibutionUtils.option(clientNum,cunstomers,onlineUser);
            if(flag){
                //分配成功
                for(Entry<String, OnlineUser> cunstomer_one:cunstomers.entrySet()){
                    OnlineUser cunstomer = cunstomer_one.getValue();
                    //获取到每个客服自己的客户列表信息
                    JSONArray jsonArray = JSONUtils.listToJSONArray(cunstomer.getConectionClients(),new String[]{"session"});
                    String listMessage = Message.getMessage(3,jsonArray);
                    Message.singleSend(listMessage,cunstomer);
                }
            }else{
                //客服 繁忙  请等待
            }

        }
        OnlineUsers.put(onlineUser.getUserId(),onlineUser);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("获得的消息：" + message + "发送消息的id是：" + session.getId());

        JSONObject jsonObject = JSONUtils.stringTOJSON(message);
        log.info("传递过的信息:"+JSONUtils.beanToString(jsonObject));

        String messageText = jsonObject.getString("message");
//        String sendUserName = jsonObject.getString("sendUserName");
        String sendUserId = jsonObject.getString("sendUserId");
//        String getUserName = jsonObject.getString("getUserName");
        String getUserId = jsonObject.getString("getUserId");


        OnlineUser fromUser = OnlineUsers.get(sendUserId);
        log.info("==发送者信息"+fromUser.toString());
        OnlineUser toUser = OnlineUsers.get(getUserId);
        log.info("==接收者信息"+toUser.toString());

        String str1 = JSONUtils.beanToString(fromUser,new String[]{"session","conectionClients"});
        JSONObject fromUserObj = JSONUtils.stringTOJSON(str1);

        String str2 = JSONUtils.beanToString(toUser,new String[]{"session","conectionClients"});
        JSONObject toUserObj = JSONUtils.stringTOJSON(str2);

        String messageContent = Message.getMessage(fromUserObj,toUserObj,4,messageText);
        Message.singleSend(messageContent,toUser);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("=====关闭的sessionID:"+session.getId());
        OnlineUser one = OnlineUsers.get(session.getId());
        //如果退出的是客户的话  需要  通知  对应的客服刷新  列表
        if(one.getUserType()==2){
            OnlineUser checkCun = null;
            oneLoop:for(Entry<String, OnlineUser> cunstomer_one:cunstomers.entrySet()){
                OnlineUser cunstomer = cunstomer_one.getValue();
                if(null!=cunstomer.getConectionClients()&&cunstomer.getConectionClients().size()>0){
                    for(OnlineUser client:cunstomer.getConectionClients()){
                        if(client.getUserId().equals(session.getId())){
                            //将该  客户信息  从 队列中删除
                            cunstomer.getConectionClients().remove(client);
                            checkCun = cunstomer;
                            break oneLoop;
                        }
                    }
                }
            }
            JSONArray jsonArray = JSONUtils.listToJSONArray(checkCun.getConectionClients(),new String[]{"session"});
            String listMessage = Message.getMessage(3,jsonArray);
            Message.singleSend(listMessage,checkCun);

            //发送下线人的信息
            String str = JSONUtils.beanToString(one,new String[]{"session","conectionClients"});
            JSONObject obj = JSONUtils.stringTOJSON(str);
            Message.singleSend(Message.getMessage(2,obj),checkCun);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("服务端发生了错误" + error.getMessage());
    }
}
