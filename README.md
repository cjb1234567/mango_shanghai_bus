如何优雅的赶公交？（课程作业）
=============

序言
-------------

你坐过公交车上班吗？
> 远远看到一辆公交车，你在后面一路狂奔，师傅却一脚油门公交车扬长而去。
> > 错过这班车，下一班永远不来了
> > > 你继续在寒风中瑟瑟发抖，或在烈日下大汗淋漓



<strong>如何优雅？</strong>
<pre>
提前知道公交车什么时候来，才能安排出门时间。

用手机APP查？ 太浪费时间了.

设置好出行时间，"MANGO" 语音助手就会提醒你， 最近一班车还有几站，多久到， 合理安排出门时间。

还能用语音问天气，问时间。节约早上的宝贵时间。让你“优美雅致”乘公交。
</pre>


安装
----------------------
0. 硬件环境
- 电脑、树莓派或其他类似设备(需要联网)、
- 音响、
- 麦克风

1. 软件环境依赖
- java >= 15.0
- python >= 3.7
- portaudio.dev >= 18.1-7.1
- python3-pyaudio >= 0.2.11-1+b2

2. [python模块依赖]
- pygame >= 2.0.1
- flask >= 1.0.2
- flask_cors >= 3.0.10
- pocketsphinx >= 0.1.15
- requests >= 2.21.0
- lxml >= 4.3.2


启动
-------------------
- 客户端
运行 
<pre><code>
nohup flask run &
</pre></code>

- 语音相关接口服务
运行 
<pre><code>
nohup python3 client/detect.py &
</pre></code>

- 公交车接口服务和设置页面
打包  ./magane/magane/gradlew build  或者直接下载jar包： https://jbox.sjtu.edu.cn/l/5F2hSZ (提取码：rnex)
运行 
<pre><code>
java -jar mango.jar
</pre></code>

提示： 可根据服务接口部署的情况修改客户端源码中的请求地址


使用方法
---------------------
1. 设置乘车路线和车站，提醒时间，到了设定时间后，会自动播报公交车信息
http://公交车服务IP地址:9090/setting

2. 语音交互
先唤醒，再提问。

唤醒词：Mango

目前支持的提问：
天气：例如“今天天气怎么样” ， “明天天气怎么样”， “后天天气怎么样”
时间: 如“现在几点了”
星期： 如“今天星期几”
公交车： 如：“我的车来了没有”



开机自启动
------------------------
在/etc/rc.local 或 /etc/rc.d/rc.local 文件中加入启动命令

<pre><code>
export FLASK_APP=app.py
nohup flask run&
nohup python3 client/detect.py &
nohup java -jar mango.jar
</code></pre>

修改权限
<pre><code>
chmod +x /etc/rc.d/rc.local
</code></pre>
