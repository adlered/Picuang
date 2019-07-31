package pers.adlered.picuang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("limit", maxFileSize);
        return modelAndView;
    }
}
