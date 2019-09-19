package netty.mycodec.spc_codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class MyMsgToMsgDecoder extends MessageToMessageDecoder<Long> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Long msg, List<Object> out) throws Exception {
        System.out.println("msgToMsgDecoder entry ant invoked,msg is:" + msg);
        out.add(String.valueOf(msg));
    }
}
