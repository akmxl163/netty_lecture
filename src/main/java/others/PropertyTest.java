package others;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

public class PropertyTest {
    public static void main(String[] args) {
//        System.setProperty("io.netty.eventLoopThreads", "3");
//        Properties pp = System.getProperties();
//        for (Map.Entry<Object, Object> entry: pp.entrySet()){
//            System.out.println(entry.getKey()+"->"+entry.getValue());
//        }
//        System.out.println(pp.getProperty("io.netty.value"));

        int x = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads",
                NettyRuntime.availableProcessors() * 2));
        System.out.println("int x = " + x);
    }
}
