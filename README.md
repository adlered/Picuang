# Picuang
:pushpin: 本地图床，使用SpringBoot开发，面向用户的网络图床服务。https://pic.stackoverflow.wiki

:wrench: 如果你遇到任何问题，都可以通过我个人签名中的联系方式与我沟通！

:bookmark: Picuang使用MIT协议，您可以自由进行个人/商业使用，但因各种原因造成的后果请自行承担。

# :globe_with_meridians: 使用技术

开发：
`Intellij IDEA`

后端：
`Thymeleaf`
`Spring Boot`

前端：
`JQuery`
`Bootstrap`
`Axios`

# :mag: 预览

![](/demo.gif)

# :page_facing_up: 使用方法

Picuang不需要配置数据库，如果你使用IDEA直接运行本项目或是用Maven打包为war包，它会自动将图片上传到网站根目录中的`uploadImages`目录中。你可以在UploadController.java中找到适配代码：

```
String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploadImages/";
```

Picuang读取了当前网站的根目录。由于项目使用了Thymeleaf，所以`static`是存储静态资源的根目录。

### :heavy_plus_sign: 调整上传文件大小限制

在使用前，你可以在`application.properties`文件中调整文件的限制：

```
#重要！Picuang图床设置
//单个文件传输文件大小限制
spring.servlet.multipart.max-file-size=20MB
//单次传输文件大小限制
spring.servlet.multipart.max-request-size=20MB
#Picuang图床设置结束
```

同时，你设置的限制大小会自动同步到前端的标题当中，用户可以直观地看到文件上传的大小限制。

### :rotating_light: 注意事项

如果你使用了Tomcat 或 Tomcat和Nginx搭载了Picuang，你可能会遇到上传失败的情况。请按照下方的几个解决办法尝试：

1. Tomcat：context.xml

修改`conf/context.xml`文件，在`</Context>`之前添加一行：

```
<Resources cachingAllowed="true" cacheMaxSize="100000" />
```

2. Tomcat：server.xml

修改`conf/server.xml`文件，在你使用端口的`Connector`配置中添加一条：

```
maxPostSize="209715200"
```

3. Nginx

在你的`location / {`下添加一行：

```
client_max_body_size 100m;
```