package others;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetTest {
    public static void main(String[] args) throws Exception{
        System.out.println("好戏开场=====" );
        RandomAccessFile inFile = new RandomAccessFile("in.txt", "r");
        RandomAccessFile outFile = new RandomAccessFile("out.txt", "rw");
        FileChannel inChannel = inFile.getChannel();
        FileChannel outChannel = outFile.getChannel();

        MappedByteBuffer mBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFile.length());

        Charset charset = Charset.forName("utf8");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(mBuffer);
        System.out.println("一个字符为：" );
        System.out.println(charBuffer.get(13));
        ByteBuffer encodeBuffer = Charset.forName("GBK").encode(charBuffer);
        outChannel.write(encodeBuffer);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
