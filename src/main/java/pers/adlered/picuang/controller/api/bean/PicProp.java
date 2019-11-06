package pers.adlered.picuang.controller.api.bean;

/**
 * <h3>picuang</h3>
 * <p>List Bean</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-06 16:54
 **/
public class PicProp {
    private String time;
    private String filename;
    private String path;
    private String ip;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
