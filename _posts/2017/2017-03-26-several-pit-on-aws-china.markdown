---
layout: "post"
title: "使用 AWS China 的几个坑"
date: "2017-03-26 16:11"
description: “Several pit on AWS China”
published: true
comments: true
category: Cloud
tags: [AWS, Pits]
imagefeature: wj/13.jpg
---

&emsp;&emsp;在没有使用AWS China(Beijing Region)之前，我还天真的认为它只是亚马逊云服务的一个中国区延伸，会延续AWS所有的服务。但是在这两天的使用之后，发现AWS China和AWS其他Region的服务差距还是挺大的，所以简单罗列一下，这两天使用它时发现的几个坑（后续也可能还会增加）。

<!--more-->
### 1. 缺少很多常用服务。
&emsp;&emsp;如容器相关的ECS(EC2 Container Service)、ECR(EC2 Container Registry)等，部署相关的CodeDeploy，还有现在比较热门的API Gateway和Lambda等等，都不在AWS China的产品列表之内。如果你想在AWS China上面玩转docker，或者希望实现容器集群的自动化部署和管理，就变得很困难啦。

&emsp;&emsp;以下为现阶段AWS China所拥有的所有服务，和其他区域的服务相比，少了很多：
<center><img class="center" src="{{ site.url }}/images/2017/aws-china-services.png"  width="80%" alt="aws-china-services.png"></center>

### 2. EC2的80、443、8080端口受到限制，需要ICP备案才可以开放。
&emsp;&emsp;我的应用监听的端口是8080，所以就在对应EC2实例上新增了一个Security Group，用于开放了8080端口（允许TCP 8080端口anywhere的inbound），啥提示也没看到，然后就是死活curl不通它公共DNS的8080端口(Operation timed out)，各种方法找原因，最后换了一个8888之类的端口（当然也同时修改了Security Group规则），居然成功了。兜兜转转之间发现国内的8080端口居然受到了限制，换句话说，80、443、8080这样端口都需要ICP备案才能开放。

### 3. Great Firewall导致的网络限制
&emsp;&emsp;众所周知的原因，既然是落在北京的机房，肯定过不了Great Firewall这一关。一些墙外的网站肯定访问不了，从Docker Hub上拉docker镜像肯定也是十分吃力的。依赖源如果来自国外可能就比较头疼了。

&emsp;&emsp;PS. 我写这篇文章的时间是**2017年3月**，希望后续会逐渐改善吧。

#### 参考链接
- [AWS China(Beijing) Region Tips](https://fastretailing.github.io/blog/2015/09/29/AWS-ChinaBeijing-Region-Tips.html)
- [AWS China 产品列表](https://www.amazonaws.cn/products/?nc1=h_ls)
- [Re: ec2 80,443,8080 port blocked?](https://forums.aws.amazon.com/thread.jspa?threadID=200550)
- [Port 80 and 8080 can not be accessed](https://forums.aws.amazon.com/thread.jspa?threadID=173724)
