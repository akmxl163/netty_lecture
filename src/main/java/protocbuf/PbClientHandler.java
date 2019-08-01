package protocbuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;

public class PbClientHandler extends SimpleChannelInboundHandler<MyDate.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDate.Person msg) throws Exception {
        System.out.println("获取到服务端发送的消息：");
        System.out.println(Arrays.asList(msg.getName(), msg.getAddr() , msg.getAge()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //连接上后开始发送一条数据到服务器
        System.out.println("开始发送到服务器");
        MyDate.Person person = MyDate.Person.newBuilder()
                .setAddr("成都")
                .setAge(22)
                .setName("duding").build();
        ctx.channel().writeAndFlush(person);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
