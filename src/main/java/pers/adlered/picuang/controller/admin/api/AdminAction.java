package pers.adlered.picuang.controller.admin.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.adlered.picuang.prop.Prop;
import pers.adlered.picuang.result.Result;
import pers.adlered.picuang.tool.ToolBox;

import javax.servlet.http.HttpSession;

/**
 * <h3>picuang</h3>
 * <p>管理员API</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-07 17:16
 **/
@Controller
public class AdminAction {
    /**
     * 检测管理员密码是否已经设定
     * @return
     * 500：管理员密码未设定，需要在Data中指定的文件设置密码
     * 200：管理员密码已设定，可以登录
     */
    @RequestMapping("/api/admin/init")
    @ResponseBody
    public Result init() {
        Result result = new Result();
        try {
            if (Prop.get("password").isEmpty()) {
                result.setCode(500);
                result.setData(ToolBox.getINIDir());
            } else {
                result.setCode(200);
            }
        } catch (NullPointerException NPE) {
            result.setCode(500);
            result.setData(ToolBox.getINIDir());
        }
        return result;
    }

    /**
     * 检查管理员是否已登录
     * @param session
     * @return
     */
    @RequestMapping("/api/admin/check")
    @ResponseBody
    public Result check(HttpSession session) {
        Result result = new Result();
        boolean logged = false;
        try {
            String admin = session.getAttribute("admin").toString();
            logged = Boolean.parseBoolean(admin);
        } catch (NullPointerException NPE) {
        }
        if (logged) {
            result.setCode(200);
        } else {
            result.setCode(500);
        }
        return result;
    }

    /**
     * 管理员登录
     * 管理员登录为了安全考虑，必须使用POST方法传输
     * @param session
     * @param password
     * @return
     * 500：登录失败
     * 200：登录成功
     */
    @RequestMapping(value = "/api/admin/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpSession session, String password) {
        Result result = new Result();
        if (password.isEmpty()) {
            result.setCode(500);
            return result;
        }
        String truePassword = Prop.get("password");
        if (truePassword.equals(password)) {
            session.setAttribute("admin", "true");
            result.setCode(200);
        } else {
            result.setCode(500);
        }
        return result;
    }

    /**
     * 管理员注销
     * @param session
     * @return
     * 200：注销成功
     */
    @RequestMapping("/api/admin/logout")
    @ResponseBody
    public Result logout(HttpSession session) {
        session.invalidate();
        Result result = new Result();
        result.setCode(200);
        return result;
    }
}
