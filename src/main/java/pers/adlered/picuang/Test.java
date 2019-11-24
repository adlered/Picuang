package pers.adlered.picuang;

import pers.adlered.picuang.access.HttpOrHttpsAccess;

import java.io.BufferedInputStream;
import java.util.Map;

/**
 * <h3>picuang</h3>
 * <p></p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-06 21:37
 **/
public class Test {
    public static void main(String[] args) {
        try {
            BufferedInputStream bufferedInputStream = HttpOrHttpsAccess.post("http://de.cichic.com/pink-polka-dot-draped-off-shoulder-oversized-midi-dress.html", "", null);
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                System.out.print(new String(bytes, 0 , len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
