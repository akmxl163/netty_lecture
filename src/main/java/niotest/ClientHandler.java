package niotest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Created by Administrator on 2019/7/14.
 */
public class ClientHandler implements Runnable {


    private SocketChannel socketChannel;
    private Selector selector;
    private int port;
    private String ip;

    public ClientHandler() {
        try {
            port = 8082;//Server.PORT;
            ip = "localhost";
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.connect(new InetSocketAddress(ip, port));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                handleKeys(keys);
            } catch (IOException e) {
                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }

    private void handleKeys(Set<SelectionKey> keys) {
        keys.forEach(key -> {
            if (key.isValid()) {
                SocketChannel sc = (SocketChannel) key.channel();
                if (key.isConnectable()) {
                    if ((sc.isConnectionPending())) {
                        try {
                            sc.finishConnect();
                            sc.register(selector, SelectionKey.OP_READ);
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                sc.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                System.exit(-1);
                            }
                        }
                    }
                }
                if (key.isReadable()) {
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    try {
                        int read = sc.read(buffer);
                        byte[] bytes = new byte[read];
                        buffer.flip();
                        buffer.get(bytes);
                        System.out.println(new String(bytes, Charset.defaultCharset()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            sc.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            System.exit(-1);
                        }
                    }
                }
            }
        });
        keys.clear();
    }

    public void doWrite() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            String toMessage = br.readLine() + "\r\n";
            buffer.put(toMessage.getBytes(Charset.defaultCharset()));
            buffer.flip();
            socketChannel.write(buffer);
        }
    }
}
