package com.kb.inno.common;

import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils {
	
	/**
	 * 문자열에 공백이 들어온 경우 "0"으로 리턴하는 메소드
	 * @param value
	 * @return value
	 */
    public static String nullToZero(String value) {
        if (value == null || value.equals("")) {
            value = "0";
        }

        return value;
    }

    /**  
     * 문자열 좌측의 공백을 제거하는 메소드 
     * @param  str 대상 문자열 :
     * @return trimed string with white space removed from the front. 
     */
    public static String ltrim(String str) {
        int len = str.length();
        int idx = 0;
        while ((idx < len) && (str.charAt(idx) <= ' ')) {
            idx++;
        }

        return str.substring(idx, len);
    }
    
    /**
     * 문자열 우측의 공백을 제거하는 메소드
     * @param  str 대상 문자열
     * @return trimed string with white space removed from the end
     */
    public static String rtrim(String str) {
    	int len = str.length();
    	
    	while ((0 < len) && str.charAt(len - 1) <= ' ') {
    		len--;
    	}
    	
    	return str.substring(0, len);
    }
}
