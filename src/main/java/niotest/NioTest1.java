package niotest;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Buffer的Scattering和Gathering
 */
public class NioTest1 {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        int messageLen = 2+3+4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        System.out.println("system started.....");
        SocketChannel sc = ssc.accept();
        while (true){
            int readLen=0;
            while (readLen<messageLen){
                long rst = sc.read(buffers);
                readLen += rst;
                System.out.println("readLen="+readLen);
                Arrays.stream(buffers)
                        .map(buffer->"readbuffer:position="+buffer.position()+",limit="+buffer.limit())
                        .forEach(System.out::println);
            }

            Arrays.stream(buffers).forEach(ByteBuffer::flip);

            int writeLen=0;
            while (writeLen<messageLen){
                long rst = sc.write(buffers);
                writeLen += rst;
                System.out.println("writeLen="+writeLen);
            }

            Arrays.stream(buffers).forEach(ByteBuffer::clear);
            System.out.println("readLen="+readLen+", writeLen="+writeLen+", messageLen="+messageLen);
        }
    }
}
