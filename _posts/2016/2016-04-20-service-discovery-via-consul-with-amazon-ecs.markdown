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
published: true
---
###### 本文翻译自文章[Service Discovery via Consul with Amazon ECS](https://aws.amazon.com/blogs/compute/service-discovery-via-consul-with-amazon-ecs/)
&emsp;&emsp;现如今随着[微服务架构](https://medium.com/aws-activate-startup-blog/using-containers-to-build-a-microservices-architecture-6e1b8bacb7d1)的出现，很多应用都是以一套分布式组件的形式来部署。在这样的架构中，你需要在多个EC2实例上的多个Docker容器中，配置和协调各种各样的应用程序。 <br/>
&emsp;&emsp;亚马逊EC2容器服务([ECS](http://aws.amazon.com/ecs/))提供了一个用于管理资源、任务以及容器调度的集群管理框架。但是，很多应用仍然需要额外的组件来管理分布式组件之间的联系。服务发现的概念就是用来定义这种帮助管理这些联系的组件。<br/>
<!--more-->
&emsp;&emsp;在接下来的文章中，我将展示HashiCorp公司的Consul是如何通过提供针对ECS集群的服务发现，来实现对亚马逊ECS性能的提升的。同时我也会使用一个示例应用来阐述这些。

## 服务发现基础
&emsp;&emsp;服务发现工具可以管理集群内进程和服务之间相互发现、相互通信的过程。它们包含创建服务目录，在该目录中注册服务，以及查询和连接到该目录下的服务。<br/>
&emsp;&emsp;比如，如果你的前端web服务需要连接到后端web服务，可以硬编码它的后端DNS，或者使用服务发现通过名字和其他元数据来找到后端然后最终获得IP地址和端口号。这让集群中的组件可以相互知晓，不管它们监听的是TCP还是UDP端口，还可以通过一个特定的名字来查询和连接服务。因为服务发现就像是连接在组件之间的胶水，它具有很高的可用性和可靠性，并且能够快速响应请求，所以显得十分重要。<br/>
&emsp;&emsp;亚马逊ECS允许你同时运行或者维护特定数量的任务实例，这也就是一个所谓的服务。你也可以在负载均衡的后面运行服务，这样服务的名字就是固定的了，这是一个简单的服务发现解决方案。虽然这针对很多应用或许有用，但它却不是适用所有情况，你仍然需要在应用（比如说罗列所有服务，然后调用服务描述来获取特定的终端）中植入构建发现的代码。还有一种选择就是使用一款专用的服务发现框架。<br/>
&emsp;&emsp;Consul就是一款可以克服上述困难的常见服务发现工具。它包含服务发现的核心组件 - 健康检查，以及支持应用配置的键值对存储。Consul是一种分布式系统，具有高可用性，弹性，以及高性能的特点。使用它的还有一个好处，就是Consul通过一般的HTTP或者用于服务查找的DNS接口就可以很简单地与应用集成。

## Consul架构
&emsp;&emsp;每个给Consul提供服务的节点都可以作为Consul的一个代理(Consul agent)。这个代理负责这个节点上服务的健康检查。健康检查是每个服务发现框架的重要组成部分，因为只有健康的服务才能被客户端发现；任何不健康的主机都会从Consul的服务上注销。<br/>
&emsp;&emsp;代理会和一个或者多个Consul服务器会话。Consul服务器会保存并且复制这些数据。根据Raft一致性算法，服务器本身会选择一个指挥者。Consul只需要一个服务器就可以工作，但我们一般推荐使用3到5个服务器，主要是为了避免失败的场景导致的数据丢失。通常情况下，一个AWS地区上的一个应用运营着一个Consul服务器集群。<br/>
&emsp;&emsp;应用中用于发现其他服务的服务可以查询任何Consul代理或者服务器，因为代理会把这些查询转发到这些服务器。下面的图表展示的就是Consul代理和服务器之间的交互。

  <center><img class="center" src="{{ site.url }}/images/2016/consul.png" alt="consul.png"></center>

更多信息，请参考[Consul架构](https://www.consul.io/docs/internals/architecture.html)。

## DNS配置
&emsp;&emsp;Consul最大的特性之一就是允许客户使用标准DNS查询来查找服务。它可以通过正常的DNS A记录查询或者使用DNS SRV记录查询来发现IP地址以及运行该服务的服务器端口。对于运行在同一个实例上的所有Docker容器来说，需要确保Consul代理被用作为了DNS解析器，这种Docker进程的配置方式非常有用。<br/>
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
&emsp;&emsp;每个提供服务的节点都需要启动一台Consul代理。在亚马逊ECS中，你需要为每个ECS集群中的每个ECS实例启动一个Consul代理。通过容器化Consul代理软件你可以很容易实现这些，在ECS实例已经启动了的时候通过调用EC2用户数据脚本就可以启动它。这个代理需要和保存服务目录的Consul服务器通信。为了测试在同一个进程中启动Consul服务器和Consul代理也是有可能的。

## 示例应用
&emsp;&emsp;解释Consul和ECS之间是如何一起运作的最好方式，还是通过一个由三个组件或者说微服务构成的示例应用：

- "股票价格"服务，它返回目前的股票价格以及给定股票代码的公司名。该服务为一个HTTP服务，并在HTTP响应中提供了一段JSON文档。
- “天气”服务，它返回给定城市的当前温度。该服务也是一个HTTP服务，并在HTTP响应中提供了一段JSON文档。
- “门户”服务，它提供给了包含其他两个服务的用户端网页，可以发送用于查找SRV记录类型的DNS查询，获取该服务的DNS A记录名和端口号。

下图为它的架构：
  <center><img class="center" src="{{ site.url }}/images/2016/consul-arch.png" alt="consul-arch.png"></center>

&emsp;&emsp;在这个例子的架构中，有两个运行着Consul代理的ECS实例，和一个运行着Consul服务器（Consul服务数据保存的地方）的EC2实例。因为只有一个Consul服务器，这一步是为了开发&测试环境而设计的。在产品环境中，一般推荐使用3到5个服务器，用于避免一些错误场景导致的数据丢失。<br/>
&emsp;&emsp;ECS集群内含运行着微服务的ECS实例。“股票价格”，“天气”，以及“门户”这三个ECS服务被部署到ECS集群中的实例当中。每个ECS实例在实例启动时都会运行两个Docker容器，其中包括一个Consul代理（consul-agent），它提供了通过HTTP的服务发现能力，以及连接到运行在这个实例上所有Docker容器的DNS。Consul代理和Consul服务器通信以获取集群的最新状态。<br/>
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
&emsp;&emsp;你需要创建多个AWS资源来让这个示例应用运行起来。为了使这个过程更加简单，可以使用如下所述的CloudFormation脚本：

- 为ECS和Consul服务器创建IAM role。
- 创建必要的安全组，用于允许ECS节点和Consul服务器之间的通信，以及允许来自定义的CIDR IP段的SSH请求。
- 登录到Consul服务器的EC2实例，安装Git和Docker，并且启动一个运行Consul服务器软件的Docker容器。
- 使用针对ECS优化过的AMI，登录到ECS实例集群自动伸缩功能组(Auto Scaling Group)。
- 启动一个Consul代理的Docker容器，并且连接到Consul服务器实例上。
- 启动Consul注册器代理，以使用Consul服务发现目录，来自动注册在这台实例上运行的ECS任务和服务。Docker守护进程可以使用Consul代理和用于DNS查询的亚马逊DNS服务器。

&emsp;&emsp;2.打开[CloudFormation控制台](https://console.aws.amazon.com/cloudformation/home)，然后从[提供的模板](https://github.com/awslabs/service-discovery-ecs-consul/blob/master/service-discovery-blog-template)中启动一个新的CloudFormation堆。你需要输入一些参数，包括已存的EC2密钥对，VPC ID，子网ID，可用区，等等。

&emsp;&emsp;请注意输入参数AmazonDnsIp必须是运行在一个保留IP地址上，在VPC网络范围”加2“内的DNS服务器。想了解更多关于VPC内亚马逊DNS服务器的内容，请看DHCP选项话题下的[亚马逊DNS服务器](http://docs.aws.amazon.com/zh_cn/AmazonVPC/latest/UserGuide/VPC_DHCP_Options.html#AmazonDNS)章节。

## 构建Docker镜像
&emsp;&emsp;3.通过SSH登录到Consul服务器公共DNS名。这就是之前步骤中CloudFormation脚本输出参数"ConsulServer"的值。

&emsp;&emsp;4.下载[这三个微服务的源码](https://github.com/awslabs/service-discovery-ecs-consul)。
你会看到三个目录，它们包含将要构建的三个Docker容器的基本信息。

&emsp;&emsp;5.登陆到Docker Hub:

```
$ sudo docker login
```

&emsp;&emsp;6.在每个子目录中构建Docker容器，用你自己的仓库名代替my_docker_hub_repo:

```
$ cd weather
$ sudo docker build -t my_docker_hub_repo/weather .
$ cd ../stock-price
$ sudo docker build -t my_docker_hub_repo/stock-price .
$ cd ../portal
$ sudo docker build -t my_docker_hub_repo/portal .
```

&emsp;&emsp;7.把这些镜像都Push到你的Docker Hub仓库，用你自己的仓库名代替my_docker_hub_repo:

```
$ sudo docker push my_docker_hub_repo/weather
$ sudo docker push my_docker_hub_repo/stock-price
$ sudo docker push my_docker_hub_repo/portal
```

&emsp;&emsp;当Docker镜像正在构建和正在被push到Docker Hub的时候，你可以查看每个目录下的Dockerfile，了解这些具体发生什么。下面就是一些需要注意的事情：

- 每个容器都被部署为一个Ruby Sinatra服务。
- “天气”服务的容器应用被定义在weather.rb这个ruby文件中。它使用一个HTTP GET请求来获取城市名，通过一个开放的天气地图服务的HTTP API调用来获取指定城市的温度，并且将其作为一个JSON对象返回。
- “股票价格”服务的容器应用被定义在stocks.rb这个ruby文件中。它使用一个HTTP GET请求来获取股票代码，通过对Yahoo Finance服务的HTTP API调用来获取公司名称以及指定代码的股票价格，并且将其作为一个JSON对象返回。
- “门户”服务的容器应用被定义在portal.rb这个ruby文件中。它包含一个定义在public/index.html中的网页，它有两个部分，分别用于展示股票价格和城市温度。在表单中输入股票代码，这个应用就会使用Consul服务发现框架通过DNS查询来查找“股票价格”服务，以获取一个SRV记录。Consul代理就会返回对应的IP地址和端口号，然后”门户”服务的应用就会调用这个服务来获取公司名和股票价格，并且在页面中展示这些结果。“天气”服务也会经历类似的过程，根据用户输入的城市名，“门户”服务的应用会通过DNS查找天气服务的IP地址和端口，并且通过一次API调用来获取这个城市的温度。

&emsp;&emsp;如下所示是一段在这个门户组件中用于服务发现的Ruby函数的代码片段。这个函数传入了一个服务名，发送了一个DNS查询（使用该服务名查找一个SRV记录），并且返回运行这个服务的IP地址和端口号。

```
def lookup_service(service_name)
  resolver = Resolv::DNS.open
  record = resolver.getresource(service_name, Resolv::DNS::Resource::IN::SRV)
  return resolver.getaddress(record.target), record.port
end
```

&emsp;&emsp;所有的服务都作为ECS的服务部署，并且会通过运行在每个ECS实例上的注册器代理，自动地被注册到Consul服务器上。

## 创建任务定义
&emsp;&emsp;现在你需要创建ECS任务定义，来启动之前在你的ECS集群中构建的容器。打开[ECS控制台](https://console.aws.amazon.com/ecs/home?region=us-east-1#/taskDefinitions)中的任务定义菜单。

&emsp;&emsp;8.创建股票价格的[ECS任务定义](http://docs.aws.amazon.com/zh_cn/AmazonECS/latest/developerguide/task_defintions.html)。你可以使用如下的模板，用你自己的仓库名代替my_docker_hub_repo。

```
{
    "family": "stock-price",
    "containerDefinitions": [
        {
            "environment": [
                {
                    "name": "SERVICE_4567_NAME",
                    "value": "stock-price"
                },
                {
                    "name": "SERVICE_4567_CHECK_HTTP",
                    "value": "/health"
                },
                {
                    "name": "SERVICE_4567_CHECK_INTERVAL",
                    "value": "10s"
                },
                {
                    "name": "SERVICE_TAGS",
                    "value": "http"
                }
            ],
            "name": "stock-price",
            "image": "my_docker_hub_repo/stock-price",
            "cpu": 100,
            "memory": 200,
            "portMappings": [
                {
                    "containerPort": 4567
                }
            ],
            "essential": true
        }
    ]
}
```

&emsp;&emsp;它添加了一些元数据，注册器代理通过ECS任务定义中的环境变量，使用Consul和这些数据来定制化这样的服务定义，包括：

- 设置Consul服务器内的服务名为“stock-price”
- 添加健康检查，通过每10秒钟调用一次“/health” URL
- 添加一个值为"http"的服务标签(service tag)

&emsp;&emsp;注意，你并不是在定义主机端口映射。Docker会自动地分配主机上的一个端口，这样这个端口号就可以通过Consul的服务发现来被别人发现。你也可以在单个ECS实例上运行多个同种类型的任务或者服务。

&emsp;&emsp;9.创建“天气”服务的[ECS任务定义](http://docs.aws.amazon.com/zh_cn/AmazonECS/latest/developerguide/task_defintions.html)。你可以使用如下的模板，用你自己的仓库名代替my_docker_hub_repo。

```
{
    "family": "weather",
    "containerDefinitions": [
        {
            "environment": [
                {
                    "name": "SERVICE_4567_NAME",
                    "value": "weather"
                },
                {
                    "name": "SERVICE_4567_CHECK_HTTP",
                    "value": "/health"
                },
                {
                    "name": "SERVICE_4567_CHECK_INTERVAL",
                    "value": "10s"
                },
                {
                    "name": "SERVICE_TAGS",
                    "value": "http"
                }
            ],
            "name": "weather",
            "image": "my_docker_hub_repo/weather",
            "cpu": 100,
            "memory": 200,
            "portMappings": [
                {
                    "containerPort": 4567
                }
            ],
            "essential": true
        }
    ]
}
```

&emsp;&emsp;它添加了一些元数据，注册器代理通过ECS任务定义中的环境变量，使用Consul和这些数据来定制化这样的服务定义，包括：

- 设置Consul服务器内的服务名为“weather”
- 添加健康检查，通过每10秒钟调用一次“/health” URL
- 添加一个值为"http"的服务标签(service tag)

&emsp;&emsp;10.创建“门户”服务的[ECS任务定义](http://docs.aws.amazon.com/zh_cn/AmazonECS/latest/developerguide/task_defintions.html)。你可以使用如下的模板，用你自己的仓库名代替my_docker_hub_repo。

```
{
    "family": "portal",
    "containerDefinitions": [
        {
            "name": "portal",
            "image": "my_docker_hub_repo/portal",
            "cpu": 100,
            "memory": 200,
            "portMappings": [
                {
                    "containerPort": 4567,
                    "hostPort": 80
                }
            ],
            "essential": true
        }
    ]
}
```

## 创建ECS服务
&emsp;&emsp;11.在ECS控制台的 ___Service___ 中，选择 ___Create___ 。选择第7步中的任务定义，命名这个服务，并且设置任务的数字为1。选择 ___Create service___ 。

&emsp;&emsp;12.重复第11步,创建第8步和第9步中的任务，以启动“股票价格”和"门户"服务。

&emsp;&emsp;13.这些服务将会立刻开始运行。你可以点击服务的 ___Tasks___ 栏内的刷新图标。在“门户”服务的状态变成“Running”之后，选择该任务并扩展"门户"服务容器。这个“门户”服务的容器实例IP就是 ___External link___ 域内 ___Network binding___ 下的超链接。打开“门户”服务的URL。

&emsp;&emsp;14.你可以在文字输入框内输入股票代码，然后“门户”服务就会查找“股票价格”服务，并且获取最新股票价格和公司名。你也可以在页面的天气板块输入一个城市名，它就会查找“天气”服务获取最新的摄氏度温度。

## 结论
&emsp;&emsp;如果你遵循了上面的所有步骤,你应该有三个运行在ECS集群上的容器了。一个用于"天气"服务，返回一个城市的温度。另一个用于"股票价格"服务，返回股票价格和指定股票代码所对应的公司名。最后一个容器用于“门户”服务应用，提供一个用户端的网页，可以通过部署在ECS实例上的Consul代理查询天气和股票价格。 <br/>
&emsp;&emsp;查看这些容器的源代码，重复这些步骤，用这样的服务发现功能来构建你自己的微服务架构吧！
