$(function(){
  let app = function () {
    let webSocket;
    //静态变量
    const COMMON = {
      URL : {
        WEBSOCKETURL : "ws://localhost:8084/websocket?type=1&name="
      },
      ELEMENT : {
        USERNAME : $("#username"),
        MESSAGE : $("#message"),
        CONTENT : $("#text"),
        ONLINEUSER : $("#onLineUser"),
        personID : $("#personID"),
        personName : $("#personName")
      },
      BTN : {
        SEND : $("#send"),
        CLOSE : $("#close")
      }
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
      COMMON.ELEMENT.MESSAGE.append(innerHTML);
    }
    //直接关闭websocket的连接
    function closeWebSocket() {
      webSocket.close();
    }
    //连通之后的回调事件
    function onopent (evt) {
      console.log(evt);
      // setMessageInnerHTML("已经连通了websocket");
    }
    //接收后台服务端的消息
    function onmessage(evt) {
      var received_msg = evt.data;
      console.log("数据已接收:" + received_msg);
      var obj = JSON.parse(received_msg);
      if(obj.type == 1){
        let messageContent = obj.message;
        // setMessageInnerHTML(messageContent);
      }else if (obj.type == 2) {
        let messageContent = obj.message;
        setMessageInnerHTML("<span><font color='red'>用户"+messageContent.name+"已下线!</span>");
      }else if (obj.type == 3) {
        //清空一次
        $("#onLineUser").empty();
        let messageContent = obj.message;
        var option = "<option value=''>--所有--</option>";
        for (var i = 0; i < messageContent.length; i++) {
          option += "<option value='"+messageContent[i].userId+"'>" + messageContent[i].name + "</option>"
        }
        $("#onLineUser").append(option);
      }else  if (obj.type == 4) {
        let fromUser = obj.fromUser;
        let messageContent = obj.message;
        setMessageInnerHTML('<div class="atalk"><span>'+fromUser.name+'说: '+'messageContent'+'</span></div>');
      }else  if (obj.type == 5) {
        //个人信息
        let messageContent = obj.message;
        COMMON.ELEMENT.personID.val(messageContent.userId);
        COMMON.ELEMENT.personName.val(messageContent.name);
      }
    }
    //连接关闭的回调事件
    function onclose() {
      console.log("连接已关闭...");
      setMessageInnerHTML("连接已经关闭....");
    }
    
    function send() {
      //消息接收者
      let getUser = COMMON.ELEMENT.ONLINEUSER.find("option:selected").text();
      let getUserId = COMMON.ELEMENT.ONLINEUSER.val();
      let message = {
        "message": COMMON.ELEMENT.CONTENT.val(),
        "sendUserName" : COMMON.ELEMENT.personName.val(),
        "sendUserId" : COMMON.ELEMENT.personID.val(),
        "getUserName": getUser,
        "getUserId" : getUserId
      };
      setMessageInnerHTML('<div class="btalk"><span>'+message.sendUserName+'说: '+message.message+'</span></div>');
      webSocket.send(JSON.stringify(message));
      //内容输入框清空
      COMMON.ELEMENT.CONTENT.val("");
    }

    if ("WebSocket" in window) {
      webSocket = new WebSocket(COMMON.URL.WEBSOCKETURL+ COMMON.ELEMENT.USERNAME.val());
      //连通之后的回调事件
      webSocket.onopen = onopent;
      //接收后台服务端的消息
      webSocket.onmessage = onmessage;
      //连接关闭的回调事件
      webSocket.onclose = onclose;
    }else{
      // 浏览器不支持 WebSocket
      alert("您的浏览器不支持 WebSocket!");
    }



    //页面监听
    function setLister() {
      //关闭按钮操作
      COMMON.BTN.CLOSE.on("click",function () {
        closeWebSocket();
      });
      //发送按钮操作
      COMMON.BTN.SEND.on("click",function () {
        send();
      });
    }

    return {
      init:function () {
        setLister();
      }
    }
  }();
  app.init();
});