package others;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SDTest {

    public static void main(String[] args) throws Exception{
        FileInputStream fin = new FileInputStream("src/protocbuf/test.proto");
        FileOutputStream fout = new FileOutputStream("src/protocbuf/out.proto");
        FileChannel inChannel = fin.getChannel();
        FileChannel outChannel = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(500);
        int count = 0;
        while (true) {
            buffer.clear();
            int len = inChannel.read(buffer);
            if(len == -1 || count >10) break;
            buffer.flip();
            outChannel.write(buffer);
            count++;
        }
        outChannel.close();
        inChannel.close();
    }

    static void bfToString(ByteBuffer bb){
        System.out.println("position:" + bb.position());
        System.out.println("limit:" + bb.limit());
        System.out.println("++++++++++++++++++++++++");

    }

    static void func1(){
        File file = new File("G:\\迅雷下载\\射雕17版\\SDYXZ");
        Collection<File> files = FileUtils.listFiles(file, new String[]{"mkv"}, false);


        for (File movie: files ) {
            String name = movie.getName();

            String pattern = "EP(\\d{2})";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);

            // 现在创建 matcher 对象
            Matcher m = r.matcher(name);
            if (m.find( )) {
                String nName = name.substring(m.start(),m.end());
                nName = ("射雕英雄传HDTV" + nName + ".mkv").replace("EP", "");
                try {
                    FileUtils.moveFile(movie, new File("G:\\迅雷下载\\射雕17版\\"+nName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("NO MATCH");
            }
        }
    }
}
