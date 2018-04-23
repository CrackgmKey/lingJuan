# lingJuan
lingJuan一套可以设置代理的淘客系统

最近有朋友反应项目编译不了，我于2018年4月18日11:38:46亲自试了一下 貌似没什么问题  导入项目先把两个需要改的地方改成本地的

另外本程序集成了极光推送和阿里百川的SDK，具体使用请去查看官网先关文档

（需要有后台基础）安装说明:将项目中houtaiwenjian目录下的houtaiwenjian.rar解压，其他gx是负责APK更新已经首页轮播图的接口，lingjuan则是后台文件，如下流程图中的网页端，需自行修改lingjuan/yewu/目录下的三个PHP文件中的数据库为自己的，然后在项目中api/getLatest.java中修改服务器地址即可

注：lingjuan必须放到网站根目录，如需放到其他目录请自行修改后台文件中的地址，gx目录随意，放到哪里 项目中写哪里即可,数据表和后台文件在一起


实现功能:领卷主要是一个淘宝客系统，说白了就是客户在领卷APP上面领取淘宝商品的优惠券，如果他使用优惠券购买了商品 你就会有佣金提成，
注：软件打开需要输入一个邀请码，请自行在搭建的网站中申请，该邀请码和PID绑定，

成品试用:http://www.aiboyy.pw/lingjuan/index.html

更新日志：
2017年10月10日11:37:34：更新1.0.2，修复少数用户崩溃问题，优化细节

2018年3月5日13:06:30：修复父类未引用布局导致的崩溃问题

2018年4月18日11:41:44：发现部分朋友说源码编译不成功，试了一下没什么问题，并且注释掉了拖累速度的Bugly


流程图如下
![image](https://github.com/CrackgmKey/lingJuan/blob/master/tupian/jdfw.gif)

首页UI

![image](https://github.com/CrackgmKey/lingJuan/blob/master/tupian/1.png)

商品UI

![image](https://github.com/CrackgmKey/lingJuan/blob/master/tupian/2.png)

搜索UI

![image](https://github.com/CrackgmKey/lingJuan/blob/master/tupian/4.png)
