package org.iflytek.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.iflytek.model.CustomRequestBody;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CustomMessageDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //首先我们需要先处理我们的消息头Header部分
        //其中Header包含 reqId reqType Len
        if (Objects.isNull(in)){
            log.info("the ByteBuf of In is null!!!");
            return;
        }
        CustomHeader header = new CustomHeader();
        header.setReqId(in.readLong());
        header.setReqType(in.readByte());
        header.setReqLen(in.readInt());
        //这里由于我们定义的协议的格式，所以不能吧读取length操作提前哦
        if (header.getReqLen()<=0){
            log.info("the Length of Message is Zero!!!");
            return;
        }
        byte[] body = new byte[header.getReqLen()];
        in.readBytes(body);
        ByteArrayInputStream bais = new ByteArrayInputStream(body);
        ObjectInputStream ois = new ObjectInputStream(bais);
        //进行反序列化
        Object bodyData = ois.readObject();
        if (Objects.isNull(body)){
            log.warn("the Body of Message is Null!!!");
        }

        CustomMessage message = new CustomMessage();
        message.setHeader(header);

        message.setBody((CustomRequestBody) bodyData);
        //添加数据对象给下一个Handler处理
        out.add(message);
        log.info("the final Message is: {} ",message);
    }

}
