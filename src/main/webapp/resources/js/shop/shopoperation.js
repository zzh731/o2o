/**
 1、从后台获取信息
 2、提交
 */
//TODO 验证表单输入的合法性
$(function () {
    var initUrl = '/shopadmin/getshopinitinfo';
    var registerShopUrl = '/shopadmin/registershop';

    getShopInitInfo();

    function getShopInitInfo() {
        /**
         * 获取页面需要的初始信息
         */
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                //先获取
                var tempHtml = '';
                var tempAreaHtml = '';
                //遍历
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index)
                {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                });
                //加入html
                $('#shop-category').html(tempHtml);
                $('#shop-area').html(tempAreaHtml);
            }
        });

        /**
         * 点击提交按钮
         */
        $('#submit').click(function () {
            var shop = {};
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();
            shop.shopCategory = {
                shopCategoryId: $('#shop-category').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                areaId: $('#shop-category').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            var shopImg = $('#shop-img')[0].files[0];
            var formData = new FormData();
            formData.append('shopImg', shopImg);
            formData.append('shopStr', JSON.stringify(shop));

            //处理输入的验证码
            var verifyCodeActual = $('#j_captcha').val();
            if (!verifyCodeActual) {
                $.toast('请输入验证码！')
                return;
            }
            formData.append('verifyCodeActual', verifyCodeActual);

            $.ajax({
                url : registerShopUrl,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function (data) {
                    if (data.success) {
                        $.toast('提交成功！');
                    } else {
                        $.toast('提交失败！'+data.errMsg);
                    }
                    $('#captcha_img').click();//无论提交成功或失败，都要重新换个验证码
                }
            });
        });
    }
});