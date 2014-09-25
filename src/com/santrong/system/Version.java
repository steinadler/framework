package com.santrong.system;


public class Version {
    
    /*
     * [System]
     */
    public static String WebVersion = "1.0.0";    						// web版本
    public static String NetEditorVersion = "1.0.0";    				// NetEditorServer版本
    public static String LiveNodeVersion = "1.0.0";    					// LiveNode版本
    
    static {
        String configFile = Version.class.getClassLoader().getResource("") + "Version.ini";
        if (configFile.startsWith("file:/")) {
            configFile = configFile.substring(5);
        }
        
        Ini ini = new Ini();
        if (ini.read(configFile)) {
            WebVersion = ini.readString("System", "WebVersion", WebVersion);
            NetEditorVersion = ini.readString("System", "NetEditorVersion", NetEditorVersion);
            LiveNodeVersion = ini.readString("System", "LiveNodeVersion", LiveNodeVersion);
        }
    }
	
}
