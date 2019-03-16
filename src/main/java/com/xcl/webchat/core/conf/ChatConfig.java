package com.xcl.webchat.core.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author :xiaochanglu
 * @Description :
 * @date :2019/3/14 8:59
 */
@Configuration
@Slf4j
public class ChatConfig {

    public ChatConfig() {
        log.info("配置文件初始化加载......");
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        log.info("=====加载serverEndpointExporter=====");
        return new ServerEndpointExporter();
    }
}
