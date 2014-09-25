package com.companyname.projectname.util;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

/**
 * @author weinianjie
 * @date 2014年7月18日
 * @time 下午5:35:18
 */
public abstract class XmlImpl {
    
    protected Document doc;
    protected Element root;
    
    public Element getRoot() {
        return root;
    }
    
    public void setRoot(Element root) {
        this.root = root;
        this.doc.setRootElement(root);
    }
    
    public int getInt(String xpath, int def) {
    	return this.getInt(xpath, root, def);
    }
    
    public int getInt(String xpath, Element parent, int def) {
    	try{
    		return Integer.parseInt(find(xpath, parent).getText());
    	}catch(Exception e) {
    		return def;
    	}
    }    
    
    public String getString(String xpath, String def) {
    	return this.getString(xpath, root, def);
    }
    
    public String getString(String xpath, Element parent, String def) {
    	try{
    		return this.find(xpath, parent).getText();
    	}catch(Exception e) {
    		return def;
    	}
    }
    

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
    
    public Element find(String xpath) {
        return find(xpath, root);
    }
    
    
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
    
    public List<Element> finds(String xpath) {
        return finds(xpath, root);
    }
}
