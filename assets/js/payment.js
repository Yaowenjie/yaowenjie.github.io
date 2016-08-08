function switchPayment() {
  var alipayQR = document.getElementById("alipay-QR");
  var wechatQR = document.getElementById("wechat-QR");
  var alipayRadio = document.getElementById("alipay");

  alipayQR.style.display = (alipayRadio.checked) ? 'block' : 'none';
  wechatQR.style.display = (alipayRadio.checked) ? 'none' : 'block';
}
