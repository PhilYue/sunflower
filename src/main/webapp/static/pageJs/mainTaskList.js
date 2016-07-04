/**
 * @tag 参考网站
 * http://labs.creative-area.net/jquery.datagrid/
 * http://v3.bootcss.com/components/#thumbnails-custom-content
 */

//代办详细列表
var countMap = {
		CS_REMINDER : "csReminderCount",//客服催单
		WAIT_FOR_CS_CONFIRM : "waitConfirmCount",//等待客服确认
		WAIT_FOR_SUPPLIER_REPLY : "waitSupplierCount",//等待供应商回复
		API_BOOKING_FAIL : "apiFailCount",//api预定失败
		EMAIL_SEND_FAIL : "emailFailCount",//邮件发送失败
		REFUND_FOR_CS_CONFIRM : "refundExamineCount",//退款申请审核
		WAIT_FOR_MERCHANT_CONFIRM : "waitMerchantCount"//商户商品待处理
};
//定时器
var showTime = 5000,tryJobCount = 0;
var getBacklogJob;
//计数器临时变量
var $type_span,new_count,all_count=0;
//A标签临时变量
var a_element,a_event;

$(function() {
	/** 第一次执行查询计数信息 */
	getBacklogCount();
	/** 定时待办事项数量查询 */
	getBacklogJob = setInterval(getBacklogCount, showTime); 
	/** 侧边栏查询 */
	initBootSideMenu();
	/** 初始化表格 */
	initGrid();
	/** 查询 */
	initSerch();
});
/**
 * 初始化表格
 */
function initGrid(){
	var user_name = $(".user-name").attr("user-name");
	var taskGrid = $( "#taskGrid" ).datagrid({
		url: "/cs/queryOrderPage.do",
		data: false,
	    autoload: true,
	    paramsMapping: {
	        page: "page",
	        paging: "rows",
	    },
		parse: function( data ) {
	        if ( $.type( data ) === 'string' ) {
	            return JSON.parse( data );
	        } else {
	            return data;
	        }
	    },
	    remoteSort: true,
	    idField:'sysTradeNo',
	    rownumbers:true,
		col: [{
			field: "sysTradeNo",
			title: "主订单号</br>子订单号",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			attr: { "style": "width:200px"},
			render: function( data ) {
                return "<a target='_blank' style='text-decoration: none;' href='/eventOrderDetail/"+data.value+".html'><span class='glyphicon glyphicon-th-large' aria-hidden='true'></span> ：" + data.value + "</a></br><span class='glyphicon glyphicon-th' aria-hidden='true'></span> ："+data.row.sysTradeNoChild+"";
            }
		},{
			field: "ownerName",
			title: "联系人信息",
			attrHeader: { 
	            "width": "20%", 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			render: function( data ) {
                return "<span class='glyphicon glyphicon-user' aria-hidden='true'></span> ：" + data.value + "</br><span class='glyphicon glyphicon-earphone' aria-hidden='true'></span> ："+data.row.ownerPhone+"</br><span class='glyphicon glyphicon-envelope' aria-hidden='true'></span> ："+data.row.ownerEmail+"";
            }
		},{
			field: "payDate",
			title: "支付时间",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			render: function( data ){
				if(null != data.value){
					return new Date(parseInt(data.value)).Format("yyyy-MM-dd hh:mm:ss");
				}else{
					return "<span class='label label-default'>未支付</span>";
				}
			}
		},{
			field: "tourName",
			title: "产品名称",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        }
		},{
			field: "totalAmt",
			title: "订单金额</br>实付金额",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			attr: { "style": "width:100px"},
			render: function( data ){
				var currency = data.row.currency;
				if ("CNY" == currency){
					currency = "￥";
				}else if ("HKD" == currency){
					currency = "HK$";
				}else if ("TWD" == currency){
					currency = "NT$";
				}else if ("SGD" == currency){
					currency = "S$";
				}else if ("EUR" == currency){
					currency = "€";
				}else if ("USD" == currency){
					currency = "$";
				}else if ("GBP" == currency){
					currency = "￡";
				}else {
					currency =  "￥";
				}
				var payCurrency = data.row.payCurrency;
				if ("CNY" == payCurrency){
					payCurrency = "￥";
				}else if ("HKD" == payCurrency){
					payCurrency = "HK$";
				}else if ("TWD" == payCurrency){
					payCurrency = "NT$";
				}else if ("SGD" == payCurrency){
					payCurrency = "S$";
				}else if ("EUR" == payCurrency){
					payCurrency = "€";
				}else if ("USD" == payCurrency){
					payCurrency = "$";
				}else if ("GBP" == payCurrency){
					payCurrency = "￡";
				}else {
					payCurrency =  "￥";
				}
                return currency+" "+ data.value + "</br>"+payCurrency+" "+data.row.payAmount;
			}
		},{
			field: "merchantStatus",
			title: "商户状态",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			render: function(data){
				var merchantStatusStr = "未知";
				switch (data.value) {
				case '-1':
					merchantStatusStr = "非商户产品";
					break;
				case '0':
					merchantStatusStr = "商户未确认";
					break;
				case '1':
					merchantStatusStr = "商户已确认";
					break;
				case '2':
					merchantStatusStr = "商户已拒绝";
					break;
				case '4':
					merchantStatusStr = "商户产品";
					break;
				default:
					break;
				}
				return merchantStatusStr;
			}
		},{
			field: "opStatus",
			title: "订单状态",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			render: function( data ){
				var opStatus = data.value;
				var payStatus = data.row.payStatus;
				var taskType = data.row.taskType;
				var html = "";
				switch (taskType) {
				case 1:
					html = "<span class='label label-default'>未支付</span>";
					break;
				case 2:
					html = "待确认";
					break;
				case 3:
					html = "待确认";
					break;
				case 4:
					html = "API预定失败";
					break;
				case 5:
					html = "邮件发送失败";
					break;
				case 6:
					html = "待退款";
					break;
				case 7:
					html = "商户待处理";
					break;
				default:
					html = "已完结";
					break;
				}
				return html;
			}
		},{
			field: "operation_",
			title: "操作",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			render: function( data ){
				var btn = "";
				var rows = data.row;
				btn += " "
				if(null!=rows.csEmail&&""!=rows.csEmail){
					btn += "<span class='label label-default' style='cursor:not-allowed' " 
				}else{
					btn += "<span class='label label-primary' style='cursor:pointer' " 
				}
				btn += "tag-belong='"+rows.csEmail+"' tag-handle='' tag-type='"+rows.taskType+"' tag-a-data='"+rows.sysTradeNo+"' tag-data='"+rows.id+"' onclick='taskHandle( this );'>"+(null!=rows.csEmail?(rows.csEmail.substring(0,rows.csEmail.lastIndexOf('@'))):"做单")+"</span>";
				if( 1 === data.row.taskType ){
					btn += "</br></br><span class='label label-warning' style='cursor:pointer' tag-handle='IGNORE' tag-type='"+rows.taskType+"' tag-a-data='"+rows.sysTradeNo+"' tag-data='"+rows.id+"' onclick='taskHandle( this );'>忽略</span>";
				}
				if( 2 === data.row.taskType ){
					btn += "</br></br><span class='label label-info' style='cursor:pointer' tag-handle='WAIT_FOR_SUPPLIER_REPLY' tag-type='"+rows.taskType+"' tag-a-data='"+rows.sysTradeNo+"' tag-data='"+rows.id+"' onclick='taskHandle( this );'>待供应商确认</span>";
				}
				return btn;
			},
		}],
		onRowData: function( data, num, $tr ) {
	        if ( data.csEmail === user_name ) {
	            $tr.addClass( "success" );
	        } 
	    },
		attr: { "class": "table table-bordered table-condensed" },
		sorter: "bootstrap",
		pager: "bootstrap",
		paramsDefault: {
	    	sysTradeNo : "",
	    	taskType : ""
	    },
	});
}

/**
 * 侧边栏
 */
function initBootSideMenu(){
	var $sideMenu = $('#sideMenu');
	var $modalBody = $("#modalBody");
	$sideMenu.BootSideMenu({side:"left", autoClose:false});
	$modalBody.animate({ 'padding-left' : $sideMenu.width() });
	$sideMenu.find('div .list-group').find('a').click(function(event){
		var $this = $(this);
		var $taskGrid;
	$type_span = $this.find('span');
		try {
			$taskGrid = $( "#taskGrid" ).datagrid( "datagrid" );
			$taskGrid._params.page = 1;//每次都初始化第一页
			$taskGrid._params.sysTradeNo = "";
			$taskGrid._params.taskType = $type_span.parent().attr("tag-data");
			$("#sysTradeNo").val("")
		} catch (e) {
			alert("查询数据失败!"+e);
			return;
		}
		var fetch = $("#taskGrid").datagrid("fetch");
	});
}
/**
 * 操作处理
 */
function taskHandle(event){
	
		var $this = $(event);
		var param_taskId = $this.attr("tag-data");
		var param_taskType = $this.attr("tag-type");
		var param_task_handle = $this.attr("tag-handle");//处理任务的方法名
		var param_task_belong = $this.attr("tag-belong");//任务归属
		var sysTradeNo = $this.attr("tag-a-data");
		
		if("undefined"!=param_task_belong&&null!=param_task_belong && ""!=param_task_belong){
			console.info("taskId : "+param_taskId+";belong to :"+param_task_belong);
			alert("任务已经被领取!");
			return;
		}
		if( isNotEmpty(param_taskId) && isNotEmpty(param_taskType) ){
			console.info("taskId : "+param_taskId+"; taskType : "+param_taskType);
		}else{
			alert("参数有误！");
			console.warn("taskId : "+param_taskId+"; taskType : "+param_taskType);
			return;
		}
		try {
			var message = "";
			$.ajax({
				async : false,
				type : 'post',
				url : "/cs/doTaskHandle.do",
				data : {
					taskIdStr : param_taskId,
					taskTypeStr : param_taskType,
					taskHandleStr : param_task_handle,
					taskBelong : param_task_belong
				},
				dataType : 'json',
				success : function(data) {
					if( !isNotEmpty(data) ){
						alert("操作，后台处理异常!");
						return;
					};
					message += (true == data.loginFlag?"":"请登录!");
					message += (true == data.invalid?"后台没拿到参数!":"");
					if( data.loginFlag && !data.invalid ){
						//刷新表格
						$("#taskGrid").datagrid("fetch");
						getBacklogCount();
						if("WAIT_FOR_SUPPLIER_REPLY" != param_task_handle && "IGNORE" != param_task_handle){
							console.info("create a , simulation click");
							a_element = new Object();
							a_event = new Object();
							a_element=document.createElement("a");
							a_element.setAttribute("href","/eventOrderDetail/"+sysTradeNo+".html");
							a_element.setAttribute("target","_blank");
							/**模拟a标签点击，兼容firefox*/
							a_event = document.createEvent("MouseEvents");  
							a_event.initEvent("click", true, true);   
							a_element.dispatchEvent(a_event);
						}
					}else{
						//未登录并且没有数据，清空定时
						window.clearInterval(getBacklogJob);
						alert(message);
					};
				},
				error : function(data) {
					//网络异常，清空定时
					window.clearInterval(getBacklogJob);
					alert($this.html()+":网络异常"+data)
				}
			});
		} catch (e) {
			console.warn($this.html()+"异常"+e);
		}
}
/**
 * 非空校验
 * @param param
 * @returns {Boolean}
 */
function isNotEmpty (param) {
	if(typeof(param)!="undefined"&&param!=null&&param!=""){
		return true;
	}else{
		return false;
	}
}
/**
 * 查询功能
 */
function initSerch(){
	/** 查询按钮 */
	var $eventsSerch = $("#btn_query");
	var $taskGrid,fetch ;
	$eventsSerch.click(function(){
		try {
			$taskGrid = $( "#taskGrid" ).datagrid( "datagrid" );
			$taskGrid._params.sysTradeNo = $("#sysTradeNo").val();
			$taskGrid._params.taskType = "";
		} catch (e) {
			alert("查询数据失败!"+e);
			return;
		}
		fetch = $("#taskGrid").datagrid("fetch");
	});
	/** enter查询表格事件 */
	$(".enter-key-search").keyup(function(){
        if(event.keyCode == 13){
        	try {
    			$taskGrid = $( "#taskGrid" ).datagrid( "datagrid" );
    			$taskGrid._params.sysTradeNo = $("#sysTradeNo").val();
    			$taskGrid._params.taskType = "";
    		} catch (e) {
    			alert("查询数据失败!"+e);
    			return;
    		}
    		fetch = $("#taskGrid").datagrid("fetch");
        }
    });
}
/**
 * 查询代办事项
 */

function getBacklogCount() {
	console.info('query Back log count begin');
	$.ajax({
		async : false,
		type : 'GET',
		url : "/cs/queryTaskCount.do",
		contentType : 'application/json',
		dataType : 'json',
		success : function(data) {
			if (data != null) {
				if("no" === data.loginFlag){
					window.clearInterval(getBacklogJob);
				}
				all_count = 0;
				for (var i in countMap) {
					$type_span = $("#" + countMap[i]);
					$type_span.attr("new-count",data[i]);
					new_count = data[i];//新代办总计数
					new_count = isNaN(new_count)?0:new_count;
					$type_span.attr("new-count",new_count);
					$type_span.html(new_count);
					
					all_count += new_count;
					
					$type_span = "";
					new_count = 0;
				}
				try {
					$("#all-count").html(all_count);
				} catch (e) {
					window.clearInterval(getBacklogJob);
					console.info('warn!!! can not find span all-count ');
				}
			} else {
				window.clearInterval(getBacklogJob);
				console.info('warn!!! query Backlog count is null');
			}
		},
		error : function(data) {
			//网络断开，清空定时
			window.clearInterval(getBacklogJob);
			console.info('error!!! query Backlog count! '+JSON.stringify(data));
			alert("网络断了")
		}
	});
}

//对Date的扩展，将 Date 转化为指定格式的String   
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
//例子：   
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt)   
{ //author: meizz   
var o = {   
 "M+" : this.getMonth()+1,                 //月份   
 "d+" : this.getDate(),                    //日   
 "h+" : this.getHours(),                   //小时   
 "m+" : this.getMinutes(),                 //分   
 "s+" : this.getSeconds(),                 //秒   
 "q+" : Math.floor((this.getMonth()+3)/3), //季度   
 "S"  : this.getMilliseconds()             //毫秒   
};   
if(/(y+)/.test(fmt))   
 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
for(var k in o)   
 if(new RegExp("("+ k +")").test(fmt))   
fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
return fmt;   
}  