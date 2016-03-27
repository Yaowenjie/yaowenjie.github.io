---
layout: post
title: 'AWS VPC的五个错误'
date: 2016-03-25 17:53
description: VPC让AWS变得更好！
comments: true
category: Cloud
tags: [Cloud, AWS, VPC, 子网, 网络]
imagefeature: wj/5.jpg
mathjax: true
share: true
---
###### 本文翻译自[Cloud Academy](http://cloudacademy.com/blog/aws-vpc-configuration-five-kick-yourself-mistakes/)

<!-- AWS VPC配置：让它变得更好。 -->
<br/>
&emsp;&emsp;AWS VPC最厉害的是它提供的令人难以置信的灵活性和安全性。亚马逊的VPC能够你在亚马逊独立虚拟网络中重新分配计算资源，例如EC2实例以及RDS部署，让你全权控制所有的进出流量。你也可以选择IP地址范围，子网关联，以及路由表以及网关配置。一切尽在你的掌握之中。

<!--more-->

### 以下是VPC的优势所在:

- 因为在AWS VPC中启动的实例并不是直接访问因特网，它们默认就更加安全。
- VPC简化了安全，能够与ACL、路由规则以及安全组(security group)无缝集成。
- 你可以连接多个网络接口。
- 你可以直接把自己的本地网络连接到一个AWS VPC上。
- VPC简化了安全多层次Web部署（比如说把一个公共应用连接到私有子网上的一个私有数据库服务器）。
- 通过私有连接，VPC连接中内置的灾备镜像可以让恢复任务关键型数据的过程更加简单更加可靠。
- 运行在专用于单个客户的硬件上的启动专用实例，可以获得额外的隔离。

## 另一方面...
&emsp;&emsp;VPC虽是精心设计的，但它们并不是刀枪不入的。特别是当人们参与进来的时候。因为你旨在确保所有部署都绝对完美，你就需要保证避免这些配置错误：

### __AWS VPC的五个配置误区__

1. __糟糕的子网或者CIDR块计划：__ 管理员在设计VPC子网以及CIDR（无类别域间路由）块时，很难考虑到网络的兼容性和未来需求。记住AWS VPC的子网块一旦被分配后，就不能改变，因此你应该认真计算每个子网所需要的节点 个数。记住：创建一个/24或者/27的CIDR或许会让你的地址不够用。同时，如果你想连接的本地数据中心使用的是10.0.26.0/24块，而你却为你的VPC子网选择了10.0.0.0/16块，就不会有好结果。
2. __公共IP地址：__ 当你登陆到一个实例非默认VPC（使用默认设置）的公共子网内，它不会具有公共IP或者使用传统EC2命名方式的主机名。一旦你创建了这样的实例，那让它可以被公众访问的唯一方法就是给这个节点分配一个静态IP(elastic IP)。静态IP并不是免费的，默认情况下，每个账户被被限制为 5个（尽管你仍然可以请求更多个）。
3. __共享产品及开发环境：__ 把所有的交付准备(staging)、开发(dev)和产品环境都放在单个VPC中，将很有可能导致大量的混乱和没完没了的安全组及路由表冲突。
4. __不考虑NAT实例的分布式冗余：__ 聪明的管理员总是把高可用性置入他们的应用中。然而这样，他们中有些人就就会把应用或者数据库服务器放置在私有子网内，这样要访问一些服务的话，就要通过独立的NAT实例。但这些NAT实例挂了的时候，你的整个应用就会陷入中断，这是一份[构建和配置NAT实例的快速上手指南](https://cloudacademy.com/amazon-web-services/amazon-vpc-networking-course/build-and-configure-a-nat-instance.html)。
5. __假设AWS VPC需要复杂的网络技巧：__ VPC不是开关，VPC不是路由器，VPC不是防火墙。即便他们担负这三个硬件工具的职责，VPC实际上仍然是一种软件定义网络(SDN)。AWS自下而上的设计，简单而不复杂。你确实需要理解一些非常基本的网络概念，比如说路由、NAT、VPN以及ACL，但是AWS自己对它们大部分都做了提升。

## 结论
&emsp;&emsp;如果你仍然还把你的应用放在传统的EC2内，你应该认真考虑是否需要把它迁移到AWS VPC上了，以享受VPC带来的巨大好处。但是一定要坚持[最佳实践](http://docs.aws.amazon.com/AmazonVPC/latest/UserGuide/VPC_Introduction.html)...并且做到深谋远虑！

&emsp;&emsp;另外，Cloud Academy提供了一套完整的[关于AWS VPC的视频课程](https://cloudacademy.com/amazon-web-services/understanding-vpc-course/)
