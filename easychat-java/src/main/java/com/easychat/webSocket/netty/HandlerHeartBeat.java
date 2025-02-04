package com.easychat.webSocket.netty;

import com.easychat.webSocket.ChannelContextUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class HandlerHeartBeat extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(HandlerHeartBeat.class);

    @Resource
    private ChannelContextUtils channelContextUtils;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE.equals(event.state())) {
                Channel channel = ctx.channel();
                Attribute<String> attr = channel.attr(AttributeKey.valueOf(channel.id().toString()));
                String userId = attr.get();

                logger.info("用户 {} 心跳超时", userId);
                ctx.writeAndFlush(new TextWebSocketFrame("闲置时间过长,请重新连接"));
                ctx.close();
            } else if (IdleState.WRITER_IDLE.equals(event.state())) {
                ctx.writeAndFlush("heart");
            }
        }
    }

}
