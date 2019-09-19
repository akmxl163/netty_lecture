package netty.mycodec.spc_codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class CodecByteToMsgDecoder extends ReplayingDecoder<Long> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("begin to decode, buffer length = "+in.readableBytes());

        //long为8字节，小于8的无法转换
        if(in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
