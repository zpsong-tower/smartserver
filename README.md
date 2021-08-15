# smartservice

**个人学习即时通讯App [smartline(米线)](https://github.com/zpsong-tower/smartline) 的服务端，主要为其提供特性支持**

您如果需要使用仓库源代码打包，需要根据文末的指引配置相关参数

---

#### 代码结构：

- Bean: 所有的模型类

    - db: 数据库模型
    - api: App请求的消息模型
    - response: 返回给App的消息模型
        - base: 基础响应模型，基础推送模型

- Factory: 所有的数据操作，与数据库挂钩

- Service: Http的入口，接口响应和逻辑处理

- provider

- utils

---

#### 数据库设计:

<div align="center">
    <img src="https://towerdance.oss-cn-shanghai.aliyuncs.com/github/smartservice/db.png">
</div>

---

**参数配置**

在 `PushUtil.java` 中配置[个推](https://www.getui.com)的相关参数

```java
private static final String APP_ID = "";

private static final String APP_KEY = "";

private static final String MASTER_SECRET = "";
```

根据 `hibernate.cfg.xml` 中的参数配置本地mysql数据库，或根据您的喜好自行配置，程序首次运行时[Hibernate](http://hibernate.org)会帮我们在指定的数据库建表并完成初始化

```xml
<property name="connection.url">jdbc:mysql://127.0.0.1:3306/DB_SMARTLINE?serverTimezone=UTC&amp;useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=false</property>
<property name="connection.username">root</property>
<property name="connection.password">123456</property>
```

