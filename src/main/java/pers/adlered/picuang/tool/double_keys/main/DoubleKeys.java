package pers.adlered.picuang.tool.double_keys.main;

import pers.adlered.picuang.tool.double_keys.storage.DoubleKeysStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Picuang</h3>
 * <p>验证Key1中的key2在key1中是否已经存在，存在返回false</p>
 * <p>用于检测某个用户名是否已经发过相同的一句话</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-28 11:06
 **/
public class DoubleKeys {
    public static void main(String[] args) {
        c("adler", "1");
        c("adler", "1");
        c("hello", "1");
        c("adler", "2");
        c("hello", "1");
        c("adler", "2");
    }

    public static void c(String key1, String key2) {
        System.out.println(check(key1, key2));
    }

    public static boolean check(String key1, String key2) {
        List<String> tempList = new ArrayList<>();
        // 检查key1是否存在
        if (DoubleKeysStorage.keyMap.containsKey(key1)) {
            // key1存在
            tempList = DoubleKeysStorage.keyMap.get(key1);
            if (tempList.contains(key2)) {
                return false;
            } else {
                tempList.add(key2);
                DoubleKeysStorage.keyMap.put(key1, tempList);
                return true;
            }
        } else {
            // key1不存在，生成key1
            tempList.add(key2);
            DoubleKeysStorage.keyMap.put(key1, tempList);
            // 第一次生成，直接返回true
            return true;
        }
    }
}
