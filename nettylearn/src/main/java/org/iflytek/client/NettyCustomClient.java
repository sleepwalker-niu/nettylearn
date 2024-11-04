package org.iflytek.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.iflytek.common.CustomHeader;
import org.iflytek.common.CustomMessage;
import org.iflytek.model.CustomRequestBody;

import java.nio.charset.StandardCharsets;

public class NettyCustomClient {
    private final String host;
    private final int port;

    public NettyCustomClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
//                    .handler(new HttpClientInitializer());
                    .handler(new CustomClientInitalizer());

            Channel ch = b.connect(host, port).sync().channel();


            sendCustomRequest(ch);


            ch.closeFuture().sync(); // 等待连接关闭
        } finally {
            group.shutdownGracefully();
        }
    }


    private void sendCustomRequest(Channel channel){
        try {
            CustomMessage message = new CustomMessage();
            CustomHeader header = new CustomHeader();
            header.setReqId(System.currentTimeMillis());
            header.setReqType('A');
            message.setHeader(header);
            String body="Hi there,this is a custom message";
            message.setBody(body);

            channel.writeAndFlush(message);
            System.out.println("send Custom request");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
