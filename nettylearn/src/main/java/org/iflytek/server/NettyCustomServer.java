package org.iflytek.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NettyCustomServer {
    private static final Logger logger = LogManager.getLogger(NettyCustomServer.class);
    private final int port;

    public NettyCustomServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new HttpServerInitializer())
                    .childOption(ChannelOption.TCP_NODELAY, true)      // 禁用Nagle算法，适用于小数据即时传输
                    .childOption(ChannelOption.SO_SNDBUF, 65535)       // 设置发送缓冲区大小
                    .childOption(ChannelOption.SO_RCVBUF, 65535)       // 设置接收缓冲区大小
                    .childHandler(new CustomServerInitalizer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_REUSEADDR, true);         // 允许端口重用;

            ChannelFuture f = b.bind(port).sync();
            logger.info("Netty HTTP Server started on port {}", port);
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.info("Netty HTTP Server stopped.");
        }
    }
}

