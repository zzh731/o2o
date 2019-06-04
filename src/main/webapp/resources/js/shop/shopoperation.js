/**
 1、从后台获取信息
 2、提交
 */
//TODO 验证表单输入的合法性
$(function () {
    // var shopId = getQueryString('shopId');
    var shopId = 1;
    var isEdit = shopId ? true : false;//如果shopId不为空，那么是修改店铺操作。反之是新增店铺操作
    var initUrl = '/shopadmin/getshopinitinfo';
    var registerShopUrl = '/shopadmin/registershop';
    var shopInfoUrl = '/shopadmin/getshopbyid?shopid=' + shopId;
    var editShopUrl = '/shopadmin/modifyshop';

    //判断是修改还是新增
    if (isEdit) {   //修改
        getShopInfo(shopId);
    } else {    //新增
        getShopInitInfo();
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHtml);
                $('#area').attr('data-id', shop.areaId);
            }
        });
    }

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
                data.areaList.map(function (item, index) {
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
                url: isEdit ? editShopUrl : registerShopUrl,//根据isEdit判断提交的url
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        $.toast('提交成功！');
                    } else {
                        $.toast('提交失败！' + data.errMsg);
                    }
                    $('#captcha_img').click();//无论提交成功或失败，都要重新换个验证码
                }
            });
        });
    }
});