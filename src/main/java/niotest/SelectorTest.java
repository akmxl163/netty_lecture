package niotest;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class SelectorTest {

    public static void main(String[] args) throws Exception {
        System.out.println("好戏开始了.............");
        int[] ports = {8082};
        Selector selector = Selector.open();
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(ports[i]));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
//        HashMap<SocketChannel, String> clientMap = new HashMap<>();
        while (true) {
            if (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);//设置不启动Nagle算法
                        socketChannel.register(selector, SelectionKey.OP_READ);

//                        clientMap.put(socketChannel, socketChannel.getRemoteAddress().toString());
                        System.out.println("注册读取通道成功：" + socketChannel);
                    } else if (selectionKey.isReadable()) {

                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        while (true) {
                            ByteBuffer readBuffer = ByteBuffer.allocate(512);
                            int readLen = socketChannel.read(readBuffer);
                            if (readLen < 0) break;
                            if(readLen == 0) continue;
                            //读取到数据则写回客户端
//                            Charset charset = Charset.forName("utf-8");
//                            String clientMsg = String.valueOf(charset.decode(buffer).array());
                            String clientMsg = new String(readBuffer.array(),0, readLen, StandardCharsets.UTF_8);
                            System.out.println(socketChannel + ":" + clientMsg);

//                            for(Map.Entry<SocketChannel, String> entry: clientMap.entrySet()){
                            String remoteAddr = socketChannel.getRemoteAddress().toString();

                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
//                                if (remoteAddr.equals(entry.getValue())) {
//                                    writeBuffer.put(("自己："+clientMsg).getBytes());
//                                    writeBuffer.flip();
//                                    entry.getKey().write(writeBuffer);
//                                }else {
                            writeBuffer.put((remoteAddr + "的消息:" + clientMsg + "\r").getBytes(StandardCharsets.UTF_8));
                            writeBuffer.flip();
                            socketChannel.write(writeBuffer);
                            System.out.println("写入消息：[" + clientMsg + "]成功");
                        }
                    }

                }
                iterator.remove();
            }
        }
    }
}
