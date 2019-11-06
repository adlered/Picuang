package pers.adlered.picuang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.adlered.picuang.access.HttpOrHttpsAccess;
import pers.adlered.picuang.result.Result;
import pers.adlered.picuang.tool.ToolBox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UploadController {
    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@PathVariable MultipartFile file) {
        Result result = new Result();
        //是否上传了文件
        if (file.isEmpty()) {
            result.setCode(406);
            return result;
        }
        //是否是图片格式
        String filename = file.getOriginalFilename();
        String suffixName = ToolBox.getSuffixName(filename);
        System.out.println("SuffixName: " + suffixName);
        if (ToolBox.isPic(suffixName)) {
            File dest = ToolBox.generatePicFile(suffixName);
            result.setData(filename);
            filename = dest.getName();
            System.out.println("Saving into " + dest.getAbsolutePath());
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                String url = "/uploadImages/" + filename;
                result.setCode(200);
                result.setMsg(url);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            result.setCode(500);
            return result;
        }
        result.setCode(500);
        return result;
    }

    @RequestMapping("/clone")
    @ResponseBody
    public Result clone(String url) {
        Result result = new Result();
        try {
            String suffixName = ToolBox.getSuffixName(url);
            System.out.println("SuffixName: " + suffixName);
            File dest = null;
            if (ToolBox.isPic(suffixName)) {
                dest = ToolBox.generatePicFile(suffixName);
            } else if (suffixName.contains(".jpg") || suffixName.contains(".jpeg") || suffixName.contains(".png") || suffixName.contains(".svg") || suffixName.contains(".gif")) {
                dest = ToolBox.generatePicFile(".jpg");
            } else {
                result.setCode(500);
                return result;
            }
            System.out.println("Saving to " + dest.getAbsolutePath());
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
            Matcher matcher = p.matcher(url);
            matcher.find();
            result.setData("From " + matcher.group());
            result.setCode(200);
            result.setMsg("/uploadImages/" + dest.getName());
            return result;
        } catch (Exception e) {
            result.setCode(500);
            return result;
        }
    }
}