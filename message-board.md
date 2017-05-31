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


> 走过，路过，言语两句：

{% if page.comments == true %}
  {% include likee_comment.html %}
{% endif %}
