package netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //存放客户端连接的组Set,需要是静态的，不然每个客户端都保存在不同的对象内部
    //或者在ServerHandler上加@Sharable注解，Initializer添加时，就会使用同一个对象
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("服务器接收到消息:" + channel.remoteAddress() + ", " + msg);
        System.out.println("channelGroup size=" + channelGroup.size());
        channelGroup.forEach(ch -> {
            //是不是自己发送的消息
            if(ch != channel){
                ch.writeAndFlush("from server,"+ channel.remoteAddress() + ", " + msg + "\n");
            } else {
                ch.writeAndFlush("from server,自己：" + msg + "\n");
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("before channelGroup size = " + channelGroup.size());
        Channel channel = ctx.channel();
        //先通知再添加，以通知到自己
        channelGroup.writeAndFlush("服务器：" + channel.remoteAddress() + " 入群\n");
        channelGroup.add(channel);
        System.out.println("after channelGroup size = " + channelGroup.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //通知下线，netty会自动执行移出操作
        channelGroup.writeAndFlush("服务器：" + channel.remoteAddress() + " 离开\n");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("服务器：" + channel.remoteAddress() + "上线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("服务器：" + channel.remoteAddress() + " 下线\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
