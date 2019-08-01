package netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class TestHttpServerHandler2 extends SimpleChannelInboundHandler<HttpObject> {

    //处理方式
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest)msg;
            System.out.println(request.getClass().getName());
            if("/favicon.ico".equals(request.uri())){
                ;
            }
            //构建返回消息
            ByteBuf content = Unpooled.copiedBuffer("hello 2222", CharsetUtil.UTF_8);
            //返回对象
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    content
            );

            //设置response head
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");//简单字符串
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());//响应长度

            //写入并强制刷新
            ctx.writeAndFlush(response);
            ctx.channel().close();
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added2.");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered2.");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered2.");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active2.");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive2.");
        super.channelInactive(ctx);
    }
}
