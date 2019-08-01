package netty.socket.cs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class SockeyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //打印地址和消息内容
        System.out.println("server get addr:" + ctx.channel().remoteAddress() + "," + msg);
        //返回一个UUID
        ctx.channel().writeAndFlush("from socket server:" + UUID.randomUUID());
    }

    //socket一般会重写异常事件,关闭连接
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
