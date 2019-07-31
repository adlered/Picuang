package pers.adlered.picuang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.adlered.picuang.result.Result;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
        String fileName = file.getOriginalFilename();
        System.out.println("Filename: " + fileName);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if (suffixName.equals(".jpeg") || suffixName.equals(".jpg") || suffixName.equals(".png") || suffixName.equals(".gif") || suffixName.equals(".svg")) {
            result.setData(fileName);
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploadImages/";
            fileName = UUID.randomUUID() + suffixName;
            System.out.println("Saving into " + path + fileName);
            File dest = new File(path + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
                String url = "/uploadImages/" + fileName;
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
}