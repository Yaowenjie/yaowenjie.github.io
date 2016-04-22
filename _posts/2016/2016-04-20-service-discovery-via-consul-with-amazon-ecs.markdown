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
&emsp;&emsp;Consul最大的特性之一就是允许客户使用标准DNS查询来查找服务。

## Consul和ECS

## 示例应用

## 运行该示例应用

## 创建ECS集群

## 创建Consul服务器以及ECS实例

## 构建Docker镜像

## 创建任务定义

## 创建ECS服务

## 结论
