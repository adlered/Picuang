package pers.adlered.picuang.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * <h3>picuang</h3>
 * <p>管理</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-07 17:05
 **/

@Controller
public class Admin {
    @RequestMapping("/admin")
    @ResponseBody
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("admin");
        return modelAndView;
    }
}
