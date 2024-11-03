package org.iflytek.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.iflytek.common.CustomMessageDecode;
import org.iflytek.common.CustomMessageEncode;

public class CustomServerInitalizer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new CustomMessageEncode());
        ch.pipeline().addLast(new CustomMessageDecode());
        ch.pipeline().addLast(new HttpRequestHandler());
    }
}
