package org.iflytek.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (request.method() == HttpMethod.GET) {
            handleGetRequest(ctx);
        } else if (request.method() == HttpMethod.POST) {
            handlePostRequest(ctx, request);
        }
    }

    private void handleGetRequest(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                ctx.alloc().buffer().writeBytes("Hello from Netty Server".getBytes(StandardCharsets.UTF_8))
        );
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        ctx.writeAndFlush(response);
    }

    private void handlePostRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String body = request.content().toString(StandardCharsets.UTF_8);
        // 使用 Jackson 将 JSON 转为 Map（可根据实际需求处理）
        Object parsedBody = mapper.readValue(body, Object.class);
        System.out.println("Received POST request with body: " + parsedBody);

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                ctx.alloc().buffer().writeBytes("Received".getBytes(StandardCharsets.UTF_8))
        );

        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        ctx.writeAndFlush(response);
        System.out.println("flushed");
    }
}

