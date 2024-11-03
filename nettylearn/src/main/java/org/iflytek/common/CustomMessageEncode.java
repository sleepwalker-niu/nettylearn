package org.iflytek.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.iflytek.model.CustomRequestBody;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
@Slf4j
public class CustomMessageEncode extends MessageToByteEncoder<CustomMessage> {
    @Override
    public void encode(ChannelHandlerContext ctx, CustomMessage msg, ByteBuf out) throws Exception {
        if (Objects.isNull(msg)) {
            log.info("the Message is Null!!!");
            return;
        }
        CustomHeader header = msg.getHeader();
        out.writeLong(header.getReqId());
        out.writeByte(header.getReqType());

        Object body = msg.getBody();

        if (Objects.nonNull(body)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(body);
            byte[] bytes = baos.toByteArray();
            //设定消息长度
            out.writeInt(bytes.length);
            //编写消息实际内容---请求体
            out.writeBytes(bytes);
            log.info("the final Message is: {} ",out);
        } else {
            log.info("the Length of Message is Zero!!!");
            out.writeInt(0);
        }
    }
}
