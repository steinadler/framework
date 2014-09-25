package com.companyname.projectname.util;

import java.io.File;
import java.io.StringReader;

import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * @author weinianjie
 * @date 2014年7月18日
 * @time 下午5:29:22
 */
public class XmlReader extends XmlImpl {
    
    /**
     * 打开xml文件
     * @param xmlFilePath
     */
    public boolean open(String xmlFilePath) {
        try {
            File file = new File(xmlFilePath);// 创建文件对象
            SAXBuilder b = new SAXBuilder();
            doc = b.build(file);
            root = doc.getRootElement();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 解析字符串     * @param xml
     */
    public boolean parse(String xml) {
        try {
            SAXBuilder sb = new SAXBuilder();
            StringReader read = new StringReader(xml);
            InputSource source = new InputSource(read);
            doc = sb.build(source);
            root = doc.getRootElement();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
