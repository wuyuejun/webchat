# webchat
简易版本的 网页客服聊天项目

利用 SpringBoot整合 webSocket  做的一个简易版本的 网页客服聊天项目

>1.WebSocket是HTML5开始提供的一种在单个 TCP 连接上进行全双工通讯的协议。
在WebSocket API中，浏览器和服务器只需要做一个握手的动作，
然后，浏览器和服务器之间就形成了一条快速通道。两者之间就直接可以数据互相传送。  
>2.浏览器通过 JavaScript 向服务器发出建立 WebSocket 连接的请求，
连接建立以后，客户端和服务器端就可以通过 TCP 连接直接交换数据。  
>3.当你获取 WebSocket 连接后，你可以通过 send() 方法来向服务器发送数据，
并通过 onmessage 事件来接收服务器返回的数据。  
得益于W3C国际标准的实现，我们在浏览器JS就能直接创建WebSocket对象，
再通过简单的回调函数就能完成WebSocket客户端的编写，非常简单！

后续应该添加多线程进行  交互数据处理
并且  交互的数据  进行存储问题
都有待后续解决


## 项目说明
该客服聊天项目,客服数量固定,客服可以接待的客户数量固定
每一个连接上去的客户都会在连接成功,马上分配一个客服
