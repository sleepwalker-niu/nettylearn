package org.iflytek.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;

public class HttpClientHandler2 extends SimpleChannelInboundHandler<FullHttpResponse> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) {

        System.out.println("Response from server: " + msg.content().toString(CharsetUtil.UTF_8));

    }
}
