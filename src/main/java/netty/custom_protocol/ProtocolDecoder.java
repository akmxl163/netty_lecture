package netty.custom_protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("protocolDecoder entry,buffer length=" + in.readableBytes());
        int head = in.readInt();
        byte[] content = new byte[head];
        in.readBytes(content);
        ProtocolBean pb = new ProtocolBean(head, content);
        out.add(pb);
    }
}
