package netty.custom_protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolEncoder extends MessageToByteEncoder<ProtocolBean> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolBean msg, ByteBuf out) throws Exception {
        System.out.println("protocolEncoder entry,msg=" + msg);
        out.writeInt(msg.getHead());
        out.writeBytes(msg.getContent());
    }
}
