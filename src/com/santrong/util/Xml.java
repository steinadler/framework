package com.santrong.util;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

/**
 * xml基类
 */
public abstract class Xml {
    
    protected Document doc;
    protected Element root;
    
    /**
     * 寻找单个元素，目前只支持中间元素唯一，最后一个元素带一个属性值过滤
     * @param xpath  以/分割元素名  例如:/persons/person@id=2
     * @return
     */
    public Element find(String xpath) {
        return find(xpath, root);
    }
    
    /**
     * 寻找单个元素，目前只支持中间元素唯一，最后一个元素带一个属性值过滤     * @param xpath  以/分割元素名  例如:/persons/person@id=2
     * @return
     */
    @SuppressWarnings("unchecked")
	public Element find(String xpath, Element parent) {
		try {
			if (xpath.equals("/")) {
				return parent;
			}

			String[] nodes = xpath.substring(1).split("/");

			int index = 0;
			Element e = parent;
			int attIndex = 0;
			String node = null;
			String attName = null;
			String attValue = null;
			while (index < nodes.length) {
				node = nodes[index];
				attIndex = node.indexOf("@");
				if (attIndex != -1) {
					attName = node.substring(attIndex + 1);
					node = node.substring(0, attIndex);
					attIndex = attName.indexOf("=");
					if (attIndex != -1) {
						attValue = attName.substring(attIndex + 1);
						attName = attName.substring(0, attIndex);
					}
					List<Element> list = e.getChildren(node) ;
					for (Element item : list) {
						if (attValue!=null && attValue.equals(item.getAttributeValue(attName))) {
							return item;
						}
					}
					return null;
				}
				
				//加入判断：e为非空,才去获取子节点
				if (e != null) {
					e = e.getChild(node);
				}
				index++;
			}
			return e;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return null;
    }
    
    /**
     * 寻找所有匹配元素，目前只支持中间元素唯一，不能带属性过滤
     * @param xpath  以/分割元素名  例如:/persons/person
     * @return
     */
    public List<Element> finds(String xpath) {
        return finds(xpath, root);
    }
    
    /**
     * 寻找所有匹配元素，目前只支持中间元素唯一，不能带属性过滤
     * @param xpath  以/分割元素名  例如:/persons/person
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Element> finds(String xpath, Element parent) {
        if (xpath.equals("/")) {
            List<Element> list = new ArrayList<Element>();
            list.add(parent);
            return list;
        }
        
        String[] nodes = xpath.substring(1).split("/");
        
        int index = 0;
        Element e = parent;
        while (index < nodes.length - 1) {
            e = e.getChild(nodes[index]);
            index++;
        }
        return e.getChildren(nodes[nodes.length - 1]);
    }
    
    public Element getRoot() {
        return root;
    }
    
    public void setRoot(Element root) {
        this.root = root;
        this.doc.setRootElement(root);
    }
    
}
