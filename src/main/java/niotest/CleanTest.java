package niotest;

import java.nio.CharBuffer;

public class CleanTest {
    public static void main(String[] args) throws Exception{
        CharBuffer cf = CharBuffer.allocate(16);
        cf.put("hwllo".toCharArray());
        cf.clear();
        cf.put("he".toCharArray());

        CharBuffer cf5 = CharBuffer.allocate(5);
        cf.read(cf5);
        System.out.println(new String(cf5.array()));
    }
}
