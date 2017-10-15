---
layout: "post"
title: "解决搬瓦工磁盘满载的问题"
date: "2017-10-15 16:21"
description: “Solving bandwagonhost full disk issue”
published: true
comments: true
category: 编程相关
tags: [搬瓦工, VPS, nginx]
imagefeature: wj/44.jpg
---

&emsp;&emsp;已经很久没有关注自己的博客了，待回来细看时，发现文章下面自己写的评论服务已经挂了。虽然之前的大部分功能并没有完全完成，但作为一个有追求的developer，怎么能坐视不管呢。接下来就大致简单复现一下发现和解决问题的过程。

<!--more-->

## 定位问题
&emsp;&emsp;首先，这个评论服务是部署在[搬瓦工](https://bandwagonhost.com)下的（咳咳，别问我为什么，自己闹着玩的东西，就用便宜的VPS搭建咯）。第一步，还是到搬瓦工对应的管理面板下查看一下机器的状态，貌似一切正常，除了这一抹红色:

<center><img class="center" src="{{ site.url }}/images/2017/bandwagon_1.png"  width="70%" alt="bandwagon_1.png"></center>

&emsp;&emsp;对，10G磁盘都满了！这很有可能是导致评论服务挂掉的原因。所以，还是ssh登陆到服务器上去，看看到底是什么导致这10G内存(上次关注它的时候连10%都没用到)都用完了。接下来，就需要在命令行里查询具体是哪个目录占了很多资源了。通过下面的这样的指令，就可以发现是哪一块在我没关注的这一阶段默默膨胀了。

```
df -h
du -hs /*
du -hs /root/*
du -hs /var/log/*
```

&emsp;&emsp;果然，原来都是nginx的log捣的鬼（居然膨胀到了将近9G，那还得了）！
<center><img class="center" src="{{ site.url }}/images/2017/bandwagon_2.png"  width="60%" alt="bandwagon_2.png"></center>

&emsp;&emsp;再cd到对应的目录查看具体是哪个文件：
```
cd /var/log/nginx
du -hs *
```

&emsp;&emsp;罪魁祸首应该就是这Ngnix的access.log
<center><img class="center" src="{{ site.url }}/images/2017/bandwagon_3.png"  width="60%" alt="bandwagon_3.png"></center>

## 解决
&emsp;&emsp;问题的原因找到了，解决起来就简单了。access.log记录了所有的nginx处理的请求记录，这肯定是随着时间的积累，信息量已经越来越大，以至于到了这个地步。不过这个log现在对于我而言没有太过价值，所以直接干脆`rm -rf ./access.log`删掉该文件。不过，当我开心地再次检查内存状况(`df -h`)时，发现内存占用和之前一样，依然有10G。这不科学？确实不科学，这个时候按经验来说，很多人可能会选择重启服务器（但我还是不希望为了这个重启云上的服务器）。网上大概了解下原因：可能存在一些运行进程在使用未链接（实际已经删除了）的文件。所以，检查一下是否这样的进程在运行（其实，这时候基本上知道是nginx了）：
```
ls -ld /proc/*/fd/* 2>&1 | fgrep '(deleted)'
```

&emsp;&emsp;果然，就是nginx。只要重启nginx服务就能够让`df`命令报出正式的内存状况。

```
service nginx restart
```

&emsp;&emsp;毕，回到最初的问题上（是评论系统挂了），刷新一下博客页面，发现评论回来了。

## 简要回顾原因
&emsp;&emsp;不难看出，评论系统挂了其实是因为其所在的服务器上nginx服务运行不正常。nginx服务运行不正常是因为它记录的log已经撑满了整个硬盘。看来后期得给nginx加上log压缩或者定期清理任务了。但是，还是等我先把这个评论系统的“评论”功能实现吧，啊哈哈哈[羞耻笑]。

## Reference
- [How to fix host disk Consumptions](https://community.hortonworks.com/questions/21501/how-to-fix-host-disk-consumptions.html)
- [`df -k` is not updating the right space status without reboot](https://www.linuxquestions.org/questions/linux-server-73/%60df-k%60-is-not-updating-the-right-space-status-without-reboot-600784/)
