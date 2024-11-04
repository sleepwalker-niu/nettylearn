package org.iflytek;
import org.iflytek.server.NettyCustomServer;
import org.iflytek.server.NettyHttpServer;

public class ServerStarter {
    public static void main(String[] args) throws InterruptedException {
        // 启动服务端
        new Thread(() -> {
            try {
//                new NettyHttpServer(8080).start();
                new NettyCustomServer(8080).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
