package pers.adlered.picuang.access;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * <h3>picuang</h3>
 * <p>访问HTTP或HTTPS网站</p>
 * <p>当postData为空或null时，使用GET方式访问，反之使用POST方式访问</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-11-06 09:48
 **/
public class HttpOrHttpsAccess {
    public static BufferedInputStream get(String path, Map<String, String> header) throws IOException, GeneralSecurityException {
        return post(path, null, header);
    }

    public static BufferedInputStream post(String path, String postData, Map<String, String> header) throws IOException, GeneralSecurityException {
        boolean HTTPS = path.matches("^(https://).*");
        // 打开连接
        URL url = new URL(path);
        URLConnection conn = null;
        conn = url.openConnection();
        // Content
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 超时
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(15000);
        // Header
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        // 输入流
        BufferedInputStream in = null;
        if (path.matches("^(https://).*")) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            httpsConn.setSSLSocketFactory(ssf);
            httpsConn.setDoInput(true);// 打开输入流，以便从服务器获取数据
            httpsConn.setDoOutput(true);// 打开输出流，以便向服务器提交数据
            if (postData != null && !postData.isEmpty()) {
                PrintWriter out = new PrintWriter(httpsConn.getOutputStream());
                out.print(postData);
                out.flush();
            }
            in = new BufferedInputStream(httpsConn.getInputStream());
        } else {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            if (postData != null && !postData.isEmpty()) {
                PrintWriter out = new PrintWriter(httpConn.getOutputStream());
                out.print(postData);
                out.flush();
            }
            in = new BufferedInputStream(httpConn.getInputStream());
        }
        return in;
    }
}

