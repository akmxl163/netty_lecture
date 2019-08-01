package netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) {
        TestServer ts = new TestServer();
        try {
            ts.server();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void server() throws InterruptedException{
        //2个线程组,boss接受连接，worker处理连接
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {

            //实现了ServletChannel，方便的启动它
            ServerBootstrap sb = new ServerBootstrap();
            //链式调用
            sb.group(boss,worker)
                    .channel(NioServerSocketChannel.class)//反射创建
                    .childHandler(new TestServerInitializer())//子处理器
            ;
            ChannelFuture cf = sb.bind(8081).sync();
            cf.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
