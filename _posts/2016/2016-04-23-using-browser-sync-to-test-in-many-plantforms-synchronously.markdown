---
layout: "post"
title: "使用Browsersync实现多浏览器跨平台的UI测试"
date: "2016-04-23 22:33"
description: "Use Browsersync for hronised browser testing"
comments: true
share: true
imagefeature: wj/5.jpg
category: 编程相关
tags: [browser-sync, 同步, 多平台, UI测试]
published: true
---
###### BrowserSync
&emsp;&emsp;最近由于TW[技术雷达(Tech Radar)](https://www.thoughtworks.com/radar/tools)的机缘，同事向我们介绍了一个用于浏览器同步测试的工具 --- Browsersync，使用之后，发现它着实简单，而且十分炫酷。更重要的一点是，__Browsersync可以同时在PC、平板、手机等设备下进项UI调试__，换句话说，你在其中一个浏览器上的操作会同步到多个设备的多个浏览器上。

<!--more-->

### __快速上手 - 安装__

&emsp;&emsp;BrowserSync本质上是一个Node模块，所以要安装它的话只需要你安装了Node.js和npm即可。你可以选择使用npm全局安装BrowserSync，只需要在terminal中输入：

``` shell
npm install -g browser-sync
```

&emsp;&emsp;当然你也可以结合gulpjs或gruntjs构建工具来安装：

``` shell
npm install --save-dev browser-sync
```

### __快速上手 - 启动__
&emsp;&emsp;Browsersync最大的特点是可以跨浏览器跨平台的同步测试，它实际上监控的是静态文件或者网站，启动它也很简单。我们就以我的博客地址yaowenjie.github.io为例，在terminal输入如下：

``` shell
browser-sync start -p http://yaowenjie.github.io
```

接着，你就会看到这样的信息：

```
[BS] Proxying: http://yaowenjie.github.io
[BS] Access URLs:
 --------------------------------------
       Local: http://localhost:3000
    External: http://192.168.1.139:3000
 --------------------------------------
          UI: http://localhost:3001
 UI External: http://192.168.1.139:3001
 --------------------------------------
```

&emsp;&emsp;同时，它还会打开电脑上的默认浏览器（比如说Chrome），访问localhost:3000，实际上就像是访问yaowenjie.github.io的一个本地镜像。你可以在本机的其他浏览器（比如firefox）打开localhost:3000,然后你在其中任何一个浏览器上对这个网站的操作都会同步到另一个浏览器上。 <br/>
&emsp;&emsp;远远不止这些，或许你也注意到了上面的信息中，除了Local的URL，我们还有一个External的URL（192.168.1.139:3000，感兴趣的可以看3001端口的URL又是什么），它就是用于其他设备（其他电脑，平板，智能手机）访问这个网页的，当然，前提是你的设备和启动Browsersync的机器处于同一个网段内，也就是说可以访问这个IP。然后，你在任何一个设备上对这个网站的操作都会同步到所有打开的浏览器上，下面就是我做的一个简单动态效果图（其中涉及pad，手机，电脑端chrome和firefox），是不是很酷？
<center><img class="center" src="{{ site.url }}/images/2016/browser-sync.gif" alt="browser-sync.gif"></center>

&emsp;&emsp;援引Browsersync官网的话：

``` word
    您可以想象一下：“假设您的桌子上有pc、ipad、iphone、android等设备，同时打开了您需要调试的页面，当您使用browsersync后，您的任何一次代码保存，以上的设备都会同时显示您的改动”。无论您是前端还是后端工程师，使用它将提高您30%的工作效率。
```

### __更多内容/参考__
&emsp;&emsp;Browsersync是免费开源的工具，它的用户也在急速的增长。未来它的推广必将减少跨平台跨浏览器人工测试的工作量，它将来的进步空间和应用场景扩展也很值得期待。 <br/>
&emsp;&emsp;更多信息，请参考Browsersync的[官网](https://www.browsersync.io/)，面对中国用户，它还有独立的[中文官网](http://www.browsersync.cn/)。
