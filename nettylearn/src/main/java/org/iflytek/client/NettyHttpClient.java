package org.iflytek.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.buffer.Unpooled;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iflytek.common.CustomHeader;
import org.iflytek.common.CustomMessage;
import org.iflytek.model.CustomRequestBody;

import java.nio.charset.StandardCharsets;

public class NettyHttpClient {
    private final String host;
    private final int port;

    public NettyHttpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitializer());
//                    .handler(new CustomClientInitalizer());

            Channel ch = b.connect(host, port).sync().channel();

            // 发送 GET 请求
            sendGetRequest(ch);

            // 发送 POST 请求
            sendPostRequest(ch);

//            sendCustomRequest(ch);


            ch.closeFuture().sync(); // 等待连接关闭
        } finally {
            group.shutdownGracefully();
        }
    }

    private void sendGetRequest(Channel channel) {
        // 创建 GET 请求
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "");
        request.headers().set(HttpHeaderNames.HOST, host);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");

        // 发送请求
        channel.writeAndFlush(request);
    }

    private void sendPostRequest(Channel channel) {
        try {
            // 创建 POST 请求并构造 JSON 请求体
            CustomRequestBody body=new CustomRequestBody("Hi there");
            String jsonBody=body.toJson();

            FullHttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, "",
                    Unpooled.wrappedBuffer(jsonBody.getBytes(StandardCharsets.UTF_8)));

            // 设置请求头
            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            request.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

            // 发送请求
            channel.writeAndFlush(request);
            System.out.println("send post request");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCustomRequest(Channel channel){
        CustomMessage record = new CustomMessage();
        CustomHeader header = new CustomHeader();
        header.setReqId(System.currentTimeMillis());
        header.setReqType('A');
        record.setHeader(header);
        String body="Hi there,this is a custom message";
        record.setBody(body);

        channel.writeAndFlush(record);
        System.out.println("send Custom request");
    }
}
