# Picuang

:pushpin: 本地图床，使用SpringBoot开发，面向用户的网络图床服务。https://pic.stackoverflow.wiki

:wrench: 如果你遇到任何问题，都可以通过我个人签名中的联系方式与我沟通！

:bookmark: Picuang使用MIT协议，您可以自由进行个人/商业使用，但因各种原因造成的后果~~雨我无瓜~~请自行承担。

# 原理

Picuang是一款`根图床`WEB程序。用户可以向Picuang中上传`jpg`/`jpeg`/`png`/`svg`/`gif`图片，Picuang会自动将图片上传至Picuang的运行目录中。

Picuang会将用户上传的图片保存到`本地`，而非将图片上传到`其它公共容器`中(例如七牛、新浪什么的)，适用于`想搭建一个提供图床存储、读取服务的同学`。

# 功能

- [x] 选择、拖拽或粘贴图片，自动上传至Picuang服务器中

- [x] 自动生成图片对应的`URL格式链接`、`HTML标签格式链接`、`Markdown格式链接`

- [x] 图片链接克隆（转存）功能，可输入图片的URL，Picuang会自动下载并保存到Picuang服务器中

- [x] 单IP上传自动阻流器，上传过快会排队上传，减轻服务器压力，防止恶意上传/克隆攻击

- [x] 历史记录功能（按IP地址读取，所以更换IP地址后无法查询）

- [ ] Picuang管理员后台设置界面（基于配置文件存储，不依赖数据库）

### 暂未确定是否加入的功能

- [ ] 用户登录功能（暂时不考虑加入，Picuang是以轻量为前提的图床程序，用户登录需要依赖数据库）

- [ ] 图片压缩功能（暂时不考虑加入，图片压缩会增加服务器负载，且压缩后的图片会~~失去灵魂~~）

此处功能短期内不会开发，但Picuang架构非常简单，实现新功能也很简单，如有能力可以自行编写，也欢迎提交PR :)

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

[可以来这里直接体验哦~](https://pic.stackoverflow.wiki/)

GIF加载较慢，你也可以访问[这里](https://pic.stackoverflow.wiki/uploadImages/125/33/189/136/2019/11/06/22/29/484ca851-88cb-4554-9444-272f218781bd.gif)查看（该图片也采用了我自搭建的公共Picuang图床存储）

![picuang2.gif](https://pic.stackoverflow.wiki/uploadImages/125/33/189/136/2019/11/06/22/29/484ca851-88cb-4554-9444-272f218781bd.gif)

# :page_facing_up: 使用方法

Picuang不需要配置数据库，如果你使用IDEA直接运行本项目或是用Maven打包为war包，它会自动将图片上传到网站根目录中的`uploadImages`目录中。你可以在UploadController.java中找到适配代码：

```
String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/uploadImages/";
```

Picuang读取了当前网站的根目录。由于项目使用了Thymeleaf，所以`static`是存储静态资源的根目录。

### 打包为war

* Picuang可在Tomcat上运行，[在这里下载已经打包好的war包，部署到Tomcat中](https://github.com/AdlerED/Picuang/releases)

* 如果你想自己修改Picuang的源码，Clone后在Intellij IDEA中运行，使用Maven - install打包新的war包，新的war包位置在一般在`target`目录中。如图所示：

![image.png](https://pic.stackoverflow.wiki/uploadImages/bce0a4b4-bd34-4e63-a3a5-74d898a9dd63.png)

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