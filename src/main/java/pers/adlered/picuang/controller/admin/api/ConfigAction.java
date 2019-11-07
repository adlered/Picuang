package pers.adlered.picuang.controller.admin.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.adlered.picuang.prop.Prop;
import pers.adlered.picuang.result.Result;

import javax.servlet.http.HttpSession;

/**
 * <h3>picuang</h3>
 * <p>配置API</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-07 17:31
 **/
@Controller
public class ConfigAction {
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
}
