package pers.adlered.picuang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.adlered.picuang.access.HttpOrHttpsAccess;
import pers.adlered.picuang.log.Logger;
import pers.adlered.picuang.prop.Prop;
import pers.adlered.picuang.result.Result;
import pers.adlered.picuang.tool.IPUtil;
import pers.adlered.picuang.tool.ToolBox;
import pers.adlered.picuang.tool.double_keys.main.DoubleKeys;
import pers.adlered.simplecurrentlimiter.main.SimpleCurrentLimiter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UploadController {
    public static SimpleCurrentLimiter uploadLimiter = new SimpleCurrentLimiter(1, 1);
    public static SimpleCurrentLimiter cloneLimiter = new SimpleCurrentLimiter(3, 1);

    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@PathVariable MultipartFile file, HttpServletRequest request, HttpSession session) {
        synchronized (this) {
            String addr = IPUtil.getIpAddr(request).replaceAll("\\.", "/").replaceAll(":", "/");
            boolean allowed = uploadLimiter.access(addr);
            Result result = new Result();
            if (Prop.get("adminOnly").equals("on")) {
                Logger.log("AdminOnly mode is on! Checking user's permission...");
                if (!logged(session)) {
                    Logger.log("User not logged! Uploading terminated.");
                    result.setCode(401);
                    result.setMsg("管理员禁止了普通用户上传文件！");
                    return result;
                }
                Logger.log("Admin is uploading...");
            }
            try {
                while (!allowed) {
                    allowed = uploadLimiter.access(addr);
                    System.out.print(".");
                    Thread.sleep(100);
                }
            } catch (InterruptedException IE) {}
            //是否上传了文件
            if (file.isEmpty()) {
                result.setCode(406);
                return result;
            }
            //是否是图片格式
            String filename = file.getOriginalFilename();
            String suffixName = ToolBox.getSuffixName(filename);
            Logger.log("SuffixName: " + suffixName);
            if (ToolBox.isPic(suffixName)) {
                String time = ToolBox.getDirByTime();
                File dest = ToolBox.generatePicFile(suffixName, time, addr);
                result.setData(filename);
                filename = dest.getName();
                Logger.log("Saving into " + dest.getAbsolutePath());
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(dest);
                    String url = "/uploadImages/" + addr + "/" + time + filename;
                    result.setCode(200);
                    result.setMsg(url);
                    int count = Integer.parseInt(Prop.get("imageUploadedCount"));
                    ++count;
                    Prop.set("imageUploadedCount", String.valueOf(count));
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                result.setCode(500);
                result.setMsg("不是jpg/jpeg/png/svg/gif图片！");
                return result;
            }
            result.setCode(500);
            result.setMsg("未知错误。");
            return result;
        }
    }

    @RequestMapping("/clone")
    @ResponseBody
    public Result clone(String url, HttpServletRequest request, HttpSession session) {
        synchronized (this) {
            String addr = IPUtil.getIpAddr(request).replaceAll("\\.", "/").replaceAll(":", "/");
            // IP地址访问频率限制
            boolean allowed = cloneLimiter.access(addr);
            try {
                while (!allowed) {
                    allowed = cloneLimiter.access(addr);
                    System.out.print(".");
                    Thread.sleep(100);
                }
            } catch (InterruptedException IE) {}
            Result result = new Result();
            // 基于IP地址的重复克隆检测限制
            if (!DoubleKeys.check(addr, url)) {
                result.setCode(401);
                result.setMsg("请不要重复克隆同一张图片。你可以在右上方的\"历史\"选项找到你克隆过的图片！");
                return result;
            }
            if (Prop.get("adminOnly").equals("on")) {
                Logger.log("AdminOnly mode is on! Checking user's permission...");
                if (!logged(session)) {
                    Logger.log("User not logged! Uploading terminated.");
                    result.setCode(401);
                    result.setMsg("管理员禁止了普通用户上传文件！");
                    return result;
                }
                Logger.log("Admin is uploading...");
            }
            String regex = "(http(s)?://)?(localhost|(127|192|172|10)\\.).*";
            Matcher matcher = Pattern.compile(regex).matcher(url);
            if (matcher.matches()) {
                result.setCode(401);
                result.setMsg("Anti-SSRF系统检测到您输入了内网地址，请检查！");
                return result;
            }
            File dest = null;
            try {
                String suffixName = ToolBox.getSuffixName(url);
                Logger.log("SuffixName: " + suffixName);
                String time = ToolBox.getDirByTime();
                if (ToolBox.isPic(suffixName)) {
                    dest = ToolBox.generatePicFile(suffixName, time, addr);
                } else {
                    dest = ToolBox.generatePicFile(".png", time, addr);
                }
                Logger.log("Saving into " + dest.getAbsolutePath());
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(dest);
                BufferedInputStream bufferedInputStream = HttpOrHttpsAccess.post(url,
                        "",
                        null);
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = bufferedInputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                bufferedInputStream.close();
                Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(url);
                m.find();
                result.setData("From " + m.group());
                result.setCode(200);
                result.setMsg("/uploadImages/" + addr + "/" + time + dest.getName());
                int count = Integer.parseInt(Prop.get("imageUploadedCount"));
                ++count;
                Prop.set("imageUploadedCount", String.valueOf(count));
                return result;
            } catch (Exception e) {
                // 出错时删除建立的文件，以防止无效图片过多产生
                if (dest != null) {
                    Logger.log("An exception has caught, deleting picture cache...");
                    dest.delete();
                }
                result.setCode(500);
                result.setMsg(e.getClass().toGenericString().replaceAll("public class ", ""));
                return result;
            }
        }
    }

    /**
     * 检查管理员是否已登录
     *
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
}