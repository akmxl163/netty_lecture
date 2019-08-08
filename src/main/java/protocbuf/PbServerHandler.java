package protocbuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PbServerHandler extends SimpleChannelInboundHandler<MyData.MyMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyData.MyMsg msg) throws Exception {
        System.out.println("获取到客户端发送的消息：");
        System.out.println(msg);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
