package netty.helloworld;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    //注册Channel后调用
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //添加http请求的编解码器
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //加入处理方法
        pipeline.addLast("httpServerHandle", new TestHttpServerHandler());
        pipeline.addLast("httpServerHandle2", new TestHttpServerHandler2());
    }
}
