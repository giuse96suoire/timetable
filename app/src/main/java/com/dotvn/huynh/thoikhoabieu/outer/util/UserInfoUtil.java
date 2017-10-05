package com.dotvn.huynh.thoikhoabieu.outer.util;

import com.dotvn.huynh.thoikhoabieu.outer.data.Constant;

/**
 * Created by Manh Hoang Huynh on 16/09/2017.
 */

public class UserInfoUtil {
    public static String convertPhoneNumberToE164(String phoneNumber) {
        if (phoneNumber.indexOf(Constant.CODE_VIETNAM) == 0) {
            return phoneNumber;
        }
        if (phoneNumber.indexOf("0") == 0) {
            return Constant.CODE_VIETNAM+phoneNumber.substring(1, phoneNumber.length());
        }
        return Constant.CODE_VIETNAM + phoneNumber;
    }
    public static String convertE164ToPhoneNumber(String e164Phone) {
       if (e164Phone.contains(Constant.CODE_VIETNAM)) {
           return e164Phone.replace(Constant.CODE_VIETNAM, "0");
       }
        if (e164Phone.contains("+")) {
            return e164Phone.replace("+", "");
        }
        return e164Phone;
    }
    public static String convertPhoneNumberToEmail(String phoneNumber) {

        return convertE164ToPhoneNumber(phoneNumber)+Constant.DOMAIN;
    }
}
