package netty.socket.cs;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

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

    //增加向下转发的功能，充当客户端
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        // ***************** 使用当前的EventLoop ************************
        EventLoop eventLoop = ctx.channel().eventLoop();
        try {
            Bootstrap clientBootStrap = new Bootstrap();
            clientBootStrap.group(eventLoop).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline.addLast("lengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                    0, 4, 0, 4));
                            channelPipeline.addLast("lengthFieldPrepender", new LengthFieldPrepender(4));
                            channelPipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
                            channelPipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
                            channelPipeline.addLast("sockeyClientHandler", new SockeyClientHandler());
                        }
                    });
            ChannelFuture channelFuture = clientBootStrap.connect("com.yibao", 8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            eventLoop.shutdownGracefully();
        }

    }
}
