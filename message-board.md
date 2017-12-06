---
layout: page
title: 留言板
permalink: message-board/index.html
description: 有問題，欢迎給我留言
tags: [Message]
imagefeature: wj/27.jpg
comments: true
---
<link rel="stylesheet" type="text/css" href="{{ site.url }}/assets/css/walk.css" />
<div id="walk-container">
  <div id="walk"></div>
</div>

> ##### 目前，作者仅提供了**gitment**评论接口，请登录github账户参与评论，原**多说**评论数据点击可见（由于懒，暂时未加评论提交功能）。
> 
> 走过，路过，言语两句：
{% if page.comments == true %}
  {% include comments.html %}
{% endif %}
