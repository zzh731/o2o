function changeVerifyCode(img) {
    img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);//生成4位随机数(但不是验证码)
}