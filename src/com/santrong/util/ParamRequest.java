package com.santrong.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class ParamRequest {
	// private static final SimpleDateFormat sdf = new SimpleDateFormat();
	public static final String DF_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DF_MONTH_PATTERN = "yyyy-MM";
	public static final String DF_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DF_TIME_PATTERN = "HH:mm:ss";
	public static final String DF_TIME_MINUTE_PATTERN = "HH:mm";	
    
    /**
     * 
     * @param request
     * @param field
     * @return
     */
    public static String getString(HttpServletRequest request, String field) {
        String ret = null;
        String s = request.getParameter(field);
        //过滤特殊字符
        if (s != null) {
            ret = s.replace("'", "").replace("<", "").replace(">", "").replace("&", "");
        }
        return ret;
    }
    
    public static String getString(HttpServletRequest request, String field, String defaultValue) {
        String ret = defaultValue;
        String s = request.getParameter(field);
        //过滤特殊字符
        if (s != null) {
            ret = s.replace("'", "").replace("<", "").replace(">", "").replace("&", "");
        }
        return ret;
    }
    
    /**
     * 
     * @param request
     * @param field
     * @return
     */
    public static String[] getStringValues(HttpServletRequest request, String field) {
        String ret[] = null;
        String[] arr = request.getParameterValues(field);
        
        if (arr != null) {
            //过滤特殊字符
            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i].replace("'", "").replace("<", "").replace(">", "").replace("&", "");
            }
            ret = arr;
        }
        return ret;
    }
    
    /**
     * 
     * @param request
     * @param field
     * @return
     */
    public static int getInt(HttpServletRequest request, String field) {
        return getInt(request, field, -9999999);
    }
    
    /**
     * 
     * @param request
     * @param field
     * @return
     */
    public static int getInt(HttpServletRequest request, String field, int defaultValue) {
        int ret = defaultValue;
        String s = request.getParameter(field);
        if (s != null) {
            try {
                ret = Integer.parseInt(s);
            } catch (NumberFormatException e) {
            }
        }
        return ret;
    }
    
    /**
     * 
     * @param request
     * @param field
     * @return
     */
    public static long getLong(HttpServletRequest request, String field) {
        long ret = -9999999;
        String s = request.getParameter(field);
        if (s != null && s.matches("[0-9]+")) {
            ret = Long.parseLong(s);
        } else {
            //待补充：空值或非数字格式 ，需在这里抛出异常（自定义异常）
        }
        return ret;
    }
    
    /***
     * 
     * @param request
     * @param field
     * @return
     */
    public static Date getMonth(HttpServletRequest request, String field) {
        String s = request.getParameter(field);
        Date date = null;
        if (CommonTools.isNotNull(s)) {
            date = CommonTools.stringToDate(s, CommonTools.DF_MONTH_PATTERN);
        }
        return date;
    }
    
    /***
     * 
     * @param request
     * @param field
     * @return
     */
    public static Date getDate(HttpServletRequest request, String field) {
        String s = request.getParameter(field);
        Date date = null;
        if (CommonTools.isNotNull(s)) {
            date = CommonTools.stringToDate(s, CommonTools.DF_DATE_PATTERN);
        }
        return date;
    }
    
    /***
     * 
     * @param request
     * @param field
     * @return
     */
    public static Date getTime(HttpServletRequest request, String field) {
        String s = request.getParameter(field);
        Date date = null;
        if (CommonTools.isNotNull(s)) {
            date = CommonTools.stringToDate(s, CommonTools.DF_TIME_PATTERN);
        }
        return date;
    }
    
    /***
     * 
     * @param request
     * @param field
     * @return
     */
    public static Date getTimeMinute(HttpServletRequest request, String field) {
        String s = request.getParameter(field);
        Date date = null;
        if (CommonTools.isNotNull(s)) {
            date = CommonTools.stringToDate(s, CommonTools.DF_TIME_MINUTE_PATTERN);
        }
        return date;
    }
    
    /***
     * 
     * @param request
     * @param field
     * @return
     */
    public static Date getDateTime(HttpServletRequest request, String field) {
        String s = request.getParameter(field);
        Date date = null;
        if (CommonTools.isNotNull(s)) {
            date = CommonTools.stringToDate(s, CommonTools.DF_FULL_PATTERN);
        }
        return date;
    }
    
    public static boolean getBoolean(HttpServletRequest request, String field) {
        String s = request.getParameter(field);
        if (s == null) {
            return false;
        }
        
        if ("true".equals(s) || "y".equals(s)  || "1".equals(s)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean getBoolean(HttpServletRequest request, String field, boolean defaultValue) {
        String s = request.getParameter(field);
        if (s == null) {
            return defaultValue;
        }
        
        if ("true".equals(s) || "y".equals(s)  || "1".equals(s)) {
            return true;
        } else {
            return false;
        }
    }
    
    
	/**
	 * 日期字符串转换为日期
	 * 
	 * @param string
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String string, String pattern) {
		if( isNull(string)){
			return null;
		}
		if (!isNotNull(pattern)) {
			pattern = DF_FULL_PATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		try {
			return sdf.parse(string);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}    
	
	/**
	 * 是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 * 是否不为空

	 * 
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotNull(String value) {
		return !isNull(value);
	}	
}
