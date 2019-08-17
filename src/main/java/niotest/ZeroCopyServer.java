package niotest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ZeroCopyServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ServerSocket socket = ssChannel.socket();
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress( 8899));

        ByteBuffer bf = ByteBuffer.allocate(4096);
        while (true){
            SocketChannel socketChannel = ssChannel.accept();
            socketChannel.configureBlocking(true);

            int readCount = 0;
            while (-1 != readCount){
                try {
                    readCount = socketChannel.read(bf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bf.rewind();
            }
        }
    }
}
