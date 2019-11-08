package pers.adlered.picuang.prop;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * <h3>picuang</h3>
 * <p>自动配置文件</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-06 21:29
 **/
@Component
public class Prop {
    private static Properties properties = new Properties();

    static {
        put();
        System.out.println("Properties loaded.");
    }

    public static void del() {
        File file = new File("config.ini");
        file.delete();
    }

    public static void put() {
        try {
            properties.load(new BufferedInputStream(new FileInputStream("config.ini")));
        } catch (FileNotFoundException e) {
            System.out.println("Generating new profile...");
            properties.put("imageUploadedCount", "0");
            properties.put("version", "V2.3");
            properties.put("password", "");
            properties.put("adminOnly", "off");
            try {
                properties.store(new BufferedOutputStream(new FileOutputStream("config.ini")), "Save Configs File.");
            } catch (FileNotFoundException FNFE) {
                FNFE.printStackTrace();
            } catch (IOException IOE) {
                IOE.printStackTrace();
            }
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static void set(String key, String value) {
        try {
            properties.setProperty(key, value);
            System.out.println("[Prop] Set key '" + key + "' to value '" + value + "'");
            PrintWriter printWriter = new PrintWriter(new FileWriter("config.ini"), true);
            Set set = properties.keySet();
            for (Object object : set) {
                String k = (String) object;
                String v = properties.getProperty(k);
                printWriter.println(k + "=" + v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
