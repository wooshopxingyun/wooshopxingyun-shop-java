
wooshopxingyun-java 后端代码

<h1 style="text-align: center">星云商城系统</h1>

#### 项目简介
星云基于当前流行技术组合的前后端分离商城系统： SpringBoot2+MybatisPlus+SpringSecurity+jwt+redis+Vue的前后端分离的商城系统， 包含商品分类、商品sku、运费模板、素材库、小程序支付、拼团活动、砍价活动、商户管理、 秒杀活动、优惠券、积分、分销、会员、充值、多门店等功能，更适合企业或个人二次开发；

### 商业版
1. 包含PC商城、管理后台、uniapp+(全平台已经适配)
2. 付费会员获得系统更多功能模块
3. 全部源码无加密
4. 永久授权
5. 付费会员专属qq群解答
6. github私服永久更新
7. 提供完善开发文档、说明书,可加入售后服务群获得技术、部署与上架指导，技术指导不限于本插件

需要购买商业版请联系客服(QQ:97437471)获取专属会员群、git私服

### 官方技术QQ交流群 (入群前，请在网页右上角点 "Star" )：826692261
|  PC商城演示地址 |  https://demo.admin.wooshopxingyun.com/buyerHome |
|---|---|
| 管理后台演示地址：账号:test 密码:123456    |  https://demo.admin.wooshopxingyun.com/login |
#### 演示二维码
 ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/yanshicode.png)

| Android  |  https://demo.h5.wooshopxingyun.com/wooshopxingyun_anz.apk |
|---|---|
| 关注公众号  | ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/qrcode_for_gh_34624ccb5486_258.jpg) |
| 360小程序  | 已完成适配  |
| 快应用  | 已完成适配  |

### 小程序端截图

| ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/yidon-1.png "在这里输入图片标题") | ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/yidon-2.png "在这里输入图片标题") |
| ------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/yidon-3.png "在这里输入图片标题")                     | ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/yidon-4.png "在这里输入图片标题")                     |

### PC客户端截图

| ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/pc1.png "在这里输入图片标题") | ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/pc2.png "在这里输入图片标题") |
| ------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/pc3.png "在这里输入图片标题")                     | ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/pc4.png "在这里输入图片标题")                     |

### 管理后台截图

| ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/admin1.png "在这里输入图片标题") | ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/admin2.png "在这里输入图片标题") |
| ------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/admin3.png "在这里输入图片标题")                     | ![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/admin4.png "在这里输入图片标题")                     |

#### 项目源码

|     |   后端源码  |   前端源码  |
|---  |--- | --- |
|  码云   |   https://gitee.com/xingyunshop/xingyunshopjava   |   https://gitee.com/xingyunshop/eladmin-wooxingyunshop-ui   |
|  GitHub   |  https://github.com/wooshopxingyun/wooshopxinyun-shop-java    |  https://github.com/wooshopxingyun/eladmin-wooxingyunshop-ui    |


#### 核心依赖

| 依赖              | 版本     |
|-----------------|--------|
| Spring Boot     | 2.5.5  |
| weixin-java     | 4.3.0  |
| Spring Security | 2.7.1  |
| Mybatis Plus    | 3.5.2  |
| hutool          | 5.7.22 |
| swagger         | 3.0.0  |

### 技术选型
* 1 后端使用技术
    * 1.1 SpringBoot2
    * 1.2 mybatis、MyBatis-Plus
    * 1.3 SpringSecurity
    * 1.5 Druid
    * 1.6 Slf4j
    * 1.7 Fastjson
    * 1.8 JWT
    * 1.9 Redis
    * 1.10 Quartz
    * 1.11 Mysql
    * 1.12 swagger
    * 1.13 WxJava
    * 1.14 Lombok
    * 1.15 Hutool
    
* 前端使用技术
    * 2.1 Vue 全家桶
    * 2.2 Element
    * 2.3 uniapp



# 本地安装
### 基本环境（必备）
- 1、JDK：8+
- 2、Redis 3.0+
- 3、Maven 3.0+
- 4、MYSQL 5.7+
- 5、Node v8+
### 开发工具
Idea、webstorm、vscode



### 安装教程
#### 后台系统工程（JAVA端）
1.  确保redis、jdk、已经安装启动
2.  下载代码
```
git clone https://gitee.com/xingyunshop/xingyunshopjava.git
```
3.  使用idea打开项目目录如下:
[如图片]
4.  将数据库文件导入数据库,修改配置信息:redis、mysql:
[如图片]
5.  输入命令 mvn clean install 或者用idea工具操作:
[如图片]
3.  启动程序，启动程序路径如下：
[如图片]

#### 后台前端工程（VUE端）
1. 请确保本地系统已经安装node,建议node8或者node10以上
2. 拉取代码
```
git clone https://gitee.com/xingyunshop/xingyunshopjava.git
```
3. 前端工程目录结构如下:
[如图片]
4. 修改配置信息:
[如图片]
5. 在目录根部输入cnpm install或者yarn install:
[如图片]
6. 安装依赖失败:
```
npm config set registry https://registry.npm.taobao.org
配置后可通过下面方式来验证是否成功
npm config get registry

在 ~/.npmrc 加入下面内容，可以避免安装 node-sass 失败
sass_binary_site=https://npm.taobao.org/mirrors/node-sass/

.npmrc 文件位于
win：C:\Users\[你的账户名称]\.npmrc
linux：直接使用 vi ~/.npmrc
```
7. 执行第五步骤成功后,在控制台输入命令：npm run dev，控制台打印出如下画面，恭喜表示本项目启动成功拉。
[如图片]

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


### 商业授权
商业版本比开源版本跟丰富,商业版提供安装教程、开发文档、操作手册、会员qq交流群。

商业使用需要授权，授权方式可选择联系官网客服，或者qq群联系群主。

商业授权模式为永久授权，支持永久升级。

商业授权源码无加密。

商业案例由于涉及部分多层二开关系，如需了解可以咨询官方客服。

### 开源须知
1.仅允许用于个人学习研究使用.

2.禁止将本开源的代码和资源进行任何形式任何名义的出售.

3.软件受国家计算机软件著作权保护（登记号：2022SR1324355）。

4.限制商用，如果需要商业使用请联系我们。QQ97437471.或者加入qq群联系群主。

![输入图片说明](https://demo.h5.wooshopxingyun.com/kaiyun/banquan.png "在这里输入图片标题")    

特别鸣谢
- eladmin:https://github.com/elunez/eladmin
- mybaitsplus:https://github.com/baomidou/mybatis-plus
- hutool:https://github.com/looly/hutool
- wxjava:https://github.com/Wechat-Group/WxJava
- vue:https://github.com/vuejs/vue
- element:https://github.com/ElemeFE/element
