---
layout: "post"
title: "[译]如何调试JEST测试？"
date: "2016-08-24 11:26"
published: true
comments: true
tags: [JEST, react, debug]
category: Front-end
imagefeature: wj/10.jpg
---
##### 本文翻译自[liusy182](https://liusy182.wordpress.com/2015/03/12/jest-how-do-you-debug-it/)

&emsp;&emsp;[Jest框架](https://facebook.github.io/jest/)是facebook旗下一款单元测试框架，我个人十分喜欢它，因为它自动mock这一点十分强大。然而，当它遇到问题的时候，就会经常抛出一些模糊的调用栈信息。我在网上搜索尝试找到如何debug Jest测试的方法，却很难找到有用的信息。总之，它仍然还是一个比较新的测试框架。

&emsp;&emsp;Jest使用虚拟DOM来运行测试。这一点不同于Karma和Jasmine（它们是利用浏览器来运行测试的）。我觉得这就会给它带来一个很大的缺点：不能使用浏览器上的调试工具来调试Jest的测试。因此，我们需要借助于Node/V8引擎自带的调试器。[Node默认的调试器](https://nodejs.org/api/debugger.html)是完全基于命令行形式的，类似于GDB - 虽然我从来就不是一个命令行调试的拥簇，但先还是解决这个问题吧。

<!--more-->

## 首次尝试
&emsp;&emsp;要调试(debug)一个Jest测试，比如说“myView-test.js”，我们需要使用如下的node指令来实现：

```js
node debug --harmony .\node_modules\jest-cli\bin\jest.js --runInBand myView-test.js
```
&emsp;&emsp;接下来让我来解释一下我们这里到底做了什么：

- "node debug"将会启动node自带的调试器。“debug”会调用一个V8引擎调试器的wrapper。这个wrapper提供了一系列的指令，用于在代码中跳进跳出和跟踪，而这些指令都不会出现在V8的“node -debug”中。（我承认它们看起来很相似，一个“-”符号之差）
- "-harmony"标志是为了让Jest正确地运行。更多关于harmony的信息可以戳[这里](http://stackoverflow.com/questions/13351965/what-does-node-harmony-do)。
- “.\node_modules\jest-cli\bin\jest.js”就是Jest的入口。这个文件会在我调用“\node_modules\.bin”里的“Jest”时被调用。
- "-runInBand"告诉Jest在当前的进程中运行所有测试，而不是再启动一个进程。Jest默认就会启动多个进程并行的运行测试。如下为源码中关于这个选项的描述的片段（在.\node_modules\jest-cli\bin\jest.js中）:<br/>

```
'Run all tests serially in the current process (rather than creating a worker pool of child processes that run tests). This is sometimes useful for debugging, but such use cases are pretty rare.'
---
'在当前的进程中顺序地运行所有测试（而不是创建一个包含诸多子进程的进程池来运行测试）。这便于调试，但是使用它的场景确实挺少的。'
```
- “myView-test.js”就是我们想要debug的测试文件。像这样使用相对路径是没有问题的，因为Jest会把它转换为一段正则表达式。

&emsp;&emsp;一旦启动了调试器，我们就可以看到调试器会在jest.cs的第一行就暂停了。可以使用Node调试器支持的API来跟踪代码。或者也可以在测试中添加断点:

```js
debug> sb('C:\\example\\reactApp\\__tests__\\myView-test.js', 1)
```

&emsp;&emsp;Node调试器提供了一系列的有用指令：

```sh
cont, c - 继续执行
next, n - 跳到下一步
step, s - 跳进
out, o - 跳出
pause - 暂停执行的代码 (就像开发工具中的暂停按钮)

setBreakpoint(), sb() - 在当前行设置断点
setBreakpoint(line), sb(line) - 特定行设置断点
setBreakpoint('fn()'), sb(...) - 在函数体内的第一段声明中设置断点
setBreakpoint('script.js', 1), sb(...) - 在script.js的第一行设置断点
clearBreakpoint, cb(...) - 清楚断点

backtrace, bt - 打印当前执行帧的回溯
list(5) - 列出脚本源代码的5行内容（前面和后面5行）
watch(expr) - 向观察列表中添加表达式
unwatch(expr) - 去掉观察列表中的表达式
watchers - 列出所有的watcher以及它们的值（在每段断点处自动列出）
repl - 在调试脚本的内容时打开调试器的repl用于评估

run - 运行脚本（在调试开始时自动运行）
restart - 重启脚本
kill - 结束脚本
```

## 使用WebStorm 9
&emsp;&emsp;因为我从来都不是命令行debugger的拥簇，所以使用Node的调试器我很快就会迷失了。我开始思考在怎么把Jest的调试工作集成到像[WebStorm 9](https://www.jetbrains.com/webstorm/)这样强大的IDE中。

&emsp;&emsp;就从这里开始，使用WebStorm我需要应付两个问题：

1. 没办法直接使用“node debug”.
2. WebStorm并没有对Jest的直接支持（尽管它支持Karma和Mocha...）

&emsp;&emsp;我最终还是解决了这个问题，那就是搭建一个针对Node的调试配置项，但是把它用作于Jest测试调试。WebStorm会在它尝试调试Node的时候自动加上V8的标志"-debug"。我们只需要配置好它，就可以调试我们想要调试的JS测试。

&emsp;&emsp;下面就是具体的步骤:

1. 点击菜单“Run –  Edit Configurations…”
2. 添加一个新的“Node.js”配置并且像下面这样填写对应的配置项：<center><img class="center" src="{{ site.url }}/images/2016/jest-1.png" alt="jest.png"></center>
3. 保存并关闭对话窗口。在编辑器内里面任意地方添加断点<center><img class="center" src="{{ site.url }}/images/2016/jest-2.png" alt="jest.png"></center>
4. 点击菜单中“Run –  Debug…”并且选择我们刚刚保存的配置。这会启动一个调试的shell来运行这些测试。并且它最终会停到我们设置的断点处。
5. 现在，我们可以使用WebStorm的调试器（出现在IDE的底部）来调试Jest测试啦！
