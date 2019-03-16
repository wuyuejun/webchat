package com.xcl.webchat.core.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :xiaochanglu
 * @Description :请求实体类
 * @date :2019/3/13 16:26
 */
public class RequestModel {
    private static String SYMBOLONE = "&";
    private static String SYMBOLTWO = "=";

//    private String parameterName;
//    private String parameterValue;

    public static Map<String,String> queryParameters(String str){
        Map<String,String> parameters = new HashMap<String,String>();
        if(str!=null&&!str.equals("")){
            String[] combinations = str.split(SYMBOLONE);
            if(combinations!=null&&combinations.length>0){
                for(String one:combinations){
                    String[] parameter = one.split(SYMBOLTWO);
                    parameters.put(parameter[0],parameter[parameter.length-1]);
                }

            }
        }
        return parameters;
    }
}
