/**
 * @tag 参考网站
 * http://labs.creative-area.net/jquery.datagrid/
 * http://v3.bootcss.com/components/#thumbnails-custom-content
 */

$(function() {
	/** 初始化表格 */
	initGrid();
	/** 查询 */
	initSerch();
	/** 搜索框类别补全 **/
	var search = new SerchAutocomplete_private("#searchEvery");
	search.init();
	/** 时间渲染 */
	$(".clockpicker").clockpicker();
	/** 按钮渲染 */
	new InitBtn({
		edit:"btn-edit-task",
		add:"btn-add-task",
		open_modal:"open-edit-modal"
	}).initAll();
});
var InitBtn = function (object) {
	this.edit_btn = object.edit,
	this.add_btn = object.add;
	this.open_modal = object.open_modal;
}
InitBtn.prototype = {
	initAll: function () {
		this.init_edit_task(this.edit_btn);
		this.init_add_task(this.add_btn);
		this.init_open_modal(this.open_modal);
	},
	init_edit_task: function (edit_btn) {
		var param = {};
		var $edit_btn = $("."+edit_btn);

		$edit_btn.click(function(even){

			$edit_btn.parent().parent().find("input").each(function(i,item){
				param[$(item).attr("name")] = $(item).val();
			});
			$edit_btn.parent().parent().find("select").each(function(i,item){
				param[$(item).attr("name")] = $(item).val();
			});
			$.ajax({
				async : false,
				type : 'post',
				url : "editTaskElement.do",
				data : param,
				dataType : 'json',
				//contentType : 'application/json',
				success : function(data) {
					if(data.message){
						//单独刷新表格
						$("#taskGrid").datagrid("fetch");
						$.gritter.add({
							title:"一个nice的消息",
							text:'修改成功！',
							time:2000
						});
						//关闭modal
						$("#editTaskModal").modal('hide');
					}else{
						alert("修改失败！");
						//关闭modal
						$("#editTaskModal").modal('hide');
						console.error()
					}
				}
			});
		});
	},
	init_add_task:function (add_btn){
		var param = {};
		var $add_btn = $("."+add_btn);
		$add_btn.click(function(){
			$add_btn.parent().parent().find("input").each(function(i,item){
				param[$(item).attr("name")] = $(item).val().trim();
			});
			$add_btn.parent().parent().find("select").each(function(i,item){
				param[$(item).attr("name")] = $(item).val().trim();
			});
			if(null == param || {} == param){
				$.gritter.add({
					title:"一个消息",
					text:'请填入参数！',
					time:2000
				});
				return;
			}
			$.ajax({
				async : false,
				type : 'post',
				data : param,
				url : "addVideoTask.do",
				//contentType : 'application/json',
				dataType : 'json',
				success : function(data) {
					if(data.message){
						//单独刷新表格
						$("#taskGrid").datagrid("fetch");
						$.gritter.add({
							title:"一个nice的消息",
							text:'任务添加添加成功！',
							time:2000
						});
						//关闭modal
						$("#addTaskModal").modal('hide');
					}else{
						alert("修改失败！");
						//关闭modal
						$("#addTaskModal").modal('hide');
						console.error()
					}
				}
			});
		});
	},
	init_open_modal : function(open_modal){
		var $span = $("."+open_modal);
		var td_data = $span.attr("data-grid");
		var $modal = $($span.attr("data-target"))
		$span.click(function(td_data){
			alert(td_data);

			$modal.find("input").each(function(i,item){

			});

		})

	}

}

/**
 * 初始化表格
 */
function initGrid(){
	var taskGrid = $( "#taskGrid" ).datagrid({
		url: "/queryMainTasks.json",
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
	    idField:'vid',
	    rownumbers:true,
		col: [{
			field: "vid",
			title: "vId",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        }
		},{
			field: "platform",
			title: "平台",
			attrHeader: { 
	            //"width": "20%",
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			render: function( data ) {
				var platformStr = "";
				switch (data.value) {
					case 0:
						platformStr = "腾讯";
						break;
					case 1:
						platformStr = "优酷土豆";
						break;
					case 2:
						platformStr = "爱奇艺";
						break;
					case 3:
						platformStr = "乐视";
						break;
					case 4:
						platformStr = "搜狐";
						break;
					case 5:
						platformStr = "B站";
						break;
					default:
						platformStr = "";
						break;
				}
				return platformStr;
			}
		},{
			field: "title",
			title: "标题",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        }
		},{
			field: "url",
			title: "URL",
			attrHeader: {
				"width": "12%",
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        }
		},{
			field: "totalAmt",
			title: "评论状态</br>弹幕状态",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			attr: { "style": "width:100px"},
			render: function( data ){
				var commentStatus = data.row.status;
				var barrageStatus = data.row.barrage_status;
				var barrageStatusStr = "未知状态";
				var commentStatusStr = "未知状态";
				switch (barrageStatus) {
					case -2:
						barrageStatusStr = "异常结束";
						break;
					case -1:
						barrageStatusStr = "手动结束";
						break;
					case 0:
						barrageStatusStr = "初始化";
						break;
					case 1:
						barrageStatusStr = "进行中";
						break;
					case 2:
						barrageStatusStr = "完成";
						break;
					default:
						break;
				}
				switch (commentStatus) {
					case -2:
						commentStatusStr = "异常结束";
						break;
					case -1:
						commentStatusStr = "手动结束";
						break;
					case 0:
						commentStatusStr = "初始化";
						break;
					case 1:
						commentStatusStr = "进行中";
						break;
					case 2:
						commentStatusStr = "完成";
						break;
					default:
						break;
				}
                return commentStatusStr + "</br>"+barrageStatusStr;
			}
		},{
			field: "start_time",
			title: "开始时间",
			attrHeader: {
				"style": "text-align: center;",
				"nowrap": "nowrap"
			},
			render: function( data ){
				if(null != data.value){
					return new Date(parseInt(data.value)).Format("yyyy-MM-dd hh:mm:ss");
				}else{
					return "<span class='label label-default'>----</span>";
				}
			}
		},{
			field: "reset_time",
			title: "定时重抓时间",
			attrHeader: {
				"style": "text-align: center;",
				"nowrap": "nowrap"
			},
			render: function( data ){
				if(null != data.value){
					return new Date(parseInt(data.value)).Format("hh:mm:ss");
				}else{
					return "<span class='label label-default'>-----</span>";
				}
			}
		},{
			field: "count",
			title: "评论数</br>弹幕数",
			attrHeader: {
				"style": "text-align: center;",
				"nowrap": "nowrap"
			},
			attr: { "style": "width:100px"},
			render: function( data ){
				//return  data.row.count + "</br>"+data.row.barrageCount;

				//return getCount(data.row.vid);
				return '<span class="label label-info" style="cursor:pointer" onclick="getCount(this,'+data.row.vid+')">点我看</span>';
			}
		},{
			field: "_operation1",
			title: "详细",
			attrHeader: { 
	            "style": "text-align: center;", 
	            "nowrap": "nowrap"
	        },
			render: function( data ){
				var rows = data.row;
				var edit = '<span class="label label-info open-edit-modal" style="cursor:pointer" onclick="editTaskModal(this)" data-rows='+JSON.stringify(rows)+' data-toggle="modal" data-target="#editTaskModal">修改</span>';
				return "<a target='_blank' style='text-decoration: none;' href='/subvideotasks/'+data.vid+ >详情</a><br>"+edit;
			}
		},{
			field: "_operation2",
			title: "操作",
			attrHeader: {
				"style": "text-align: center;",
				"nowrap": "nowrap"
			},
			render: function( data ){
				var rows = data.row;
				var btn = "";
				if( rows.status === 0 || rows.status === 1 ){
					btn += "<span class='label label-warning' style='cursor:pointer' onclick='stop_confirm("+rows.vid+");'>停止</span>";
				}else{
					btn += "<span class='label label-primary' style='cursor:pointer' onclick='revert_confirm("+rows.vid+");'>重置</span>";
				}
				btn += "<span class='label label-danger' style='cursor:pointer' onclick='remove_confirm("+rows.vid+");'>删除</span>";
				return btn;
			}
		}],
		onRowData: function( data, num, $tr ) {
	        /*if ( data.csEmail === user_name ) {
	            $tr.addClass( "success" );
	        } */
	    },
		attr: { "class": "table table-bordered table-condensed" },
		sorter: "bootstrap",
		pager: "bootstrap",
		paramsDefault: {
			searchEvery : "",
	    	taskType : ""
	    },
	});
}

function getCount(e,vid){
	var $span = $(e);
	var cCount = 0;
	var bCount = 0;
	var html = "";
	$.ajax({
		async : false,
		url : "/queryMainTasksCBCount.json?taskId="+vid,
		dataType : 'json',
		success : function(data) {
			html = data.commentCount + "<br/>"+data.barrageCount
		}
	});
	return $span.parent().html(html);
}
function editTaskModal(e){

	var $span = $(e);
	var td_data = JSON.parse($span.attr("data-rows"));
	var $modal = $($span.attr("data-target"))
	$modal.find("input").each(function(i,item){
		$(item).val(td_data[$(item).attr("name")])
	});
	$modal.find("input").each(function(i,item){
		$(item).val(td_data[$(item).attr("name")])
	});
}
/***
 * @Title 停止任務
 * @param vid
 */
function stop_confirm(vid){
	$.ajax({
		async : false,
		data : vid,
		url : "/stopvideotask_new/"+vid,
		dataType : 'json',
		success : function(data) {
			if(data.message){
				//单独刷新表格
				$("#taskGrid").datagrid("fetch");
				$.gritter.add({
					title:"一个nice的消息",
					text:'任務已經停止！',
					time:2000
				});
				//关闭modal
				$("#addTaskModal").modal('hide');
			}else{
				alert("修改失败！");
				//关闭modal
				$("#addTaskModal").modal('hide');
				console.error();
			}
		}
	});
}
/***
 * @Title 重置任務
 * @param vid
 */
function revert_confirm(vid){
	$.ajax({
		async : false,
		type : 'get',
		url : "/revertvideotask_new/"+vid,
		contentType : 'application/json',
		dataType : 'json',
		success : function(data) {
			if(data.message){
				//单独刷新表格
				$("#taskGrid").datagrid("fetch");
				$.gritter.add({
					title:"一个nice的消息",
					text:'任務已經停止！',
					time:2000
				});
				//关闭modal
				$("#addTaskModal").modal('hide');
			}else{
				alert("修改失败！");
				//关闭modal
				$("#addTaskModal").modal('hide');
				console.error();
			}
		}
	});
}
/***
 * @Title 刪除任務
 * @param vid
 */
function remove_confirm(vid){
	$.ajax({
		async : false,
		type : 'get',
		url : "/removevideotask_new/"+vid,
		contentType : 'application/json',
		dataType : 'json',
		success : function(data) {
			if(data.message){
				//单独刷新表格
				$("#taskGrid").datagrid("fetch");
				$.gritter.add({
					title:"一个nice的消息",
					text:'任務已經停止！',
					time:2000
				});
				//关闭modal
				$("#addTaskModal").modal('hide');
			}else{
				alert("修改失败！");
				//关闭modal
				$("#addTaskModal").modal('hide');
				console.error()
			}
		}
	});
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
			$taskGrid._params.searchEvery = $("#searchEvery").val();
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
    			$taskGrid._params.searchEvery = $("#searchEvery").val();
    			$taskGrid._params.taskType = "";
    		} catch (e) {
    			alert("查询数据失败!"+e);
    			return;
    		}
    		fetch = $("#taskGrid").datagrid("fetch");
        }
    });
}
/***
  * @param fmt
 * @returns {*}
 * @constructor
 * 对Date的扩展，将 Date 转化为指定格式的String
 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 例子：
 (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 */
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

/**
 * 自动提示
 * @param element
 * @constructor
 */
var SerchAutocomplete_private = function (element) {
	this.$element = $(element)
}
SerchAutocomplete_private.prototype = {

	init: function () {
		return  this.$element.autocomplete({
			delay: 0,
			source: function (query, process) {
				var matchCount = this.options.items;//返回结果集最大数量
				//$.post("/bootstrap/region",{"matchInfo":query,"matchCount":matchCount},function(respData){
				//    return process(respData);
				//});
				var resp_data = [{
					regionName: "任务编号",
					regionCode: query,
					regionType: 1,
					regionNameEn: "vid"
				}, {
					regionName: "任务名称",
					regionCode: query,
					regionType: 2,
					regionNameEn: "title"
				}, {
					regionName: "URL",
					regionCode: query,
					regionType: 3,
					regionNameEn: "url"
				}];
				//return process(resp_data);
				return resp_data;
			},
			formatItem: function (item) {
				return item["regionName"] + " : " + item["regionCode"];
				//return item["regionName"]+"("+item["regionNameEn"]+"，"+item["regionShortnameEn"]+") - "+item["regionCode"];
			},
			setValue: function (item) {
				return {'data-value': item["regionCode"], 'real-value': item["regionNameEn"]};
			},
			select_callback:function(val,realVal,$serch_input){
				var $taskGrid = $( "#taskGrid" ).datagrid( "datagrid" );
				try {
					$taskGrid._params.page = 1;//每次都初始化第一页
					$taskGrid._params.searchEvery = $("#searchEvery").val();
					search_content = $("#searchEvery").val();
					$taskGrid._params.taskType = "";
					$taskGrid._params.taskBelong = document.getElementById("myOrder").value;
					$taskGrid._params.searchType = realVal;
					$taskGrid._params.channelSource = search_channel;
					document.getElementById("btn_query").value = realVal;
				} catch (e) {
					alert("查询数据失败!"+e);
					return;
				}
				fetch = $("#taskGrid").datagrid("fetch");
			}
			//获取文本框的实际值
			//var regionCode = $("#autocompleteInput").attr("real-value") || "";

		});
	}
}
