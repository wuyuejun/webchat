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
=================================================
=================================================
AOP原理和AbstractRoutingDataSource抽象类
1、AOP：这个东西。。。不恰当的说就是相当于拦截器，只要满足要求的都会被拦截过来，然后进行一些列的操作。
2、AbstractRoutingDataSource：这个类是实现多数据源的关键，他的作用就是动态切换数据源，
实质：有多少个数据源就存多少个数据源在 targetDataSources
（是 AbstractRoutingDataSource 的一个 map 类型的属性，其中 value 为每个数据源，key表示每个数据源的名字）
这个属性中，
然后根据 determineCurrentLookupKey（）这个方法获取当前数据源在map中的key值，
然后 determineTargetDataSource（）方法中动态获取当前数据源，
如果当前数据源不存并且默认数据源也不存在就抛出异常。

AbstractRoutingDataSource
类：
大概的作用就是根据
getConnection()
根据查找lookup key键对不同目标数据源的调用，
通常是通过(但不一定)某些线程绑定的事物上下文来实现

3、定义多个数据源：怎么定义就不多说了，和方法一是一样的，主要是将定义好的多个数据源放在动态数据源中。
4、定义AOP：就是不同业务切换不同数据库的入口。如果觉得 execution 太长不愿意写，就可以定义一个注解来实现
=============================================================================================================2019/3/16

定义了一个 Log 注解，然后希望被 @Log 注解的类，它的所有方法的执行，都可以被 Spring 的 AOP 拦截到
Log 注解：
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Log {
    String value() default "";
}
=====================================================
LogAdvice：
@Aspect
@Component
public class LogAdvice {
    @Pointcut("@target(ssm.annotation.Log)")
    private void advice() {
    }
    @Before("advice()")
    public void doBefore(JoinPoint jp) {
        String methodInfo = getMethodInfo(jp);
        System.out.println(methodInfo);
    }
    ...
}
=====================================================
报错：
Spring:INFO CglibAopProxy: Unable to proxy method 
[protected final void org.springframework.transaction.support.AbstractPlatformTransactionManager.
resume(java.lang.Object,org.springframework.transaction.support.AbstractPlatformTransactionManager$SuspendedResourcesHolder) 
throws org.springframework.transaction.TransactionException] 
because it is final: All calls to this method via a proxy will NOT be routed to the target instance.
将
@Pointcut("@target(ssm.annotation.Log)")
改为
@Pointcut("@annotation(ssm.annotation.Log)")
不会报错，但是只能在方法上使用 @Log 才可以在 AOP 中拦截到，这意味着要拦截一个类的所有方法，只能在每个方法上都加上这个注解

使用 @within("ssm.annotation.Log")，可以拦截被 @Log 注解的类的所有方法

@annotation 这个表达式只能针对方法。 
如果要实现你想要的效果，那就得用 @execution(* * *(..)) 切入所有类所有方法。
然后在 切入点逻辑里面判断该类有没有 @Log 注解
