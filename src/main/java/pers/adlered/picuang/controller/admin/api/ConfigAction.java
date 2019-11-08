package pers.adlered.picuang.controller.admin.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.adlered.picuang.prop.Prop;
import pers.adlered.picuang.result.Result;
import pers.adlered.picuang.tool.FileUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * <h3>picuang</h3>
 * <p>配置API</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-07 17:31
 **/
@Controller
public class ConfigAction {
    /**
     * 检查管理员是否已登录
     * @param session
     * @return
     */
    public boolean logged(HttpSession session) {
        boolean logged = false;
        try {
            logged = Boolean.parseBoolean(session.getAttribute("admin").toString());
        } catch (NullPointerException NPE) {
        }
        return logged;
    }

    @RequestMapping("/api/admin/getConf")
    @ResponseBody
    public String getConf(HttpSession session, String conf) {
        if (logged(session)) {
            return Prop.get(conf);
        } else {
            return "Permission denied";
        }
    }

    @RequestMapping("/api/admin/setConf")
    @ResponseBody
    public Result setConf(HttpSession session, String conf, String value) {
        Result result = new Result();
        if (logged(session)) {
            Prop.set(conf, value);
            result.setCode(200);
        } else {
            result.setCode(500);
        }
        return result;
    }

    @RequestMapping("/api/admin/export")
    @ResponseBody
    public void exportConfig(HttpServletResponse response, HttpSession session) {
        if (logged(session)) {
            String fileName = "config.ini";
            FileUtil.downloadFile(response, fileName);
        }
    }

    @RequestMapping("/api/admin/import")
    @ResponseBody
    public Result importConfig(@PathVariable MultipartFile file, HttpSession session) {
        Result result = new Result();
        try {
            String filename = file.getOriginalFilename();
            // 如果已登录 && 文件不为空 && 是ini文件
            if (logged(session) && (!file.isEmpty()) && filename.matches(".*(\\.ini)$")) {
                File config = new File("config.ini");
                config.renameTo(new File("config.ini.backup"));
                File newConfig = new File(config.getAbsolutePath());
                file.transferTo(newConfig);
                System.out.println(newConfig.getPath());
                result.setCode(200);
            } else {
                result.setCode(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(500);
        }
        return result;
    }
}
