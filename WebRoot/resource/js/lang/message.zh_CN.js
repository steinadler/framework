var Message = {
	success : "\u64cd\u4f5c\u6210\u529f",
	fail : "\u64cd\u4f5c\u5931\u8d25",
	
	error_param : "\u53c2\u6570\u9519\u8bef",
	error_oldpwd : "\u65e7\u5bc6\u7801\u9519\u8bef",
	error_file_exists : "\u6587\u4ef6\u5df2\u5b58\u5728", 
	error_file_not_exists : "\u6587\u4ef6\u4e0d\u5b58\u5728",
	error_meeting_already_begin : "\u6b63\u5728\u4e0a\u8bfe\uff0c\u4e0d\u53ef\u91cd\u590d\u64cd\u4f5c",
	error_meeting_already_close : "\u5df2\u7ecf\u4e0b\u8bfe\uff0c\u4e0d\u53ef\u91cd\u590d\u64cd\u4f5c",
	error_meeting_other_close : "\u5df2\u7ecf\u4e0b\u8bfe\uff0c\u8bf7\u5237\u65b0\u9875\u9762",
	error_meeting_connecte_error : "\u7cfb\u7edf\u8fde\u63a5\u5f02\u5e38",
	error_meeting_is_begin_not_save : "\u5df2\u7ecf\u4e0a\u8bfe\uff0c\u4e0d\u80fd\u4fdd\u5b58",
	error_record_already_begin : "\u5df2\u7ecf\u6709\u8bfe\u4ef6\u6b63\u5728\u5f55\u5236\u4e2d\uff0c\u8bf7\u5148\u505c\u6b62\u5f55\u5236",
	error_record_already_close : "\u5f55\u5236\u5df2\u7ecf\u505c\u6b62\uff0c\u4e0d\u53ef\u91cd\u590d\u64cd\u4f5c",
	error_disk_lack : "\u78c1\u76d8\u5269\u4f59\u7a7a\u95f4\u4e0d\u8db3",
	error_datasource_already_max : "\u6700\u591a\u53ea\u80fd\u6709{0}\u4e2a\u6570\u636e\u6e90",
	error_datasource_already_exists : "\u8be5\u6570\u636e\u6e90\u5df2\u7ecf\u5b58\u5728",
	error_login_nullInput : "\u7528\u6237\u540d\u548c\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a",
	error_login_user_not_exists : "\u7528\u6237\u4e0d\u5b58\u5728",
	error_login_password_wrong : "\u5bc6\u7801\u9519\u8bef",
	error_access_deny : "\u6ca1\u6709\u6743\u9650",
	error_system_updating : "\u7cfb\u7edf\u6b63\u5728\u5347\u7ea7\u4e2d\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5",
	error_update_no_file : "\u65e0\u6cd5\u83b7\u53d6\u5347\u7ea7\u6587\u4ef6\uff0c\u5347\u7ea7\u6587\u4ef6\u6269\u5c55\u540d\u5fc5\u987b\u662ftar.gz",
	error_update_error_file_large : "\u5347\u7ea7\u6587\u4ef6\u5927\u5c0f\u8d85\u8fc7\u9650\u5236\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458",
	error_setting_dbdoing : "\u6570\u636e\u5e93\u6b63\u5219\u5907\u4efd\u6216\u8005\u6062\u590d\uff0c\u8bf7\u7a0d\u540e\u64cd\u4f5c",
	error_close_all_room : "\u7ed3\u675f\u8bfe\u7a0b\u7684\u65f6\u5019\u9047\u5230\u4e86\u9519\u8bef",
	
	warn_del_confirm : "\u786e\u5b9a\u8981\u5220\u9664\u5417", 
	warn_db_backup_confirm : "\u786e\u5b9a\u8981\u5907\u4efd\u6570\u636e\u5e93\u5417",
	warn_db_restore_confirm : "\u5f53\u524d\u6570\u636e\u5e93\u5c06\u88ab\u8986\u76d6\uff0c\u8bf7\u5148\u5907\u4efd\uff0c\u786e\u5b9a\u8981\u7ee7\u7eed\u8fd8\u539f\u5417",
	warn_datasource_already_max : "\u6700\u591a\u53ea\u80fd\u6709{0}\u4e2a\u6570\u636e\u6e90",
	warn_class_is_open : "\u6b63\u5728\u4e0a\u8bfe\uff0c\u8bf7\u7a0d\u540e\u518d\u64cd\u4f5c",
	warn_class_is_open_update_confirm : "\u6b63\u5728\u4e0a\u8bfe\uff0c\u786e\u5b9a\u8981\u5f3a\u5236\u5347\u7ea7\u5417",
	warn_reboot : "\u786e\u5b9a\u8981\u91cd\u542f\u670d\u52a1\u5668\u5417",
	
	notice_must_select_one : "\u8bf7\u9009\u62e9\u4e00\u6761\u8bb0\u5f55",
	notice_only_one_select : "\u53ea\u80fd\u9009\u62e9\u4e00\u6761\u8bb0\u5f55",
	notice_only_support_ie : "\u8bf7\u5728\u9875\u9762\u53f3\u4e0a\u89d2\u4e0b\u8f7d\u5b89\u88c5\u64ad\u653e\u63d2\u4ef6\uff0c\u5e76\u7528IE\u6d4f\u89c8\u5668\u89c2\u770b",
	notice_file_recording : "\u6587\u4ef6\u6b63\u5728\u5f55\u5236\u4e2d",
	notice_datasource_edit : "\u6570\u636e\u6e90\u5904\u4e8e\u7f16\u8f91\u72b6\u6001",
	notice_last_version : "\u5f53\u524d\u7248\u672c\u5df2\u7ecf\u662f\u6700\u65b0\u7248\u672c",
	notice_meeting_is_open : "\u5f53\u524d\u6b63\u5728\u5f00\u4f1a\uff0c\u65e0\u6cd5\u5347\u7ea7",
	notice_update_success : "\u5347\u7ea7\u6210\u529f\uff0c\u8bf7\u91cd\u542f\u670d\u52a1\u5668",
	notice_update_fail : "\u5347\u7ea7\u5931\u8d25\uff0c\u8bf7\u5237\u65b0\u9875\u9762",	
	notice_download_player : "\u8bf7\u5728\u9875\u9762\u53f3\u4e0a\u89d2\u4e0b\u8f7d\u5b89\u88c5\u64ad\u653e\u63d2\u4ef6\uff0c\u7136\u540e\u91cd\u542f\u6d4f\u89c8\u5668",
	notice_over_max_play : "\u8d85\u8fc7\u4e86\u6700\u5927\u76f4\u64ad\u70b9\u64ad\u4eba\u6570\u9650\u5236",
	notice_reboot_success : "\u6b63\u5728\u91cd\u542f\u670d\u52a1\u5668\uff0c\u8bf7\u7a0d\u540e\u518d\u8bbf\u95ee",
	
	play_page_prev : "\u4e0a\u4e00\u9875",
	play_page_next : "\u4e0b\u4e00\u9875",
	
	text_confirm : "\u786e\u8ba4",
	text_cancel : "\u53d6\u6d88",
	text_edit : "\u7f16\u8f91",
	text_del : "\u5220\u9664",
	text_restore : "\u8fd8\u539f",
	text_addr : "\u5730\u5740",
	text_port : "\u7aef\u53e3",
	text_username : "\u7528\u6237\u540d",
	text_password : "\u5bc6\u7801",
	text_disconnected : "\u672a\u8fde\u63a5",
	text_uploadprossor : "\u4e0a\u4f20\u4e2d",
	text_downloadprossor : "\u4e0b\u8f7d\u4e2d",
	text_updateprossor : "\u5347\u7ea7\u4e2d",
	text_waitprossor : "\u7b49\u5f85",
	
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