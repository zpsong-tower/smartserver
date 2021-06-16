# smartservice

**即时通讯App smartline(米线) 的服务端**

其他说明待补充

-----

#### 代码结构：

- Bean: 所有的模型类

    - db: 数据库模型
    - api: App请求的消息模型
    - response: 返回给App的消息模型
        - base: 基础响应模型，错误码常量，响应模型生成器

- Factory: 所有的数据操作，与数据库挂钩

- Service: Http的入口，接口响应和逻辑处理

- provider

- utils

------

#### 数据库设计:

<div align="center">
    <img src="https://towerdance.oss-cn-shanghai.aliyuncs.com/github/smartservice/db.png">
</div>

-----

