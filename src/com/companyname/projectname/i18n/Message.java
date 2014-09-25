package com.companyname.projectname.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 国际化 */
public class Message {
    
    private static final Message instance = new Message();
    
    private String baseName;
    private String lang;
    private ResourceBundle message;
    
    public static void main(String[] args) {
        //notes_upgrade_1=1、上传升级文件需要一定时间，请{0}不要{1}进行其他的操作。
        
        Message message = Message.getInstance();
        System.out.println(message.getString("notes_upgrade_1"));
        System.out.println(message.getString("notes_upgrade_1", new String[]{"lis", "lhy"}));
        System.out.println(message.getDynamicString("text is #{notes_upgrade_1} text2 is #{notes_upgrade_1}"));
        System.out.println(message.getDynamicString("text is #{notes_upgrade_1} text2 is #{notes_upgrade_1}", new String[]{"lis", "lhy", "lis2", "lhy2"}));
    }
    
    private Message() {
        baseName = "message";
        lang = Locale.getDefault().toString();
        if (lang == null) {
            lang = "zh_CN";
        }
        message = ResourceBundle.getBundle(baseName);
    }
    
    public static Message getInstance() {
        return instance;
    }
    
    /**
     * 设置国际化文件，如message_en_US.properties
     * @param baseName  前缀，如message
     * @param lang  后缀，如en_US
     */
    public void setI18nFile(String baseName, String lang) {
        if (baseName == null) {
            baseName = "message";
        }
        if (lang == null) {
            lang = "zh_CN";
        }
        
        if (this.baseName != null && this.baseName.equals(baseName)
                && this.lang != null && this.lang.equals(lang)) {
            return;
        }
        
        this.baseName = baseName;
        this.lang = lang;
        String[] locale = lang.split("_");
        message = ResourceBundle.getBundle(baseName, new Locale(locale[0], locale[1]));
    }
    
    /**
     * 获取国际化文本
     * @param key
     * @return
     */
    public String getString(String key) {
        return message.getString(key);
    }
    
    /**
     * 获取国际化文本，会替换文本中{数字}为params[数字]
     * @param key
     * @param params
     * @return
     */
    public String getString(String key, String[] params) {
        String value = message.getString(key);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                value = value.replace("{" + i + "}", params[i]);
            }
        }
        return value;
    }
    
    /**
     * 获取国际化文本，会替换文本中#{key}为国际化文本
     * @param text
     * @return
     */
    public String getDynamicString(String text) {
        Pattern p = Pattern.compile("[#][{]([\\w\\d_-]|[.])+[}]");
        Matcher m = p.matcher(text);
        String group = null;
        String key = null;
        String value = null;
        String result = text;
        while (m.find()) {
            group = m.group();
            key = group.substring(2, group.length() - 1);
            if (message.containsKey(key)) {
                value = getString(key);
                group = "[#][{]" + key + "[}]";
                result = result.replaceFirst(group, value);
            }
        }
        return result;
    }
    
    /**
     * 获取国际化文本，会替换文本中#{key}为国际化文本, {数字}为params[数字]
     * @param text
     * @param params
     * @return
     */
    public String getDynamicString(String text, String[] params) {
        Pattern p = Pattern.compile("[#][{]([\\w\\d_-]|[.])+[}]");
        Matcher m = p.matcher(text);
        String group = null;
        String key = null;
        String value = null;
        String result = text;
        int index = 0;
        int paramIndex = 0;
        while (m.find()) {
            group = m.group();
            key = group.substring(2, group.length() - 1);
            if (message.containsKey(key)) {
                value = getString(key);
                index = 0;
                while (value.indexOf("{" + index + "}") != -1) {
                    value = value.replace("{" + index + "}", params[paramIndex++]);
                    index++;
                }
                group = "[#][{]" + key + "[}]";
                result = result.replaceFirst(group, value);
            }
        }
        return result;
    }
    
    public String getLang(){
    	return this.lang;
    }
    
}
