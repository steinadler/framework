package com.santrong.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.santrong.i18n.Message;
import com.santrong.log.Log;
import com.santrong.util.CommonTools;

public class Global {
	
	/*
	 * static final config
	 */
	public static final String iniFileEncoding = "UTF-8";
	
	
	/*
	 *[Global] 
	 */
	public static int ProcessRepeatLogin = 0;   						// 0: 不处理重复登录，1: 处理重复登录
	public static int RepeatLoginProcess = 0;   						// 0: 不做处理，1: 提示用户是否强制登录，2: 直接强制登录，3：提示已登录
	
	
    /*
     * [RecFile]
     */
	public static String RecData= "/RecData";							// 挂载硬盘的目录，用作主存储课件区
    public static String LocalFileSavePath = "/Rec";					// 媒体中心播放文件存储地址
    public static String NetFileSavePath = "/RecN"; 					// 连入媒体中心网存文件存储地址
    public static String VodClusterConfFile="/usr/local/openresty/nginx/conf/cluster.conf";		// nginx负载均衡的配置文件
    
    public static String RemoteFileUrl = "/D/Rec/";         			// 远程录播服务器url
    public static String RecFilePlayer = "/resource/content.htm";		// 点播课件是url的后缀
    public static String RecFilePlayerPad = "/resource/content_pad.htm";	// 点播课件是url的后缀（平板电脑）
    /*
     * [System]
     */
    public static boolean isBeyonsys = true;    						// 是否beyonsys版本
    public static boolean isDirector = false; 							// 是否支持电影模式
    public static boolean VodRemoteFile = true;							// 是否支持点播远程课件
    public static boolean DelRemoteFile = true;							// 在课件上传成功后配置是否删除远程课件
    public static boolean OpenVodLiveAuth = false;						// 是否开启所有的点播和直播的权限（总开关开启针对第三方平台的无权限限制访问）
    public static int LessonCTFormat = 0;								// 课表导入模板中课时的格式，0表示填写的是课时x-x，1表示填写的是起止时间xx:xx-xx:xx
    public static String KeyLicencePath = "/etc/reachLic";				// licence相关信息文件的目录
    public static String ClientUploadFile = "/uploadPath";				// 上传的ftp目录，这里是Ftp根目录下的目录
    
    public static boolean allLogin = false;								// 是否所有的页面都需要登录
    
    public static int WanIndex = 1;										// wan网口的索引
    public static int LanIndex = 0;										// lan网口的索引
    public static String LanDeviceName = "";							// lan网口的网口名
    public static String WanDeviceName = "";							// wan网口的网口名
    
    public static int RecServerPort = 3000;								// 媒体中心跟录播通信端口
    public static int LiveNodePort = 3008;								// 媒体中心跟直播节点通信端口
    public static int DCMPPort = 5000;									// 媒体中心跟管理平台通信端口
    public static int NatFtpPort = 21;									// 媒体中心上的FTP开发端口
    public static String DBDir_Name="/opt/MysqlBackup";					// 数据库备份目录
    public static boolean SupportNat = false;                          	// 直播、点播使用默认内网IP
    public static int UserLockTime = 24;                  				// 用户锁定时间（单位：小时）
    public static int DownloadMaxCount = 10;                            // 控制前台下载人数
    public static String PublishBBMCIp = "";							// 发布BlackBoard时给出的媒体中心的IP地址
    public static boolean EnableOcr = true;								// 是否启用OCR，默认关闭
    public static boolean EnableDot = true;								// 是否启用Dot(打点)，默认启用
    public static boolean OpenDebug = true;   							// 是否开启系统测试模块
    public static boolean OpenDirector = false;							// 是否开启导播台
    public static boolean OpenMovieLive = false;						// 是否开启电影模式直播
    
    public static int RSSFileDays = 60;                             	// RSS发布多少天之内的课件
    public static int RSSFileCount = 50;                            	// RSS发布的最多课件个数
    
    public static boolean XmlRpcOpen = true;							//XMLRPC接口是否验证权限
    
    
    /*
     * [VodHeart]
     */
    public static int VodHeartInterval = 5000;						// 点播心跳时间
    public static int VodHeartTimeout = 15000;						// 点播心跳过时时间
    
    public static boolean ENABLE_ZONE = true;                       //是否开启个人空间    
    
    
    
    /*
     * [Encoder]
     */
    
    static {
        String configFile = Global.class.getClassLoader().getResource("") + "Global.ini";
        if (configFile.startsWith("file:/")) {
            configFile = configFile.substring(5);
        }
        
        Ini ini = new Ini();
        if (ini.read(configFile)) {
            // 重复登录处理
            ProcessRepeatLogin = ini.readInt("Global", "ProcessRepeatLogin", ProcessRepeatLogin);
            if(ProcessRepeatLogin == 0) {
                RepeatLoginProcess = 0;
            } else {
                RepeatLoginProcess = ini.readInt("Global", "RepeatLoginProcess", RepeatLoginProcess);
                if (RepeatLoginProcess == 0) {
                    ProcessRepeatLogin = 0;
                }
            }
            
            RecData = ini.readString("RecFile", "FileParentPath", RecData);
            LocalFileSavePath = ini.readString("RecFile", "LocalFileSavePath", LocalFileSavePath);
            NetFileSavePath = ini.readString("RecFile", "NetFileSavePath", NetFileSavePath);
            VodClusterConfFile = ini.readString("RecFile", "VodClusterConfFile", VodClusterConfFile);
            RemoteFileUrl = ini.readString("RecFile", "RemoteFileUrl", RemoteFileUrl);
            RecFilePlayer = ini.readString("RecFile", "RecFilePlayer", RecFilePlayer);
            RecFilePlayerPad = ini.readString("RecFile", "RecFilePlayerPad", RecFilePlayerPad);
            
            isBeyonsys = ini.readBoolean("System", "isBeyonsys", isBeyonsys);
            isDirector = ini.readBoolean("System", "isDirector", isDirector);
            DelRemoteFile = ini.readBoolean("System", "DelRemoteFile", DelRemoteFile);
            VodRemoteFile = ini.readBoolean("System", "VodRemoteFile", VodRemoteFile);
            OpenVodLiveAuth = ini.readBoolean("System", "OpenVodLiveAuth", OpenVodLiveAuth);
            LessonCTFormat = ini.readInt("System", "LessonCTFormat", LessonCTFormat);
            KeyLicencePath = ini.readString("System", "KeyLicencePath", KeyLicencePath);
            ClientUploadFile = ini.readString("System", "ClientUploadFile", ClientUploadFile);
            
            WanIndex = ini.readInt("System", "WanIndex", WanIndex);
            LanIndex = ini.readInt("System", "LanIndex", LanIndex);
            WanDeviceName = ini.readString("System", "WanDeviceName", WanDeviceName);
            LanDeviceName = ini.readString("System", "LanDeviceName", LanDeviceName);
            
            RecServerPort = ini.readInt("System", "RecServerPort", RecServerPort);
            LiveNodePort = ini.readInt("System", "LiveNodePort", LiveNodePort);
            DCMPPort = ini.readInt("System", "DCMPPort", DCMPPort);
            NatFtpPort = ini.readInt("System", "NatFtpPort", NatFtpPort);
            DBDir_Name = ini.readString("System", "DBDir_Name",DBDir_Name);
            UserLockTime = ini.readInt("System", "UserLockTime", UserLockTime);
            SupportNat = ini.readBoolean("System", "SupportNat", SupportNat);
            DownloadMaxCount =ini.readInt("System", "DownloadMaxCount", DownloadMaxCount);
            PublishBBMCIp = ini.readString("System", "PublishBBMCIp", PublishBBMCIp);
            EnableOcr = ini.readBoolean("System", "EnableOcr", EnableOcr);
            EnableDot = ini.readBoolean("System", "EnableDot", EnableDot);
            ENABLE_ZONE = ini.readBoolean("System", "EnableZone", ENABLE_ZONE);
            OpenDebug = ini.readBoolean("System", "OpenDebug", OpenDebug);
            OpenDirector = ini.readBoolean("System", "OpenDirector", OpenDirector);
            
            XmlRpcOpen = ini.readBoolean("System", "XmlRpcOpen", XmlRpcOpen);
            
            VodHeartInterval = ini.readInt("VodHeart", "VodHeartInterval", VodHeartInterval);
            VodHeartTimeout = ini.readInt("VodHeart", "VodHeartTimeout", VodHeartTimeout);           
            
            RSSFileDays = ini.readInt("System", "RSSFileDays", RSSFileDays);
            RSSFileCount = ini.readInt("System", "RSSFileCount", RSSFileCount);
            
            
        }
    }

	
	/**
	 * 获取系统升级更新的目录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getUpdatePath(HttpServletRequest request) {
		return request.getRealPath("/updatePath");
	}
	
	/***
	 * 获取系统资源上传的目录，包括日志文件的生成
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getUploadPath(HttpServletRequest request) {
		return request.getRealPath("/uploadPath");
	}
	
	/**
	 * 获取系统资源下载的目录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getDownPath(HttpServletRequest request) {
		return request.getRealPath("/downPath");
	}
	
	/**
	 * 获取系统logo存放路径
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getLogoPath(HttpServletRequest request) {
		return request.getRealPath("/images");
	}
    
	/**
	 * 获取语言lan下的系统title
	 * @param lang
	 * @return
	 */
	public static String getSystemTitle(String lang) {
		SystemConfig sc = new SystemConfig();
		sc.getSysConfig();
		String title = sc.getSystemTitle();
		if (!CommonTools.isNotNull(title)) {
			try {
				// 国际化
				Message message = Message.getInstance();
				message.setI18nFile("system", lang);
				title = message.getString("project_title");
			} catch (Exception e) {
				Log.printStackTrace(e);
				title = "";
			}
		}
		
		return title;
	}
	
	/**
	 * 获取session下系统的title
	 * @param session
	 * @return
	 */
	public static String getSystemTitle(HttpSession session) {
		return getSystemTitle((String) session.getAttribute("lang"));
	}
	
}
