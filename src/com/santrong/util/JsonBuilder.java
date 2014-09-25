package com.santrong.util;

public class JsonBuilder {
	
	private StringBuffer json;
	
	public JsonBuilder() {
		json = new StringBuffer();
		json.append("{");
	}
	
	public void add(String key, String value) {
	    json.append("'");
		json.append(key);
		if (value.startsWith("[") && value.endsWith("]")) {   // 数组，集合
		    json.append("':");
            json.append(value);
            json.append(",");
		} else {  // 属性
		    json.append("':'");
		    json.append(value);
	        json.append("',");
		}
	}
	
	public void add(String key, int value) {
        add(key, String.valueOf(value));
    }
	
	public String getJson() {
		if (json.length() > 1) {
			json.deleteCharAt(json.length() - 1);
		}
		json.append("}");
		return json.toString();
	}
	
	public static void main(String[] args) {
		JsonBuilder json = new JsonBuilder();
		json.add("name", "lis");
		json.add("sex", "F");
		System.out.println(json.getJson());
	}
	
}
