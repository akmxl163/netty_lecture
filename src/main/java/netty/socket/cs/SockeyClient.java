package netty.socket.cs;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SockeyClient {

    public static void main(String[] args) throws InterruptedException{
        EventLoopGroup clientGroop = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroop)
                    .channel(NioSocketChannel.class)
                    .handler(new SockeyClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8081).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            clientGroop.shutdownGracefully();
        }
    }
}
