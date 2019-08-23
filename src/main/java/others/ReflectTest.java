package others;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 使用反射加载配置文件，并执行其中的指定方法
 */
public class ReflectTest {

    public static void main(String[] args) throws Exception {

        //1.使用
        Properties p = new Properties();
        ClassLoader classLoader = ReflectTest.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("application.properties");
        p.load(is);
        String className = p.getProperty("className");
        String methodName = p.getProperty("methodName");

        Class cls = Class.forName(className);
        Object obj = cls.newInstance();
        Method method = cls.getMethod(methodName);
        method.invoke(obj);
        System.out.println("enddddddddddddddded");
    }
}
