---
layout: "post"
title: "AWS网络基础"
date: "2016-06-19 16:32"
description: “AWS Network”
published: true
comments: true
category: Cloud
tags: [AWS, VPC, 网络]
imagefeature: wj/13.jpg
---
&emsp;&emsp;本文将围绕AWS网络基础的相关概念，介绍使用VPC（虚拟私人云）的益处以及AWS常见的安全网络架构，并主要通过几种不同情况下如何做连接和访问控制的需求出来，来介绍各个网络组件（如ACL、安全组、Peering等）的作用。

<!--more-->

## 1.AWS VPC及其好处
&emsp;&emsp;VPC(Virtual Private Cloud)直译过来是虚拟私人云，实际上就是AWS里面虚拟局域网的概念（从较大氛围而言，一般内部还需要进一步的网络划分）。总结而言，使用它一般会有以下一些益处/优势：

- 因为在AWS VPC中启动的实例并不直接访问因特网，所以默认情况下它们就更加安全。
- VPC简化了安全，能够与ACL、路由规则以及安全组(security group)无缝集成。
- 你可以添加多个网络接口。
- 可以直接把自己的本地网络连接到一个AWS VPC上。
- VPC简化了安全实施以及多层Web部署（比如说把一个公共应用连接到私有子网中的一个私有数据库服务器上）。
- 通过私有连接，VPC连接中内置的灾备镜像可以让恢复任务关键型数据的过程更加简单和可靠。

## 2. AWS网络基本安全架构
<center><img class="center" src="{{ site.url }}/images/2016/aws-n1.png" alt="smudge.png"></center>
&emsp;&emsp;上图可以看出，在一个安全的网络架构中，外部的流量要到一个实际的实例可能会经过很多相关的步骤：Gateway, Router, Routing Table, ACL, Subnet, Security Group等等。

## 3. 几种常见的连接和访问控制情形
&emsp;&emsp;以下为AWS网络中几种常见的互连/访问控制操作：

- 同一个VPC下的子网(subnet)之间
- 同一个Region下不同VPC之间
- 不同Region的VPC之间
- 本地数据中心网络与AWS VPC之间
- VPC与S3之间

### 3.1 同一个VPC下的子网（subnet）之间
&emsp;&emsp;同一个VPC下的子网本身默认就是可以互相访问的，但用户一般需要对ACL和Security Group进行访问控制即可。如下图所示：
<center><img class="center" src="{{ site.url }}/images/2016/aws-n2.png" alt="smudge.png"></center>

### 3.2 同一个Region下不同VPC之间
&emsp;&emsp;需要通过借助VPC Peering来实现同一个Region下不同VPC之间的连接.
<center><img class="center" src="{{ site.url }}/images/2016/aws-n3.png" alt="smudge.png"></center>

### 3.3 不同Region的VPC之间
&emsp;&emsp;不同的Region下VPC之间的连接，需要在各自的VPC公共网络通过配置VPN 实例来实现IPSec连接。
<center><img class="center" src="{{ site.url }}/images/2016/aws-n4.png" alt="smudge.png"></center>

### 3.4 本地数据中心网络与AWS VPC之间
&emsp;&emsp;通过配置VPN Connection在VPC配置Virtual Private Gateway, 在公司数据中心配置Customer Gateway，然后通过IPSec联系起来。
<center><img class="center" src="{{ site.url }}/images/2016/aws-n5.png" alt="smudge.png"></center>

### 3.5 VPC与S3之间
&emsp;&emsp;Endpoints能够提供指向S3的安全连接，它易于配置、具备高度可靠性，而且整个流程不需要任何Gateway或者NAT实例的介入。
<center><img class="center" src="{{ site.url }}/images/2016/aws-n6.png" alt="smudge.png"></center>

## 4. 其他参考

- [配置AWS VPC的五个误区](http://yaowenjie.github.io/cloud/VPC-5-mistakes)
- [AWS VPC](https://aws.amazon.com/vpc/)
- [Security in Your VPC](http://docs.aws.amazon.com/AmazonVPC/latest/UserGuide/VPC_Security.html)
