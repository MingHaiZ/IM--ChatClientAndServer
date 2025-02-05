package com.easychat.webSocket.netty;

import com.easychat.entity.dto.TokenUserInfoDto;
import com.easychat.redis.RedisComponent;
import com.easychat.utils.StringTools;
import com.easychat.webSocket.ChannelContextUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class HandlerWebSocket extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(HandlerWebSocket.class);
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private ChannelContextUtils channelContextUtils;

    @Override

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
//        logger.info("收到消息{}", msg.text());
//        channel.writeAndFlush(new TextWebSocketFrame("客户端返回消息" + msg.text()));
        Attribute<String> attr = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attr.get();
//        logger.info("收到用户[{}]的信息: {}", userId, msg.text());
        redisComponent.saveUserHeartBeat(userId);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete e = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String s = e.requestUri();
            logger.info("url{}", s);
            String token = getToken(s);
            if (token == null) {
                ctx.writeAndFlush(new TextWebSocketFrame("Token校验不通过!"));
                ctx.close();
            }
            TokenUserInfoDto tokenUserInfo = redisComponent.getTokenUserInfo(token);
            if (tokenUserInfo == null) {
                ctx.writeAndFlush(new TextWebSocketFrame("Token校验不通过"));
                ctx.close();
                return;
            }
            logger.info("redisToken: {}", tokenUserInfo.getToken());
            logger.info("token:{}", token);
            channelContextUtils.addContext(tokenUserInfo.getUserId(), ctx.channel());
//            ctx.writeAndFlush(new TextWebSocketFrame("成功链接到服务器"));
        }
    }

    private String getToken(String uri) {
        if (StringTools.isEmpty(uri) || uri.indexOf("?") == -1) {
            return null;
        }
        String[] queryParams = uri.split("\\?");
        if (queryParams.length != 2) {
            return null;
        }
        String[] params = queryParams[1].split("=");
        if (params.length != 2) {
            return null;
        }
        return params[1];
    }

    /**
     * 通道就绪后调用,一般用来做初始化
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("有新的链接加入");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        logger.info("有链接断开");
        channelContextUtils.removeContext(ctx.channel());
    }
}
