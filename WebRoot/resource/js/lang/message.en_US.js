var Message = {
	success : "success",
	fail : "fail",
	
	error_param : "param error",
	error_oldpwd : "old password wrong",
	error_file_exists : "file exists", 
	error_file_not_exists : "file not exists",
	error_meeting_already_begin : "lesson is begin",
	error_meeting_already_close : "lesson is end",
	error_meeting_other_close : "lesson is end, fresh the page",
	error_meeting_connecte_error : "connect error",
	error_meeting_is_begin_not_save : "lession is begin, coun't save",
	error_record_already_begin : "lesson is recording",
	error_record_already_close : "lesson is end",
	error_disk_lack : "disk lack",
	error_datasource_already_max : "max have {0} datasources",
	error_datasource_already_exists : "datasource exists",
	error_login_nullInput : "username and password coun't be empty",
	error_login_user_not_exists : "user not exists",
	error_login_password_wrong : "password wrong",
	error_access_deny : "auth deny",
	error_system_updating : "system is updateing",
	error_update_no_file : "update file mast end with tar.gz",
	error_update_error_file_large : "file is too large",
	error_setting_dbdoing : "db is backup or restore, try again later",
	error_close_all_room : "close lesson case error",
	
	warn_del_confirm : "sure?", 
	warn_db_backup_confirm : "sure?",
	warn_db_restore_confirm : "current database will be cover, sure?",
	warn_datasource_already_max : "max have {0} datasources",
	warn_class_is_open : "lesson is starting, please do later",
	warn_class_is_open_update_confirm : "lesson is starting,  still update?",
	warn_reboot : "server will reboot, sure?",
	
	notice_must_select_one : "select one less",
	notice_only_one_select : "only select one you can",
	notice_only_support_ie : "download the play plugin and use ie brownser",
	notice_file_recording : "file is recording",
	notice_datasource_edit : "database is editing",
	notice_last_version : "version is last",
	notice_meeting_is_open : "lesson is begin, coun't update",
	notice_update_success : "update success, please reboot server",	
	notice_update_fail : "update fail, please refresh page",	
	notice_download_player : "download the play plugin and restart ie brownser",
	notice_over_max_play : "over vod or live limit",
	notice_reboot_success : "system is rebooting, please fresh page later",
	
	play_page_prev : "prev",
	play_page_next : "next",
	
	text_confirm : "yes",
	text_cancel : "cancel",
	text_edit : "edit",
	text_del : "del",
	text_restore : "restore",
	text_addr : "addr",
	text_port : "port",
	text_username : "uname",
	text_password : "pwd",
	text_disconnected : "disconnected",
	text_uploadprossor : "uploading",
	text_downloadprossor : "downloading",
	text_updateprossor : "updateing",
	text_waitprossor : "waiting",
	
	dynamic : function(key, value) {
		if (key == "") {
			return "";
		}
		var msg = Message[key];
		if (!msg) {
			return key;
		}
		if (msg.indexOf("{") != -1) {
			if (typeof(value) == "object") {
				for (var i = 0; i < value.length; i++) {
					if (typeof(value) == "object" || typeof(value) == "string") {
						value[i] = value[i].toString();
						if (value[i].charAt(0) == '#') {
							value[i] = Message[value[i].substring(1)];
						}
						if (!value[i]) value[i] = "";
					}
					msg = msg.replace("{" + i + "}", value[i]);
				}
			} else if (typeof(value) == "string") {
				if (value.charAt(0) == '#') {
					value = Message[value.substring(1)];
				}
				if (!value) value = "";
				msg = msg.replace("{0}", value);
			} else {
				msg = msg.replace("{0}", value);
			}
		}
		return msg;
	},
	
	dateString : function(dateString) {
		var year, month, day, week;
		if (dateString) {
			var dateStrings = dateString.split("-");
			year = dateStrings[0];
			month = dateStrings[1];
			day = dateStrings[2];
			week = dateStrings[3];
		} else {
			var date = new Date();
			year = date.getFullYear();
			month = date.getMonth()+1;
			day = date.getDate();
			week = date.getDay();
		}
		
		return Message.dynamic("date_string", [year, month, day, Message.week[week]]);
	}
};