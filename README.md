# LinwinSHS Http Server

#### 版本 2.8.0 长期稳定版本

#### 介绍
SHS Web服务器系统，高性能、高并发、高效率。支持Windows、Linux、docker等，同样，也可以在replit这些云环境内部署运行，采用Java作为开发语言，稳定安全高效。
包括了http服务器模块、反向代理服务器模块、快速网站调试模块。部署简单，高效，支持可视化管理运行。采用Java线程池高并发处理。

#### 文档 (English)
URL: https://gitee.com/LinWin-Cloud/linwinshs-server/wikis

#### 软件架构
##### Linux运行环境提供支持
1. amd64
#### Windows运行环境支持
1. x64
#### 其他支持
如果不是以上的任何架构，那么可以采用更换jre的操作来进行各个平台的适配

#### 技术
##### VirtualWebPage 
在启动服务器的时候将一些页面文件如html，css，JavaScript和文本文件加载到内存当中，并且更具情况定期更新。
省去读取硬盘上文件的操作和时间，快捷高效

##### 同步处理，异步读取
采用同步多线程处理 socket 请求，采用异步读取文件内容。

#### 参与贡献的人员
1. Linwin-Cloud
2. zmh-program
3. ChatGPT-4

#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
