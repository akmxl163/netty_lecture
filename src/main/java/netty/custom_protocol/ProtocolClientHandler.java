package netty.custom_protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class ProtocolClientHandler extends SimpleChannelInboundHandler<ProtocolBean> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolBean msg) throws Exception {
        System.out.println("protocolClient channelRead0 method invoked,get message:" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10; i++) {
           String msg = "send from protocol client " + (i+1);
            byte[] content = msg.getBytes(Charset.forName("utf8"));
            int head = content.length;
            ctx.writeAndFlush(new ProtocolBean(head, content));
            System.out.println("protocol clientActive send " + (++count));
        }
    }
}
