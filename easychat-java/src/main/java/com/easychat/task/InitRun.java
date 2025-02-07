package com.easychat.task;

import com.easychat.webSocket.netty.HandlerWebSocket;
import com.easychat.webSocket.netty.NettySocketStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class InitRun implements ApplicationRunner {

    @Resource
    private NettySocketStarter nettySocketStarter;

    @Resource
    private HandlerWebSocket handlerWebSocket;

    private static final Logger logger = LoggerFactory.getLogger(InitRun.class);

    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            executorService.execute(() -> {
                nettySocketStarter.startNettyServer();
            });
            logger.info("SpringBoot启动成功!");
        } catch (Exception e) {

        }
    }
}
