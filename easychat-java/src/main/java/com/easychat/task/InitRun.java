package com.easychat.task;

import com.easychat.webSocket.netty.HandlerWebSocket;
import com.easychat.webSocket.netty.NettySocketStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Component
public class InitRun implements ApplicationRunner {

    @Resource
    private NettySocketStarter nettySocketStarter;

    @Resource
    private HandlerWebSocket handlerWebSocket;

    private static final Logger logger = LoggerFactory.getLogger(InitRun.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            nettySocketStarter.startNettyServer();
        } catch (Exception e) {

        }
    }
}
