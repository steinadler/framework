package com.companyname.projectname.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author weinianjie
 * @date 2014年7月31日
 * @time 下午2:54:30
 */
public class ValidateTools {
	
	/**
	 * 校验是否是uuid
	 * @param str
	 * @return
	 */
	public static boolean isGUID(String str) {
		if(str != null) {
			Pattern pat = Pattern.compile("[0-9a-zA-Z]{32}");  
			Matcher mat = pat.matcher(str);  
			return mat.find();
		}
		return false;
	}
}
