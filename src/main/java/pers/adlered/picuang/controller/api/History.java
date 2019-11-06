package pers.adlered.picuang.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.adlered.picuang.controller.api.bean.PicProp;
import pers.adlered.picuang.tool.IPUtil;
import pers.adlered.picuang.tool.ToolBox;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
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
        File[] hour = file.listFiles();
        for (File i : hour) {
            if (i.isDirectory()) {
                String dir = getHome(request) + year + "/" + month + "/" + day + "/" + i.getName() + "/";
                File[] minute = new File(dir).listFiles();
                for (File j : minute) {
                    String filesDir = dir + j.getName() + "/";
                    File[] files = new File(filesDir).listFiles();
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
                        continue;
                    }
                }
            }
        }
        return list;
    }

    @RequestMapping("/api/day")
    @ResponseBody
    public List<String> day(HttpServletRequest request, String year, String month) {
        StringBuilder sb = new StringBuilder();
        File file = new File(getHome(request) + year + "/" + month + "/");
        File[] list = file.listFiles();
        List<String> lists = new ArrayList<>();
        try {
            for (File i : list) {
                if (i.isDirectory()) {
                    lists.add(i.getName());
                }
            }
        } catch (NullPointerException NPE) {
        }
        return lists;
    }

    @RequestMapping("/api/month")
    @ResponseBody
    public List<String> month(HttpServletRequest request, String year) {
        StringBuilder sb = new StringBuilder();
        File file = new File(getHome(request) + year + "/");
        File[] list = file.listFiles();
        List<String> lists = new ArrayList<>();
        try {
            for (File i : list) {
                if (i.isDirectory()) {
                    lists.add(i.getName());
                }
            }
        } catch (NullPointerException NPE) {
        }
        return lists;
    }

    @RequestMapping("/api/year")
    @ResponseBody
    public List<String> year(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        File file = new File(getHome(request));
        File[] list = file.listFiles();
        List<String> lists = new ArrayList<>();
        try {
            for (File i : list) {
                if (i.isDirectory()) {
                    lists.add(i.getName());
                }
            }
        } catch (NullPointerException NPE) {
        }
        return lists;
    }

    private String getHome(HttpServletRequest request) {
        String addr = IPUtil.getIpAddr(request).replaceAll("\\.", "/").replaceAll(":", "/");
        return ToolBox.getPicStoreDir() + addr + "/";
    }
}
