package protocbuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class PbClientHandler extends SimpleChannelInboundHandler<MyData.MyMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyData.MyMsg msg) throws Exception {
        System.out.println("获取到服务端发送的消息：");
        System.out.println(msg);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int i = new Random().nextInt(3);
        MyData.MyMsg msg = null;
        if (i==0){
            msg = MyData.MyMsg.newBuilder().setMyType(MyData.MyMsg.Type.PersonType)
                    .setPerson(MyData.Person.newBuilder()
                            .setAddr("成都")
                            .setAge(22)
                            .setName("duding").build())
                    .build();
        }else if(i==1){
            msg = MyData.MyMsg.newBuilder().setMyType(MyData.MyMsg.Type.DogType)
                    .setDog(MyData.Dog.newBuilder()
                            .setColor("Yellow")
                            .setName("teddy").build())
                    .build();
        }else {
            msg = MyData.MyMsg.newBuilder().setMyType(MyData.MyMsg.Type.CatType)
                    .setCat(MyData.Cat.newBuilder()
                            .setDate("2019-08-03")
                            .setName("van lake").build())
                    .build();
        }
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
