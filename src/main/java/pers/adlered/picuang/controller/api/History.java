package pers.adlered.picuang.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.adlered.picuang.controller.api.bean.PicProp;
import pers.adlered.picuang.log.Logger;
import pers.adlered.picuang.tool.IPUtil;
import pers.adlered.picuang.tool.ToolBox;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h3>picuang</h3>
 * <p>查看历史记录API</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-06 16:24
 **/
@Controller
public class History {
    @RequestMapping("/api/list")
    @ResponseBody
    public List<PicProp> list(HttpServletRequest request, String year, String month, String day) {
        List<PicProp> list = new ArrayList<>();
        File file = new File(getHome(request) + year + "/" + month + "/" + day + "/");
        File[] hour = listFiles(file);
        for (File i : hour) {
            if (i.isDirectory()) {
                String dir = getHome(request) + year + "/" + month + "/" + day + "/" + i.getName() + "/";
                File[] minute = listFiles(new File(dir));
                for (File j : minute) {
                    String filesDir = dir + j.getName() + "/";
                    File[] files = listFiles(new File(filesDir));
                    try {
                        for (File k : files) {
                            if (k.isFile()) {
                                PicProp picProp = new PicProp();
                                picProp.setTime(i.getName() + ":" + j.getName());
                                picProp.setFilename(k.getName());
                                picProp.setPath("/uploadImages/" + IPUtil.getIpAddr(request).replaceAll("\\.", "/").replaceAll(":", "/") + "/" + year + "/" + month + "/" + day + "/" + i.getName() + "/" + j.getName() + "/" + k.getName());
                                picProp.setIp(IPUtil.getIpAddr(request));
                                list.add(picProp);
                            }
                        }
                    } catch (NullPointerException NPE) {
                        logNpe();
                        continue;
                    }
                }
            }
        }
        Collections.reverse(list);
        return list;
    }

    @RequestMapping("/api/day")
    @ResponseBody
    public List<String> day(HttpServletRequest request, String year, String month) {
        File file = new File(getHome(request) + year + "/" + month + "/");
        File[] list = listFiles(file);
        List<String> lists = new ArrayList<>();
        try {
            for (File i : list) {
                if (i.isDirectory()) {
                    lists.add(i.getName());
                }
            }
        } catch (NullPointerException NPE) {
            logNpe();
        }
        Collections.reverse(lists);
        return lists;
    }

    @RequestMapping("/api/month")
    @ResponseBody
    public List<String> month(HttpServletRequest request, String year) {
        StringBuilder sb = new StringBuilder();
        File file = new File(getHome(request) + year + "/");
        File[] list = listFiles(file);
        List<String> lists = new ArrayList<>();
        try {
            for (File i : list) {
                if (i.isDirectory()) {
                    lists.add(i.getName());
                }
            }
        } catch (NullPointerException NPE) {
        }
        Collections.reverse(lists);
        return lists;
    }

    @RequestMapping("/api/year")
    @ResponseBody
    public List<String> year(HttpServletRequest request) {
        File file = new File(getHome(request));
        File[] list = listFiles(file);
        List<String> lists = new ArrayList<>();
        try {
            for (File i : list) {
                if (i.isDirectory()) {
                    lists.add(i.getName());
                }
            }
        } catch (NullPointerException NPE) {
        }
        Collections.reverse(lists);
        return lists;
    }

    private String getHome(HttpServletRequest request) {
        String addr = IPUtil.getIpAddr(request).replaceAll("\\.", "/").replaceAll(":", "/");
        return ToolBox.getPicStoreDir() + addr + "/";
    }

    private File[] listFiles(File file) {
        File[] files = file.listFiles();
        return files == null ? new File[0] : files;
    }

    private void logNpe() {
        Logger.log(String.format("A null pointer exception occurred in [%s]", this.getClass().getName()));
    }
}
