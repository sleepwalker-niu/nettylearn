package org.iflytek.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.iflytek.common.CustomMessageDecode;
import org.iflytek.common.CustomMessageEncode;
import org.iflytek.server.HttpRequestHandler;

public class CustomClientInitalizer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new CustomMessageDecode());
        ch.pipeline().addLast(new CustomMessageEncode());
        ch.pipeline().addLast(new CustomClientHandler());
    }

}
