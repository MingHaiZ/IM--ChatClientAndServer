package com.easychat.webSocket.netty;

import com.easychat.config.AppConfig;
import com.easychat.entity.constants.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class NettySocketStarter {

    private static final Logger log = LoggerFactory.getLogger(NettySocketStarter.class);
    private static EventLoopGroup BOSS_GROUP = new NioEventLoopGroup(1);
    private static EventLoopGroup WORKER_GROUP = new NioEventLoopGroup();
    @Resource
    private HandlerWebSocket handlerWebSocket;
    @Resource
    private AppConfig appConfig;

    @PreDestroy
    public void close() {
        BOSS_GROUP.shutdownGracefully();
        WORKER_GROUP.shutdownGracefully();
    }

    public void startNettyServer() {

        try {
            ChannelFuture sync = new ServerBootstrap()
                    .group(BOSS_GROUP, WORKER_GROUP)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
//                        设置几个重要的处理器
//                        对Http协议的支持,使用http的解码器,编码器
                            pipeline.addLast(new HttpServerCodec());
//                        聚合解码 httpRequest/httpContent/lastHttpContent到fullHttpRequest
//                        保证接收到的http请求的完整性
                            pipeline.addLast(new HttpObjectAggregator(60 * 1024));
//                        心跳机制  long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit
//                        readIdleTime读超时时间 即测试端一定时间内未接收到被测试的消息
//                        writerIdleTime写超时时间 即测试段一定时间内未向测试端发送测试消息
//                        allIdleTime所有类型的超时时间
                            pipeline.addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new HandlerHeartBeat());
//                        将http协议升级到ws协议,对websocket支持
                            pipeline.addLast(new WebSocketServerProtocolHandler(Constants.WS_PROTOCOL, null, true, 64 * 1024, true, true, 10000L));
                            pipeline.addLast(handlerWebSocket);

                        }
                    })
                    .bind(appConfig.getWsPort())
                    .sync();
            log.info("Netty客户端启动成功");
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
