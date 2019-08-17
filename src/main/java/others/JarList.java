package others;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
public class JarList {

    public static void main(String[] args) {
        try {
            Collection<File> files = FileUtils.listFiles(
                    new File("C:\\Users\\Administrator\\.gradle\\caches\\modules-2\\files-2.1\\org.slf4j\\slf4j-api\\1.7.25"),
                    new String[]{"jar"},
                    true);
            System.out.println("jars count");
            for (File file: files ) {

                System.out.println("File path:" + file.getAbsolutePath());
                JarFile jarFile = new JarFile(file);
                Enumeration enu = jarFile.entries();
                while (enu.hasMoreElements()) {
                    JarEntry jarEntry = (JarEntry) enu.nextElement();
                    String name = jarEntry.getName();
                    if (name.endsWith(".class")) {
                        System.out.println("  |- name=" + name);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
