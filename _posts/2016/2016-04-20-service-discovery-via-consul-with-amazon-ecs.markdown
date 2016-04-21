---
layout: "post"
title: "在ECS上使用Consul实现服务发现"
date: "2016-04-20 20:41"
comments: true
share: true
imagefeature: wj/14.jpg
published: false
---
###### 本文翻译自文章[Service Discovery via Consul with Amazon ECS](https://aws.amazon.com/blogs/compute/service-discovery-via-consul-with-amazon-ecs/)

随着当今微服务架构的出现，很多应用现在都采用了一系列分布式组件的部署方式。在这样的架构中，你需要在多个EC2实例的众多Docker容器中配置和协调各种各样应用程序的运行。

亚马逊EC2容器服务(ECS)提供了一个用于管理资源、任务以及容器调度的集群管理框架。但是，很多应用仍然需要额外的组件来管理分布式组件之间的联系。服务发现的概念就是用来形容这种帮助管理这些联系的组件。

<!--more-->

在接下来的文章中，我将展示HashiCorp公司的Consul是如何通过提供针对ECS集群的服务发现，来实现对亚马逊ECS性能的提升的。同时我也会提供一些实例应用。

## 服务发现基础
服务发现工具可以管理集群内进程和服务之间可以相互发现、相互交流的过程。它们包含创建服务目录，在该目录中注册服务，以及之后可以查询和连接到该目录下的服务。

比如，如果你的前端web服务需要连接后端web服务，
