package pers.adlered.picuang.tool;

import org.springframework.util.ClassUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * <h3>picuang</h3>
 * <p>工具箱</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-06 11:09
 **/
public class ToolBox {
    private static final Set<String> suffixSet;

    static {
        suffixSet = new HashSet<>();
        suffixSet.add(".jpeg");
        suffixSet.add(".jpg");
        suffixSet.add(".png");
        suffixSet.add(".gif");
        suffixSet.add(".svg");
        suffixSet.add(".bmp");
        suffixSet.add(".ico");
        suffixSet.add(".tiff");
    }
    
    public static String getSuffixName(String filename) {
        String suffixName = filename.substring(filename.lastIndexOf("."));
        suffixName = suffixName.toLowerCase();
        return suffixName;
    }

    public static boolean isPic(String suffixName) {
        return suffixName.contains(suffixName);
    }

    public static String getPicStoreDir() {
        return ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploadImages/";
    }

    public static File generatePicFile(String suffixName, String time, String IP) {
        String path = getPicStoreDir() + IP + "/" + time;
        String fileName = UUID.randomUUID() + suffixName;
        return new File(path + fileName);
    }

    public static String getDirByTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/HH/mm/");
        return simpleDateFormat.format(date);
    }

    public static String getINIDir() {
        return new File("config.ini").getAbsolutePath();
    }
}
