package org.iflytek.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;

public class HttpClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        HttpContent content = (HttpContent) msg;
        FullHttpResponse content = (FullHttpResponse) msg;
        ByteBuf buf = content.content();

        System.out.println("Response from server: " + buf.toString(CharsetUtil.UTF_8));
        buf.release();
    }
}
