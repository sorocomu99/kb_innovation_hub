package com.kb.inno.common;

import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils {
    public static String nullToZero(String value) {
        if (value == null || value.equals("")) {
            value = "0";
        }

        return value;
    }

    /**  * 문자열 좌측의 공백을 제거하는 메소드 
     * @param  str 대상 문자열 :
     * @return trimed string with white space removed from the front. 
     */
    public static String ltrim(String str) {
        int len = str.length();
        int idx = 0;
        while ((idx < len) && (str.charAt(idx) <= ' '))
        {
            idx++;
        }

        return str.substring(idx, len);
    }
}
