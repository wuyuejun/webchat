package com.xcl.webchat.core.utils;

import com.xcl.webchat.core.model.OnlineUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author :xiaochanglu
 * @Description :客服分配客户工具类
 * 客服可接收的客户数有定值
 * 超出数量范围，不再接收
 * @date :2019/3/14 16:56
 */
public class DistibutionUtils {
    private static int tempNum = 0;

    private static List<OnlineUser> mapToArray(Map<String, OnlineUser> cunstomers){
        List<OnlineUser> cunstomerList = new ArrayList<OnlineUser>(cunstomers.size());
        for(Entry<String, OnlineUser> cunstomers_one:cunstomers.entrySet()){
            //每一个客服
            OnlineUser cunstomer = cunstomers_one.getValue();
            cunstomerList.add(cunstomer);
        }
        return cunstomerList;
    }
    /**
     * @Description  ：分配逻辑
     * 当 连接的客户数量 <= 客服数量的时候
     * 直接是连接进来一个,客服就按顺序接收一个
     * 当 连接的客户数量 > 客服数量的时候
     * 就取模数 ,然后按照 模数进行 给客服 分配
     * @author       : xcl
     * @param        : [clientNum, cunstomers, client]
     * @return       : boolean
     * @date         : 2019/3/14 20:08
     */
    public static boolean option(int clientNum,Map<String, OnlineUser> cunstomers,OnlineUser client){
        List<OnlineUser> list = mapToArray(cunstomers);
        if(clientNum<=list.size()){
            list.get(clientNum-1).getConectionClients().add(client);
            return true;
        }else{
            //取  模数  得到给那个客服处理
            int num = clientNum % list.size();
            List<OnlineUser> conectionClients = list.get(num).getConectionClients();
            if(conectionClients.size()>=OnlineUser.maxNum){
                return false;
            }
            list.get(num).getConectionClients().add(client);
            return true;
        }
    }

    public static void option(Map<String, OnlineUser> cunstomers,Map<String, OnlineUser> clients){
        //客服的数量
        int cunstomersSum = cunstomers.size();
        //客户的数量
        int clientsSum = clients.size();
        //客服队列
        List<OnlineUser> cunstomerList = mapToArray(cunstomers);


        if(clientsSum<=cunstomersSum){
            //遍历  客户的 数量
            for(Entry<String, OnlineUser> clients_one:clients.entrySet()){
                OnlineUser client = clients_one.getValue();
                tempNum++;
                cunstomerList.get(tempNum).getConectionClients().add(client);
            }
        }else{

        }

    }

}
