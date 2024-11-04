package org.iflytek.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.iflytek.common.CustomMessage;
import org.iflytek.model.CustomRequestBody;

import java.util.Objects;
@Slf4j
public class CustomRequestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("I'm here~ CustomRequestHandler~");
        CustomMessage message = (CustomMessage) msg;
        if (Objects.isNull(message)) {
            log.info("the Message is Null!!!");
            return;
        }
        log.info("Server Receive Message : {}", message);
        message.setBody("This is Server' response Message");
        message.getHeader().setReqType('B');
        //将消息写回客户端
        ctx.writeAndFlush(message);
    }
}
