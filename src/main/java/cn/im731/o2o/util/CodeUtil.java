package cn.im731.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeInput = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        if (verifyCodeInput == null || !verifyCodeExpected.equals(verifyCodeInput)) {
            return false;
        }
        return true;
    }
}
