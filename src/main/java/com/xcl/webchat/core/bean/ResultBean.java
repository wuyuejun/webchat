package com.xcl.webchat.core.bean;

import java.io.Serializable;
import lombok.Data;

/**
 * @author :xiaochanglu
 * @Description :结果bean
 * @date :2018/12/13 14:40
 */
@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 5469950154296270097L;
    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;
    /**
     * 操作失败
     */
    public static final int FAIL = 500;
    /**
     * 参数校验失败
     */
    public static final int VALIDATE_FAILED = 404;
    /**
     * 未认证
     */
    public static final int UNAUTHORIZED = 401;
    /**
     * 未授权
     */
    public static final int  FORBIDDEN = 403;

    /**
     * 状态码，0：成功，-1：失败
     */
    private int status;
    /**
     * 描述信息
     */
    private String msg;
    /**
     * 服务端数据
     */
    private T data;


    public ResultBean status(int status) {
        this.setStatus(status);
        return this;
    }

    public ResultBean msg(String msg){
        this.setMsg(msg);
        return this;
    }

    public ResultBean msg(Throwable e){
        this.setMsg(e.toString());
        return this;
    }

    public ResultBean data(T data){
        this.setData(data);
        return this;
    }


    /**
     * @Description  ：默认成功格式
     * @author       : xcl
     * @return       : xcl.com.pro.com.xcl.sso.core.bean.ResultBean
     * @date         : 2018/12/14 14:34
     */
    public ResultBean success() {
        return this.msg("操作成功").status(SUCCESS);
    }
    /**
     * @Description  ：自定义返回格式
     * @author       : xcl
     * @param        : [msg, status, data]
     * @return       : xcl.com.pro.com.xcl.sso.core.bean.ResultBean
     * @date         : 2018/12/14 14:34
     */
    public ResultBean custom(int status, String msg,T data) {
        return this.status(status).msg(msg).data(data);
    }
    /**
     * @Description  ：默认失败格式
     * @author       : xcl
     * @return       : xcl.com.pro.com.xcl.sso.core.bean.ResultBean
     * @date         : 2018/12/14 14:34
     */
    public ResultBean faild() {
        return this.status(FAIL).msg("失败");
    }
    /**
     * @Description  ：默认失败格式，返回是吧信息
     * @author       : xcl
     * @param        : [e]
     * @return       : xcl.com.pro.com.xcl.sso.core.bean.ResultBean
     * @date         : 2018/12/14 14:34
     */
    public ResultBean faild(Throwable e) {
        return faild().msg(e);
    }
    /**
     * @Description  ：参数验证失败使用
     * @author       : xcl
     * @param        : [message]
     * @return       : xcl.com.manager.core.bean.ResultBean
     * @date         : 2019/1/19 11:28
     */
    public ResultBean validateFailed(String message){
        return this.status(VALIDATE_FAILED).msg(message);
    }
    /**
     * @Description  ：未登录时使用
     * @author       : xcl
     * @return       : xcl.com.manager.core.bean.ResultBean
     * @date         : 2019/1/19 11:30
     */
    public ResultBean unauthorized(){
        return this.status(UNAUTHORIZED).msg("当前未登录或token已经过期");
    }
    /**
     * @Description  ：未授权时使用
     * @author       : xcl
     * @return       : xcl.com.manager.core.bean.ResultBean
     * @date         : 2019/1/19 11:31
     */
    public ResultBean forbidden(){
        return this.status(FORBIDDEN).msg("该用户无相关权限");
    }

    //    /**
    //     * @Description  ：返回分页成功数据
    //     * @author       : xcl
    //     * @param        : [data]
    //     * @return       : xcl.com.manager.core.bean.ResultBean
    //     * @date         : 2019/1/19 11:31
    //     */
    //    public ResultBean pageSuccess(List<T> data) {
    //        Map<String, Object> result = new HashMap<>();
    //        result.put("pageSize", pageInfo.getPageSize());
    //        result.put("totalPage", pageInfo.getPages());
    //        result.put("total", pageInfo.getTotal());
    //        result.put("pageNum", pageInfo.getPageNum());
    //        result.put("list", pageInfo.getList());
    //    }
}
