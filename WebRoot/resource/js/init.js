/**
 * load js file with the filename specified and call callback function if present.
 * @param obj
 * obj = {};
 * obj.urlprefix: specified the path for the js file.
 * obj.filename: the filename  (example:  obj.filename = "/search.js?rev={%SEARCH_JS%}";)
 * obj.fn: callback function. (example:  obj.fn = function(jsfiles) {alert("hello" + jsfiles)}; )
 */

jQuery(function($) {
    $(".searchbar .auto").change(function(e){
        $(".searchbar form").submit();
    });
    
    $(".close").click(function(e){
        $(".dialog").hide();
    });

    $(".delconfirm").click(function(e){
        return confirm("确定删除么？");
    });
    
    $("form").submit(function(e){
        var isPass = true;
        
        //空白检测
        textboxs = $(this).find(".notnull");
        for(var i=0;i<textboxs.size();i++){
            if(textboxs.eq(i).val().trim() == ""){
                isPass = false;
                break;
            }
        }        
        
        //日期类型检测
        var textboxs = $(this).find(".form_date");
        for(var i=0;i<textboxs.size();i++){
            //var reg_date = /^[2]\d{3}\-[01]{0,1}\d\-[0123]{0,1}\d$/ig;//强检测
            var reg_date = /^\d{4}\-\d{1,2}\-\d{1,2}$/ig;//弱检测
            var val = textboxs.eq(i).val().trim();
            if(!val == "" && !reg_date.test(val)){
                isPass = false;
                break;
            }
        }
        
        //正整数检测        
        textboxs = $(this).find(".form_int");
        for(var i=0;i<textboxs.size();i++){
            var reg_int = /^\d*$/ig;
            var val = textboxs.eq(i).val().trim();
            if(!val == "" && !reg_int.test(val)){
                isPass = false;
                break;
            }
        }            
        
        if(!isPass){
            alert("请仔细检查数据格式!");
        }
        return isPass;
    });
});

function IndexClass() {
};

IndexClass.prototype = {
    index:function() {
		var search_def_text = '输入公司名称搜索';
        $(".searchbar .keywords").focus(function(e){
        	if ($(this).val() == search_def_text) {
        		$(this).val('');
        		$(this).css({'color' : '#000'});
        	}
        });
        
        $(".searchbar .keywords").blur(function(e){
        	if ($(this).val() == '') {
        		$(this).val(search_def_text);
        		$(this).css({'color' : '#999'});
        	}
        });
        
        $(".salaryRank li").mouseover(function(e){
        	$(this).siblings("li").removeClass("active");
        	$(this).addClass("active");
        });        
    },
    
    userlist:function() {
        $(".infolist .advance").click(function(e){
            var tr = $(this).parent().parent();            
            $(".dialog input[name=sid]").val(tr.children().eq(0).text());
            $(".dialog #uname").html(tr.children().eq(1).text());
            $(".dialog input[name=jdate]").val(tr.children().eq(6).text());
            $(".dialog input[name=rdate]").val(tr.children().eq(7).text());
            $(".dialog").show();
        });
        
        $(".dialog form").submit(function(e){
            if(false){
                return false;
            }
            return true;
        });
    }
};

function SearchClass() {
    $(".houses li").hover(function() {
        $(".houses li").removeClass("hover");
        $(this).addClass("hover");
    }, function() {
        $(".houses li").removeClass("hover");
    });
    $(".bykeywords a.sel").click(function() {
        return false;
    });
    subSearch();
};
SearchClass.prototype = {
    loadestate:function() {
        loadjs({filename:"/estate.js?rev={%ESTATE_JS%}"})
    },
    resale:function() {
        this.loadestate();
        loadjs({filename:"/search.js?rev={%SEARCH_JS%}"});
        $(".nav li").removeClass("up");
        $("#n_resale").addClass("up");
        $("#name").focus();
        verifyinsearchpage();
        pathnav();
    }
};