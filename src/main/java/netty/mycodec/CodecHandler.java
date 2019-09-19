package netty.mycodec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CodecHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(123456L);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server entry and invoked.");
        System.out.println("server get message: " + msg);
        ctx.writeAndFlush(123456L);
        ctx.writeAndFlush("a missed msg");
    }
}
