---
layout: page
title: 作者的闲言碎语
tags: [Wenjie]
imagefeature: wj/99.jpg
---
<figure>
  <img src="{{ site.url }}/images/wj/head1.jpg" alt="Wenjie Yao">
  <figcaption>Yao Wenjie</figcaption>
</figure>

> Developer ・ 开花猿
>
> Consultant ・ 自熏师
>
> Engineer ・ 攻城狮
>
> ThoughtWorker ・ 骚窝客

---

现在，<br/>
我那什么，<br/>
什么也不想说，

因为，<br/>
我并不知道，<br/>
知道说什么

但是，<br/>
听大家说，<br/>
换行，<br/>
就可以成诗

---

> 非常抱歉，
>
> 你要的某些功能
>
> 暂时可能
>
> 还没有
>
> 因为这个家伙
>
> ### *比较懒* ###
>
###### 但是你仍然可以star/fork[该博客的github repo](https://github.com/Yaowenjie/yaowenjie.github.io)，[列issue](https://github.com/Yaowenjie/yaowenjie.github.io/issues)给我，[发邮件](mailto:wsywj61@gmail.com)吐槽我，或者直接在[博客留言板]({{ site.url }}/message-board)留言
>
###### 我不会告诉你，你可能忽略了博客下面那栏社交账号，以及每篇文章下的分享按钮，哈哈哈哈啊哈哈哈


### 鼓励/支持我继续创作
<div id="payment-group">
<fieldset class="switch">
  <span id='legend-text'>扫码打赏我: </span>

	<input id="alipay" name="view" type="radio" checked onClick="switchPayment()">
	<label for="alipay">支付宝</label>

	<input id="wechatpay" name="view" type="radio" onClick="switchPayment()">
	<label for="wechatpay">微信</label>

	<span class="switch-button"></span>
</fieldset>
</div>

<center><img id="alipay-QR" src="{{ site.url }}/images/alipay-QR.jpg" alt="alipay-QR.jpg"></center>
<center><img id="wechat-QR" src="{{ site.url }}/images/wechat-QR.jpg" alt="wechat-QR.jpg" style="display: none"></center>

<script type="text/javascript">
  function switchPayment() {
    var alipayQR = document.getElementById("alipay-QR");
    var wechatQR = document.getElementById("wechat-QR");
    var alipayRadio = document.getElementById("alipay");

    alipayQR.style.display = (alipayRadio.checked) ? 'block' : 'none';
    wechatQR.style.display = (alipayRadio.checked) ? 'none' : 'block';
  }
</script>


<style>
#payment-group {
  position: relative;
  margin: 20px auto;
  padding: 20px 20px 23px;
  border: 1px dashed rgba(0, 0, 0, .5);
  background-image: linear-gradient(top, rgba(255, 255, 255, .1), rgba(0, 0, 0, .1));
  background-color: #555;
  width: 380px;
  border-radius: 5px;
  box-shadow: 0 0 0 3px #555,
              -2px 3px 0px 2px rgba(0, 0, 0, .6),
              2px -2px 0px 2px rgba(255, 255, 255, .6),
              -5px 5px 20px 0px rgba(0, 0, 0, .6);
  line-height: 1;
}

#payment-group:after {
  content: " ";
  position: absolute;
  z-index: 1;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  border-radius: 5px;
  border: 1px dashed #fff;
}

.switch {
  position: relative;
  border: 0;
  padding: 0;
  width: 300px;
  font-family: helvetica;
  font-weight: bold;
  font-size: 22px;
  color: #222;
  text-shadow: 0 1px 0 rgba(255, 255, 255, .3);
}

.switch #legend-text {
  width: 150px;
  float: left;
  width: 50%;
  padding: 7px 10% 3px 0;
  text-align: center;
  color: rgba(253, 223, 225, 0.6);
}

.switch input {
  position: absolute;
  opacity: 0;
}

.switch #legend-text:after {
  content: '';
  position: absolute;
  top: 0;
  left: 50%;
  z-index: 0;
  width: 50%;
  height: 100%;
  padding: 2px;
  background-color: #222;
  border-radius: 3px;
  box-shadow: inset -1px 2px 5px rgba(0, 0, 0, .8), 0 1px 0 rgba(255, 255, 255, .2);
}

.switch label {
  position: relative;
  z-index: 2;
  float: left;
  width: 25%;
  margin-top: 2px;
  padding: 5px 0 3px 0;
  text-align: center;
  color: #64676b;
  text-shadow: 0 1px 0 #000;
  cursor: pointer;
  transition: color 0s ease .1s;
}

.switch input:checked + label {
  color: #2d592a;
  text-shadow: 0 1px 0 rgba(255, 255, 255, .5);
}

.switch input:focus + label {
  outline: none;
}

.switch .switch-button {
  clear: both;
  position: absolute;
  top: -1px;
  left: 50%;
  z-index: 1;
  width: 25%;
  height: 100%;
  margin: 2px;
  background-color: #70c66b;
  background-image: linear-gradient(top, rgba(255, 255, 255, .2), rgba(0, 0, 0, 0));
  border-radius: 3px;
  border: 1px dashed rgba(0, 0, 0, .3);
  box-shadow: 0 0 0 3px #70c66b, -2px 3px 5px #000;
  transition: all .3s ease-out;
}

.switch .switch-button:after {
  content: " ";
  position: absolute;
  z-index: 1;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  border-radius: 3px;
  border: 1px dashed #fff;
}

.switch input:last-of-type:checked ~ .switch-button {
  left: 75%;
}
</style>
