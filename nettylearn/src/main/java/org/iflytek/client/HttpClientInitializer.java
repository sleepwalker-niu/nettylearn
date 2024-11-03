package org.iflytek.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(new HttpClientCodec());
        ch.pipeline().addLast(new HttpObjectAggregator(65536));
//        ch.pipeline().addLast(new HttpResponseDecoder());//HTTP编码
//        ch.pipeline().addLast(new HttpRequestEncoder());//HTTP解码
        ch.pipeline().addLast(new HttpClientHandler());
//        ch.pipeline().addLast(new HttpClientHandler2());

    }
}
