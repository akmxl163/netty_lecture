package protocbuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;

public class PbServerHandler extends SimpleChannelInboundHandler<MyDate.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDate.Person msg) throws Exception {
        System.out.println("获取到客户端发送的消息：");
        System.out.println(Arrays.asList(msg.getName(), msg.getAddr() , msg.getAge()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        MyDate.Person person = MyDate.Person.newBuilder()
//                .setAddr("成都")
//                .setAge(22)
//                .setName("duding").build();
//        ctx.channel().writeAndFlush(person);
        super.channelActive(ctx);
    }
}
