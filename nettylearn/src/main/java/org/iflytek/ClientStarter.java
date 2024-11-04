package org.iflytek;

import org.iflytek.client.NettyCustomClient;
import org.iflytek.client.NettyHttpClient;

public class ClientStarter {
    public static void main(String[] args) throws InterruptedException {
        String host = "localhost"; // 服务器地址
        int port = 8080;           // 服务器端口

//        NettyHttpClient client = new NettyHttpClient(host, port);
        NettyCustomClient client = new NettyCustomClient(host, port);
        client.start(); // 启动客户端并发送请求
    }
}
