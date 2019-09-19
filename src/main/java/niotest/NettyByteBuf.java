package niotest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class NettyByteBuf {
    public static void main(String[] args) {
        heapToDirect();
    }

    //堆内缓存切换到堆外内存，反之同理。
    //注意directBuf长度一定要刚好，甚至不能长了
    public static void heapToDirect(){
        ByteBuf heapBuf = Unpooled.buffer(10);
        heapBuf.writeInt(233);
        ByteBuf directBuf = Unpooled.directBuffer(heapBuf.readableBytes());
        heapBuf.readBytes(directBuf);
        System.out.println("get direct buffer int=" + directBuf.readInt());
    }
    public static void autoIncrese(){

        ByteBuf bf = Unpooled.buffer(10);
        System.out.println(bf.capacity());
        for (int i = 0; i < 16; i++) {
            bf.writeByte(i);
        }
        System.out.println(bf.readerIndex());
        System.out.println(bf.writerIndex());
        System.out.println(bf.capacity());
        System.out.println(bf.maxCapacity());
        ByteBuf directBuf = Unpooled.directBuffer();
    }

    public static void compsiteBufFunc(){
        CompositeByteBuf cbb = Unpooled.compositeBuffer();
        ByteBuf heapBf = Unpooled.buffer(10);
        ByteBuf directBf = Unpooled.directBuffer(6);

        //添加多个
        cbb.addComponents(heapBf, directBf);
        cbb.removeComponent(0);
        cbb.forEach(System.out::println);
    }
    public static void func2(){
        ByteBuf bf = Unpooled.copiedBuffer("涨hello world".toCharArray(), Charset.forName("utf-8"));
        if(bf.hasArray()) {
            byte[] bb = bf.array();
            System.out.println(new String(bb, Charset.forName("utf-8")));
        }
        for (int i = 0; i < bf.array().length; i++) {

            System.out.println(bf.getByte(i));
        }
        System.out.println(bf.getCharSequence(0, 4, Charset.forName("utf-8")));
        System.out.println(bf.getCharSequence(4, 6, Charset.forName("utf-8")));
    }
    public static void func1(){
        ByteBuffer bff = ByteBuffer.allocateDirect(16);
        ByteBuf bf = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            bf.writeByte(i);
        }
        for (int i = 0; i < bf.capacity(); i++) {
            System.out.println(bf.readByte());
        }
        System.out.println(bf.arrayOffset());
        System.out.println(bf.readerIndex());
        System.out.println(bf.writerIndex());
        System.out.println(bf.capacity());

        System.out.println(bf.readableBytes());//writerIndex - readerIndex
    }
}
