package longheartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HeartClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup clientGroop = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroop)
                    .channel(NioSocketChannel.class)
                    .handler(new HeartClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8081).sync();
            Channel channel = channelFuture.channel();
            //循环接受输入
//            channel.writeAndFlush("my client message," + LocalDateTime.now());
            while (true){
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                channel.writeAndFlush(br.readLine() + "\r\n");
            }
//            channelFuture.channel().closeFuture().sync();
        } finally {
            clientGroop.shutdownGracefully();
        }
    }
}
