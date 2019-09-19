package netty.custom_protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class ProtocolHandler extends SimpleChannelInboundHandler<ProtocolBean> {

    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(123456L);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolBean msg) throws Exception {
        System.out.println("protocal server entry,read " + (++count) + " msg=" + msg);
        byte[] content = UUID.randomUUID().toString().getBytes(Charset.forName("utf8"));
        int head = content.length;
        ctx.writeAndFlush(new ProtocolBean(head, content));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
