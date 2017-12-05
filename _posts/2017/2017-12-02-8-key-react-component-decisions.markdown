---
layout: "post"
date: "2017-12-02 15:22"
title: "[译]React组件的8项关键决策 - 标准化React开发"
description: “8 Key React Component Decisions”
published: true
comments: true
category: Front-end
tags: [React, Decision]
imagefeature: wj/43.jpg
---
###### 本文翻译自Cory House的[博客文章](https://medium.freecodecamp.org/8-key-react-component-decisions-cc965db11594)。

&emsp;&emsp;React自2013年开源以来，逐步进化演变。当你在网上搜索它时，你会偶尔发现一些讲述过时方法的旧文章。本文就是讲述了团队中编写React组件时需要采取的八项关键决策。

<!--more-->

### 决策1：开发环境

&emsp;&emsp;在你编写第一个组件之前，你的团队往往需要在开发环境上达成共识。这里存在很多观点。。。

<center><img class="center" src="{{ site.url }}/images/2017/react-1.png" alt="react.png"></center>

&emsp;&emsp;当然，你可以[从头构建一个JS的开发环境](https://www.pluralsight.com/courses/javascript-development-environment)。25%的React开发者这么做。我当前的团队使用了create-react-app的一个fork版本，不过添加了一些额外的功能，比如[支持CRUD逼真的mock API](https://medium.freecodecamp.org/rapid-development-via-mock-apis-e559087be066),[可复用的组件库](https://www.pluralsight.com/courses/react-creating-reusable-components)，以及一些代码检查优化处理措施（我们也检查测试文件，而create-react-app是忽略的）。我喜欢create-react-app，但是[这个工具会帮助你比较众多具有竞争力的替代方案](https://www.andrewhfarmer.com/starter-project/)。想在服务器端渲染？你可以看看[Gatsby](http://gatsbyjs.org)和[Next.js](https://github.com/zeit/next.js/)。你甚至可以考虑使用像[CodeSandbox](https://codesandbox.io)这样的线上编辑器。

### 决策2：Types

&emsp;&emsp;你可以忽略类型，可以使用[prop-types](https://reactjs.org/docs/typechecking-with-proptypes.html)，使用[Flow](https://flow.org)，或者使用[TypeScript](https://www.typescriptlang.org)。请注意prop-type在React 15.5中被抽成了一个[独立的库](https://www.npmjs.com/package/prop-types)，所以一些旧文章告诉你的一些import已不再起作用了。

&emsp;&emsp;在这个问题上，社区上仍然存在分歧：

<center><img class="center" src="{{ site.url }}/images/2017/react-2.png" alt="react.png"></center>

&emsp;&emsp;我更喜欢用prop-types，因为我发现它在组件内提供了足够好的类型安全性。使用Babel, [Jest tests](https://facebook.github.io/jest/)， [ESlint](http://www.eslint.org)， 以及prop-types的组合后，我很难看到运行时的类型错误了。

### 决策3：createClass还是ES的Class

&emsp;&emsp;React.createClass是最初的API，在15.5版本中，它就被弃用了。有些人会觉得[转战到ES的class上去有些为时过早](https://medium.com/dailyjs/we-jumped-the-gun-moving-react-components-to-es2015-class-syntax-2b2bb6f35cb3)。但不管怎样，createClass风格已经从React的核心库中移出去了，取而代之的是React官方文档中[“React without ES6”](https://reactjs.org/docs/react-without-es6.html)这一页内容。所以这就很清楚了：ES(ECMAScript)的class才是未来。你也可以使用[react-codemod](https://github.com/reactjs/react-codemod)把createClass简单地转化成ES的class。

### 决策4：Class还是Function

&emsp;&emsp;你可以使用class或者function来定义React组件。Class用在使用ref和生命周期方法的时候。这里是[在可能的情况下考虑使用function的9个理由](https://hackernoon.com/react-stateless-functional-components-nine-wins-you-might-have-overlooked-997b0d933dbc)。不过，[function组件也有一些缺点](https://hackernoon.com/react-stateless-functional-components-nine-wins-you-might-have-overlooked-997b0d933dbc)。

### 决策5：State管理

&emsp;&emsp;你可以使用自带的React组件state。它就足够了。[状态提升](http://www.css88.com/react/docs/lifting-state-up.html)可以很好地运作。或者，你会喜欢Redux或MobX：

<center><img class="center" src="{{ site.url }}/images/2017/react-3.png" alt="react.png"></center>

&emsp;&emsp;[我是Redux的粉丝](https://www.pluralsight.com/courses/react-redux-react-router-es6)，但是我经常使用自带的React只因为他更加简单。在我当前的角色下，已经交付了十几个React应用了，其中只有两个值得使用Redux。我更喜欢在单个大型应用上发布许多个小型自治应用。

&emsp;&emsp;顺便要提到的是，如果你对不可变状态感兴趣，至少有[4种状态不可变的状态](https://medium.com/@housecor/handling-state-in-react-four-immutable-approaches-to-consider-d1f5c00249d5)。

### 决策6： 绑定(Binding)

&emsp;&emsp;在React组件中，至少有[六种处理绑定的方法](https://medium.freecodecamp.org/react-binding-patterns-5-approaches-for-handling-this-92c651b5af56)。这主要是因为现代JS语言提供了多种处理绑定的方式。你可以在构造方法（constuctor）里面做绑定，也可以在渲染时做绑定，在渲染方法中使用箭头方法做绑定，使用class属性做绑定，甚至使用修饰符做绑定。可以阅读[这篇文章的评论](https://medium.freecodecamp.org/react-binding-patterns-5-approaches-for-handling-this-92c651b5af56)了解更多的选项！每种方法都有它自己的优点，但是假如你可以接受试验性的功能，[我建议你使用默认的class属性(又叫做property initializers)](https://medium.freecodecamp.org/react-binding-patterns-5-approaches-for-handling-this-92c651b5af56)。
这个投票是从2016年8月份开始的。到目前为止，它显示了class属性使用人数的增长，以及createClass使用人数的减少。

<center><img class="center" src="{{ site.url }}/images/2017/react-4.png" alt="react.png"></center>

&emsp;&emsp;*附注*: 一些人对于这个问题很疑惑：为什么render方法里面的箭头方法和数据绑定可以回存在潜在的问题。真实的原因是什么？因为[它会是shouldComponentUpdate和PureComponent行为变得古怪](https://medium.freecodecamp.org/why-arrow-functions-and-bind-in-reacts-render-are-problematic-f1c08b060e36)。

### 决策7：样式

&emsp;&emsp;在这一点上，选项之间的角逐就变得非常紧张了。有50+种的方式来定义组件的样式，其中包括React的内联样式，传统CSS，Ssss/Less，[CSS模块](https://github.com/css-modules/css-modules)，以及[56个CSS-in-JS的选项](https://github.com/MicheleBertoli/css-in-js)。不是开玩笑，在[样式模块的这门课](https://www.pluralsight.com/courses/react-creating-reusable-components)里，我探索了React定义样式的几种方法，以下就是对应的总结：

<center><img class="center" src="{{ site.url }}/images/2017/react-5.png" alt="react.png" style="margin-bottom: 0;">
<p style="font-size: 12px; color: grey; text-align: center;">红色表示差，绿色表示好，灰色表示警告。</p></center>

&emsp;&emsp;那为什么在React的样式方案选项上有这么多的选择呢？因为没有明显的胜者。

<center><img class="center" src="{{ site.url }}/images/2017/react-6.png" alt="react.png"></center>

&emsp;&emsp;看上去CSS-in-JS正在增长，而CSS模块化正在逐渐失去增长动力。

&emsp;&emsp;我当前的团队使用的是Sass，搭配着BEM，这已经蛮愉快了，但是我同样喜欢[styled-components](https://www.styled-components.com)。

### 决策8：可复用逻辑

&emsp;&emsp;React最初接受[mixin](https://reactjs.org/docs/react-without-es6.html#mixins)作为组件间分享代码的一种机制。但是mixin导致了一些问题，并且现在[被视为是有害的](https://reactjs.org/blog/2016/07/13/mixins-considered-harmful.html)。你不能在ES的class组件中使用mixin，因此现在人们使用[高阶组件](https://reactjs.org/docs/higher-order-components.html)和[render 属性](https://cdb.reacttraining.com/use-a-render-prop-50de598f11ce)(又称function as child)来在组件间共享代码。

<center><img class="center" src="{{ site.url }}/images/2017/react-7.png" alt="react.png"></center>

&emsp;&emsp;高阶组件现在越来越流行，但是我更喜欢使用render 属性，因为它们往往更加易读易写。Michael Jackson最近给我推销了这个(译者注：youtube，翻不了的话请留言，我后期再换成国内源)：
<iframe width="560" height="315" src="https://www.youtube.com/embed/BcVAq3YFiuc" frameborder="0" allowfullscreen></iframe>


### 当然，这还不是全部。。。

&emsp;&emsp;这里还有更多的决策需要考虑：

- 用[js还是jsx扩展名](https://github.com/facebookincubator/create-react-app/issues/87#issuecomment-234627904)？
- 是否把[每个组件放在各自的目录下](https://medium.com/styled-components/component-folder-pattern-ee42df37ec68)？
- 是否是一个组件对应一个文件？要不要[采用每个目录下放置一个index.js文件这样让人抓狂的形式](https://hackernoon.com/the-100-correct-way-to-structure-a-react-app-or-why-theres-no-such-thing-3ede534ef1ed)？
- 如果使用propTypes，那是在class自身内使用[静态属性](https://michalzalecki.com/react-components-and-class-properties/#static-fields)，还是在底部声明它们？是否要将[propType定义的足够深](https://iamakulov.com/notes/deep-proptypes/?utm_content=buffer57abf&utm_medium=social&utm_source=twitter.com&utm_campaign=buffer)？
- 是按传统的方式在constructor里面初始化state，还是利用[属性初始化器语法](http://stackoverflow.com/questions/35662932/react-constructor-es6-vs-es7)？

&emsp;&emsp;因为React大多数情况下还是JavaScript，你同样需要考虑一份JS开发规则的常规决策清单，比如[分号](https://eslint.org/docs/rules/semi)，[逗号](https://eslint.org/docs/rules/comma-dangle)，[格式](https://github.com/prettier/prettier)，以及[事件处理器的命名](https://jaketrent.com/post/naming-event-handlers-react/)等等。


### 选择一个标准，然后自动化执行
&emsp;&emsp;关于上面的这些内容，你在野蛮生长的现如今可能会看到几十种组合。

&emsp;&emsp;那么，接下来几步就是关键了：

1. **在团队内讨论这些决策，并且将标准定义成文档。**
2. **不要在代码审查（code review）时浪费时间人工检查这些。应该用像[ESLint](https://eslint.org), [react-codemod](https://github.com/yannickcr/eslint-plugin-react)，[prettier](https://github.com/prettier/prettier)这样的工具自动验证你的标准。**
3. **需要重建已有的React组件? 使用[react-codemod](https://github.com/reactjs/react-codemod)来自动化这个过程。**

&emsp;&emsp;如果我还忽略了什么关键决策，请在下面的评论中指出。
