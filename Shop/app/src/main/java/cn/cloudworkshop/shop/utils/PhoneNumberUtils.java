package cn.cloudworkshop.shop.utils;

import android.text.TextUtils;

/**
 * Author：Libin on 2016-10-20 12:07
 * Email：1993911441@qq.com
 * Describe：手机号判断
 */

public class PhoneNumberUtils {
    public static boolean judgePhoneNumber(String phoneNumber) {
        return isMatchLength(phoneNumber) && isMobileNo(phoneNumber);
    }


    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @return
     *
     */
    private static boolean isMatchLength(String str) {
        return !TextUtils.isEmpty(str) && str.getBytes().length == 11;
    }

    /**
     * 验证手机号码格式
     */
    private static boolean isMobileNo(String mobileNo) {
        // "1"代表第1位为数字1，"[3-9]"代表第二位可以为3-9中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "1[3-9]\\d{9}";
        return !TextUtils.isEmpty(mobileNo) && mobileNo.matches(telRegex);
    }

}








