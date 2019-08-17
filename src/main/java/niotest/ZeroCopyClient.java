package niotest;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class ZeroCopyClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String file = "G:\\迅雷下载\\CentOS-7-x86_64-DVD-1810.iso";
        FileChannel fileChannel = new FileInputStream(file).getChannel();
        long start = System.currentTimeMillis();
        long transferLen = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送字节：" + transferLen);
        System.out.println("耗时：" + (System.currentTimeMillis()-start));
        fileChannel.close();
    }
}
