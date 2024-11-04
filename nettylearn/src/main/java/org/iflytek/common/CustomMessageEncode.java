package org.iflytek.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.iflytek.model.CustomRequestBody;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Objects;
@Slf4j
public class CustomMessageEncode extends MessageToByteEncoder<CustomMessage> {
    @Override
    public void encode(ChannelHandlerContext ctx, CustomMessage msg, ByteBuf out) throws Exception {
        System.out.println("I'm here~ CustomMessageEncode~");
        if (Objects.isNull(msg)) {
            log.info("the Message is Null!!!");
            System.out.println("the Message is Null!!!");
            return;
        }
        CustomHeader header = msg.getHeader();
        out.writeLong(header.getReqId());
        out.writeByte(header.getReqType());
        System.out.println("test1");
        String body = msg.getBody();
        System.out.println("test2");
//        if (Objects.nonNull(body)) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        oos.writeObject(body);
//        byte[] bytes = baos.toByteArray();
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        // 计算消息体的长度
        int length = bodyBytes.length;

        System.out.println("test3");
//        //设定消息长度
//        out.writeInt(bytes.length);
//        //编写消息实际内容---请求体
//        out.writeBytes(bytes);
        // 写入消息体长度
        out.writeInt(length);
        // 写入消息体
        out.writeBytes(bodyBytes);
        log.info("the final Message is: {} ",out);
        System.out.println("the final Message is:");
//        } else {
//            log.info("the Length of Message is Zero!!!");
//            System.out.println("the Length of Message is Zero!!!");
//            out.writeInt(0);
//        }
    }
}
