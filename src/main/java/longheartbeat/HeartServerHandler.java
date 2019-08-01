package longheartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    System.out.println("读超时");
                    break;
                case WRITER_IDLE:
                    System.out.println("写超时");
                    break;
                case ALL_IDLE:
                    System.out.println("读写超时");
                    break;
            }
        }
        //关闭连接
        ctx.channel().close();
    }
}
