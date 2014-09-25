package com.santrong.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.santrong.log.Log;

/**
 * ini文件处理类

 */
public class Ini {
    
    // 打开ini文件路径
    private String iniFile;
    // ini编码
    private String encoding = Global.iniFileEncoding;
    // ini文件内所有数据
    
    private Map<String, Map<String, String>> datas = new HashMap<String, Map<String, String>>();
    // 开启备份方案-断电保护
    private boolean openBak = true;
    // 读取次数，防止死循环读取
    private int readCount = 0;
    
    public Ini() {
    }
    
    public Ini(String iniFile) {
        read(iniFile);
    }
    
    /**
     * 读取所有数据
     * @param iniFile
     * @return
     */
    public boolean read(String iniFile) {
        readCount++;
        
        if (readCount > 2) {
            return false;
        }
        
        this.iniFile = iniFile;
        datas.clear();
        
        BufferedReader br = null;
        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(
//                    iniFile), encoding));
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    iniFile)));
        } catch (Exception e) {
            if (openBak && copy(iniFile + ".bak", iniFile)) {
                return read(iniFile);
            }
            return false;
        }
        
        String line = null;
        char firstChar = '0';
        String type = null;
        Map<String, String> typeAtts = null;
        String attName = null;
        String attValue = null;
        int equalsIndex = 0;
        
        try {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                
                firstChar = line.charAt(0);
                switch (firstChar) {
                    case '#' : // 注释
                    case ';' :
                        continue;
                    case '[' : // 类型
                        type = line.substring(1, line.length() - 1).trim();
                        if (datas.containsKey(type)) {
                            typeAtts = datas.get(type);
                        } else {
                            typeAtts = new HashMap<String, String>();
                            datas.put(type, typeAtts);
                        }
                        break;
                    default : // 属性
                        
                        if (typeAtts == null) {
                            continue;
                        }
                        
                        equalsIndex = line.indexOf("=");
                        if (equalsIndex != -1) {
                            attName = line.substring(0, equalsIndex);
                            attValue = line.substring(attName.length() + 1);
                            typeAtts.put(attName.trim(), attValue.trim());
                        }
                        break;
                }
            }
        } catch (IOException e) {
            if (openBak && copy(iniFile + ".bak", iniFile)) {
                return read(iniFile);
            }
            return false;
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                Log.printStackTrace(e);
            }
        }
        
        if (openBak && datas.size() == 0) {
            if (copy(iniFile + ".bak", iniFile)) {
                return read(iniFile);
            }
        }
        
        return true;
    }
    
    /**
     * 无视大小写

     * @param type
     * @param attName
     * @return
     */
    public String readString(String type, String attName) {
        String attValue = null;
        for (String dType : datas.keySet()) {
            if (dType.equalsIgnoreCase(type)) {
                for (String dAttName : datas.get(dType).keySet()) {
                    if (dAttName.equalsIgnoreCase(attName)) {
                        attValue = datas.get(dType).get(dAttName);
                    }
                }
            }
        }
        
        return attValue;
    }
    
    public String readString(String type, String attName, String defaultValue) {
        String attValue = readString(type, attName);
        return attValue != null ? attValue : defaultValue;
    }
    
    public Integer readInt(String type, String attName) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return null;
        }
        
        try {
            return Integer.parseInt(attValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public int readInt(String type, String attName, int defaultValue) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return defaultValue;
        }
        
        try {
            return Integer.parseInt(attValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public Long readLong(String type, String attName) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return null;
        }
        
        try {
            return Long.parseLong(attValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public long readLong(String type, String attName, long defaultValue) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return defaultValue;
        }
        
        try {
            return Long.parseLong(attValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public Float readFloat(String type, String attName) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return null;
        }
        
        try {
            return Float.parseFloat(attValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public float readFloat(String type, String attName, float defaultValue) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return defaultValue;
        }
        
        try {
            return Float.parseFloat(attValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public Double readDouble(String type, String attName) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return null;
        }
        
        try {
            return Double.parseDouble(attValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public double readDouble(String type, String attName, double defaultValue) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return defaultValue;
        }
        
        try {
            return Double.parseDouble(attValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public Boolean readBoolean(String type, String attName) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return false;
        }
        
        return attValue.equalsIgnoreCase("true") || attValue.equals("1");
    }
    
    public boolean readBoolean(String type, String attName, boolean defaultValue) {
        String attValue = readString(type, attName);
        if (attValue == null) {
            return defaultValue;
        }
        
        return attValue.equalsIgnoreCase("true") || attValue.equals("1");
    }
    
    public void writeString(String type, String attName, String attValue) {
        String saveType = null;
        for (String dType : datas.keySet()) {
            if (dType.equalsIgnoreCase(type)) {
                saveType = dType;
                for (String dAttName : datas.get(dType).keySet()) {
                    if (dAttName.equalsIgnoreCase(attName)) {
                        datas.get(dType).put(dAttName, attValue);
                        return;
                    }
                }
            }
        }
        
        Map<String, String> typeAtts = null;
        if (saveType == null) {
            typeAtts = new HashMap<String, String>();
            datas.put(type, typeAtts);
        } else {
            typeAtts = datas.get(saveType);
        }
        
        typeAtts.put(attName, attValue);
    }
    
    public void writeInt(String type, String attName, int attValue) {
        writeString(type, attName, String.valueOf(attValue));
    }
    
    public void writeLong(String type, String attName, long attValue) {
        writeString(type, attName, String.valueOf(attValue));
    }
    
    public void writeFloat(String type, String attName, float attValue) {
        writeString(type, attName, String.valueOf(attValue));
    }
    
    public void writeDouble(String type, String attName, double attValue) {
        writeString(type, attName, String.valueOf(attValue));
    }
    
    public void writeBoolean(String type, String attName, boolean attValue) {
        writeString(type, attName, String.valueOf(attValue));
    }
    
    public void delete(String type, String attName) {
        datas.get(type).remove(attName);
    }
    
    public void deletes(String type, String attName) {
        List<String> attNames = new ArrayList<String>(datas.get(type).keySet());
        for (int i = attNames.size() - 1; i >= 0; i--) {
            if (attNames.get(i).startsWith(attName)) {
                delete(type, attNames.get(i));
            }
        }
    }
    
    public boolean contains(String type) {
        return datas.containsKey(type);
    }
    
    public boolean contains(String type, String attName) {
        return datas.containsKey(type) && datas.get(type).containsKey(attName);
    }
    
    public boolean save() {
        return saveAs(iniFile);
    }
    
    /**
     * 另存为

     * @param asFile
     * @return
     */
    public boolean saveAs(String asFile) {
        File file = new File(asFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            	Log.printStackTrace(e);
                return false;
            }
        }
        
        PrintWriter ini = null;
        try {
//            ini = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
//                    file), encoding));
            ini = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
                    file)));
        } catch (Exception e) {
        	Log.printStackTrace(e);
            return false;
        }
        
        int count = datas.size();
        int index = 1;
        Map<String, String> typeAtts = null;
        
        // 按类型和属性名排序
        Comparator<String> sortComparator = new Comparator<String>() {
            
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        };
        List<String> types = new ArrayList<String>(datas.keySet());
        Collections.sort(types, sortComparator);
        List<String> attNames = null;
        String attValue = null;
        for (String type : types) {
            ini.println("[" + type + "]");
            typeAtts = datas.get(type);
            attNames = new ArrayList<String>(typeAtts.keySet());
            Collections.sort(attNames, sortComparator);
            String countValue = "";//count最后写
            String count1Value = "";//count最后写
            for (String attName : attNames) {
                attValue = typeAtts.get(attName);
            	if(attName.equalsIgnoreCase("Count")){
            		countValue = attValue;
            		continue;
            	}
            	if(attName.equalsIgnoreCase("Count1")){
            		count1Value = attValue;
            		continue;
            	}
                ini.println(attName + "=" + (attValue != null ? attValue : ""));
            }
            //ini.flush();
            if(!"".equals(countValue)){
            	 ini.println("Count=" + countValue);
            }
            if(!"".equals(count1Value)){
           	 ini.println("Count1=" + count1Value);
           }
            if (index < count) {
                ini.println();
            }
            index++;
        }
        ini.flush();
        ini.close();
        //datas.clear();//写完清空数组
        return openBak ? copy(asFile, asFile + ".bak") : true;
    }
    
    /**
     * 复制备份
     * @param fromFile
     * @param toFile
     * @return
     */
    public boolean copy(String fromFile, String toFile) {
        // 验证源文件是否正确，错误则不进行复制操作
        File from = new File(fromFile);
        if (!from.exists()) {
            return false;
        }
        if (from.length() == 0) {
            return false;
        }
        
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(from);
            byte[] buffer = new byte[2048];
            int n = 0;
            String line = null;
            while (-1 != (n = input.read(buffer))) {
                if (line == null) { // 验证文件第一行是否有内容，如果没有就认为是错误的源文件，不进行复制操作
                
                    line = new String(buffer);
                    if (line.trim().length() == 0) {
                        return false;
                    }
                }
                if (output == null) {
                    output = new FileOutputStream(toFile);
                }
                output.write(buffer, 0, n);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
        }
        
        return true;
    }
    
    public boolean isOpenBak() {
        return openBak;
    }
    
    public void setOpenBak(boolean openBak) {
        this.openBak = openBak;
    }
    
    public String getEncoding() {
        return encoding;
    }
    
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

	public Map<String, Map<String, String>> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Map<String, String>> datas) {
		this.datas = datas;
	}
    
}
