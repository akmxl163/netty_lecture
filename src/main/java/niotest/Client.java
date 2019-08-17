package niotest;

import java.io.IOException;

/**
 * Created by Administrator on 2019/7/14.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        ClientHandler clientHandler = new ClientHandler();
        new Thread(clientHandler).start();
        clientHandler.doWrite();
    }
}
