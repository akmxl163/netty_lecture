package niotest;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SelectorTest {

    public static void main(String[] args) throws Exception {
        System.out.println("好戏开始了.............");
        int[] ports = {8081};
        Selector selector = Selector.open();
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(ports[i]));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        HashMap<SocketChannel, String> clientMap = new HashMap<>();
        while (true) {
            if (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        clientMap.put(socketChannel, socketChannel.getRemoteAddress().toString());
                        System.out.println("注册读取通道成功：" + socketChannel);
                    } else if (selectionKey.isReadable()) {

                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                        while (true) {
                            ByteBuffer buffer = ByteBuffer.allocate(512);
                            int read = socketChannel.read(buffer);
                            if (read <= 0) break;
                            //读取到数据则写回客户端
//                            Charset charset = Charset.forName("utf-8");
//                            String clientMsg = String.valueOf(charset.decode(buffer).array());
                            String clientMsg = new String(buffer.array());
                            System.out.println(socketChannel + ":" + clientMsg);

                            for(Map.Entry<SocketChannel, String> entry: clientMap.entrySet()){
                                String remoteAddr = socketChannel.getRemoteAddress().toString();

                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                if (remoteAddr.equals(entry.getValue())) {
                                    writeBuffer.put(("自己："+clientMsg).getBytes());
                                    writeBuffer.flip();
                                    entry.getKey().write(writeBuffer);
                                }else {
                                    writeBuffer.put((entry.getValue() + ":" + clientMsg).getBytes());
                                    writeBuffer.flip();
                                    entry.getKey().write(writeBuffer);
                                }
                            }
                        }
                    } else if(selectionKey.isValid()){

                    }
                    iterator.remove();
                }
            }
        }
    }
}
