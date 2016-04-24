---
layout: "post"
title: "在ECS上使用Consul实现服务发现"
description: "Service discovery via consul with amazon ecs"
date: "2016-04-20 20:41"
comments: true
share: true
imagefeature: wj/14.jpg
category: Cloud
tags: [ECS, consul, 服务发现, docker]
published: false
---
###### 本文翻译自文章[Service Discovery via Consul with Amazon ECS](https://aws.amazon.com/blogs/compute/service-discovery-via-consul-with-amazon-ecs/)

&emsp;&emsp;随着当今微服务架构的出现，很多应用现在都采用了一系列分布式组件的部署方式。在这样的架构中，你需要在多个EC2实例的众多Docker容器中配置和协调各种各样应用程序的运行。

&emsp;&emsp;亚马逊EC2容器服务(ECS)提供了一个用于管理资源、任务以及容器调度的集群管理框架。但是，很多应用仍然需要额外的组件来管理分布式组件之间的联系。服务发现的概念就是用来形容这种帮助管理这些联系的组件。

<!--more-->

&emsp;&emsp;在接下来的文章中，我将展示HashiCorp公司的Consul是如何通过提供针对ECS集群的服务发现，来实现对亚马逊ECS性能的提升的。同时我也会提供一些实例应用。

## 服务发现基础
&emsp;&emsp;服务发现工具可以管理集群内进程和服务之间相互发现、相互通信的过程。它们包含创建服务目录，在该目录中注册服务，以及可以查询和连接到该目录下的服务。

&emsp;&emsp;比如，如果你的前端web服务需要连接到后端web服务，它可能硬编码它的后端DNS，或者使用服务发现通过名字和其他元数据来找到后端然后最终获得IP地址和端口号。这让在集群中组件可以相互知晓，不管它们监听的是TCP还是UDP端口，还可以通过一个特定的名字来查询以及连接服务。因为服务发现就像是连接在组件之间的胶水，它具有很高的可用性和可靠性，并且能够快速响应请求，所以至关重要。

&emsp;&emsp;亚马逊ECS允许你同时运行或者维护特定数量的任务实例，这也就是一个所谓的服务。你也可以在负载均衡的后面运行服务，这样服务的名字就是固定的了，这是一个简单的服务发现解决方案。虽然这针对很多应用或许有用，但它却不是适用所有情况，你仍然需要在应用（比如说罗列所有服务，然后调用服务描述来获取特定的终端）中植入构建发现的代码。还有一种选择就是使用一款专用的服务发现框架。

&emsp;&emsp;Consul就是一款可以克服上述困难的常见服务发现工具。它包含服务发现的核心组件 - 健康检查，以及支持应用配置的键值对存储。Consul是一种分布式系统，具有高可用性，弹性，以及高性能的特点。使用它的好处之一是Consul通过一般的HTTP或者用于服务查找的DNS接口就可以很简单地与应用集成。

## Consul架构
&emsp;&emsp;每个给Consul提供服务的节点都可以作为Consul的一个代理(Consul agent)。这个代理负责这个节点上服务的健康检查。健康检查是每个服务发现框架的重要组成部分，因为只有健康的服务才能被客户端发现；任何不健康的主机都会从Consul的服务上撤销注册。

&emsp;&emsp;代理会和一个或者多个Consul服务器会话。Consul服务器会保存并且复制这些数据。根据Raft一致性算法，服务器本身会选择一个指挥者。Consul只需要一个服务器就可以工作，推荐使用3到5个服务器是为了避免失败的场景导致的数据丢失。通常情况下，一个AWS地区上的一个应用运行了Consul服务器的集群。

&emsp;&emsp;应用中用于发现其他服务的服务可以查询任何Consul代理或者服务器，因为代理会转发这些查询到这些服务器。下面的图表展示的就是Consul代理和服务器之间的交互。

  <center><img class="center" src="{{ site.url }}/images/2016/consul.png" alt="consul.png"></center>

## DNS配置
&emsp;&emsp;Consul最大的特性之一就是允许客户使用标准DNS查询来查找服务。它可以通过正常的DNS A记录查询或者使用DNS SRV记录查询来发现IP地址以及运行该服务的服务器端口。对于运行在同一个实例上的所有Docker容器来说，需要确保Consul代理被用作为了DNS解析器，这种Docker进程的配置方式非常有用。

&emsp;&emsp;比如说，假设这个Consul代理安装在一个EC2实例上，并且监听着用于DNS请求的53端口，你可以通过以下的NS查询来查找名为“hello-world”的服务。
```
$dig @0.0.0.0 –t SRV hello-world.service.consul
```
&emsp;&emsp;这会返回IP地址和运行该服务的服务器端口。结构看起来应该和下面的差不多：
```
;; QUESTION SECTION:
;hello-world.service.consul.   IN	SRV
;; ANSWER SECTION:
hello-world.service.consul.	0  IN	SRV  1 1 80 i-28cdc8ce.node.eu1.consul.
;; ADDITIONAL SECTION:
i-28cdc8ce.node.eu1.consul. 0	IN	A	10.0.1.93
```

## Consul和ECS
&emsp;&emsp;每个提供服务的节点都需要启动一台Consul代理。在亚马逊ECS中，你需要为每个ECS集群中的每个ECS实例启动一个Consul代理。通过容器化Consul代理软件你可以很容易实现这些，在ECS实例已经启动了的时候通过条用EC2用户数据脚本就可以启动它。这个代理需要和保存服务目录的Consul服务器通信。也可以在同一个进程中启动Consul服务器和代理进行测试。

## 示例应用
&emsp;&emsp;解释Consul和ECS之间是如何一起运作的最好方式，还是通过一个由三个组件或者说微服务的实例应用：
- "股票价格"服务，它返回目前的股票价格以及给定股票代码的公司名。该服务为一个HTTP服务，并在HTTP响应中提供了一段JSON文档。
- “天气”服务，它返回给定城市的当前温度。该服务也是一个HTTP服务，并在HTTP响应中提供了一段JSON文档。
- “门户”服务，它提供给了包含其他两个服务的用户端网页，可以发送用于查找SRV记录类型的DNS查询，获取该服务的DNS A记录名和端口号。

下图为它的架构：
  <center><img class="center" src="{{ site.url }}/images/2016/consul-arch.png" alt="consul-arch.png"></center>

&emsp;&emsp;在这个例子的架构中，有两个运行着Consul代理的ECS实例，和一个运行着Consul服务器（Consul服务数据保存的地方）的EC2实例。因为只有一个Consul服务器，这一步是为了开发&测试环境而设计的。在产品环境中，一般推荐使用3到5个服务器，用于避免一些错误场景导致的数据丢失。

&emsp;&emsp;ECS集群内含运行着微服务的ECS实例。“股票价格”，“天气”，以及“门户”这三个ECS服务被部署到ECS集群中的实例当中。每个ECS实例在实例启动时都会运行两个Docker容器，其中包括一个Consul代理（consul-agent），它提供了通过HTTP的服务发现能力，以及连接到运行在这个实例上所有Docker容器的DNS。Consul代理和Consul服务器通信以获取集群的最新状态。

&emsp;&emsp;另一个容器是一个注册器代理，它可以基于已经发布的端口和定义在ECS任务定义中的容器环境变量元数据，为ECS任务或ECS服务自动注册/注销服务。它可以在没有任何用户定义的元数据的情况下注册服务，但可以让用户重写或者自己定制Consul服务的定义。更多关于注册器的内容，请参考 [Github 项目](https://github.com/gliderlabs/registrator/commit/1098fc63857a357e6ebea9971c41aa397a24381b)。

## 运行该示例应用
&emsp;&emsp;要追随这个实例应用的内容，你需要保证你的AWS账号有以下内容：
- 一个VPC，并支持DNS（DNS support处于enabled），而且至少有一个公共子网
- 一个IAM用户，拥有登陆EC2实例和创建IAM策略/角色的权限
- 一个EC2秘钥对，用户可以访问的私有密钥文件(.pem文件)

&emsp;&emsp;同时，你应当有一个[Docker Hub](http://registry.hub.docker.com)的账号和对应仓库（比如说仓库的名字叫“my_docker_hub_repo”。）

*注意:确保在必要的文档内键入你定义的值（比如说，用你自己Docker Hub仓库名来代替“my_docker_hub_repo”）。*

## 创建ECS集群
&emsp;&emsp;1.首先到[ECS控制台](https://console.aws.amazon.com/ecs/home)并选择创建集群(Create cluster)。为这个集群起一个独立的名字然后选择创建(Create)。

## 创建Consul服务器以及ECS实例
&emsp;&emsp;你需要创建多个AWS资源来让着个示例应用运行起来。为了使这个过程更加简单，可以使用如下所述的CloudFormation脚本：
- 为ECS和Consul服务器创建IAM role
- 创建


2.打开[CloudFormation控制台](https://console.aws.amazon.com/cloudformation/home)，然后从[提供的模板](https://github.com/awslabs/service-discovery-ecs-consul/blob/master/service-discovery-blog-template)中启动一个新的CloudFormation堆。你需要输入一些参数，包括已存的EC2密钥对米子，VPC ID，子网ID，可用区，等等。

请注意


## 构建Docker镜像
3.通过SSH登录到Consul服务器公共DNS名。这将

4.下载[这三个微服务的源码]()

## 创建任务定义

## 创建ECS服务

## 结论
