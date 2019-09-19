package netty.mycodec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import netty.mycodec.spc_codec.CodecMsgToByteEncoder;
import netty.mycodec.spc_codec.CodecReplayingDecoder;

public class CodecClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new CodecMsgToByteEncoder());
        pipeline.addLast(new CodecReplayingDecoder());
        pipeline.addLast(new CodecClientHandler());
    }
}
