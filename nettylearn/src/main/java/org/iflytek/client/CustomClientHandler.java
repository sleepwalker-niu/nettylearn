package org.iflytek.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.iflytek.common.CustomMessage;
import org.iflytek.model.CustomRequestBody;

import java.util.Objects;
@Slf4j
public class CustomClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CustomMessage message = (CustomMessage) msg;
        log.info("Client Receive Message is: {}", message);
        //这里直接调用父类的read方法
        super.channelRead(ctx, msg);
    }
}
