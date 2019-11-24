package pers.adlered.picuang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pers.adlered.picuang.prop.Prop;

import java.io.File;
import java.text.DecimalFormat;

@Controller
public class MainController {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        // 图片总数计算
        modelAndView.addObject("files", Prop.get("imageUploadedCount"));
        // 剩余空间计算
        File diskPartition = new File("/");
        String freePartitionSpace = new DecimalFormat("#.00").format(diskPartition.getFreeSpace() / 1073741824);
        modelAndView.addObject("free", freePartitionSpace);
        // 限制文件大小
        modelAndView.addObject("limit", maxFileSize);
        // 版本
        modelAndView.addObject("version", Prop.getVersion());
        return modelAndView;
    }

    @RequestMapping("/history")
    @ResponseBody
    public ModelAndView history() {
        ModelAndView modelAndView = new ModelAndView("history");
        modelAndView.addObject("version", Prop.getVersion());
        return modelAndView;
    }
}
