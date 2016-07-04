<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>工单系统</title>
</head>
<body>
	<%@ include file="common/commonHead.jsp"%>
	<div id="sideMenu" style=" border-radius: 11px;width: 160px;top: 25%;">
		<!-- <div class="user">
			<img src="http://www.haiwan.com/res/images/logo-com.jpg"
				alt="HanWanCS" class="img-thumbnail">
			<a href="javascript:;" target="_blank" class="navbar-link">海玩客服组</a>
		</div> -->
		<div class="list-group" style="margin-top: 35%;">
			<a href="javascript:;" class="list-group-item" tag-data="WAIT_FOR_CS_CONFIRM">待客服确认订单<span id="waitConfirmCount" class="badge" new-count="0">0</span></a>
			<a href="javascript:;" class="list-group-item" tag-data="WAIT_FOR_SUPPLIER_REPLY">待供应商回复<span id="waitSupplierCount" class="badge" new-count="0">0</span></a> 
			<a href="javascript:;" class="list-group-item" tag-data="WAIT_FOR_MERCHANT_CONFIRM">等待商户处理<span id="waitMerchantCount" class="badge" new-count="0">0</span></a>
			<a href="javascript:;" class="list-group-item" tag-data="API_BOOKING_FAIL">API预定失败<span id="apiFailCount" class="badge" new-count="0">0</span></a> 
			<a href="javascript:;" class="list-group-item" tag-data="REFUND_FOR_CS_CONFIRM">退款申请审核<span id="refundExamineCount" class="badge" new-count="0">0</span></a>
			<a href="javascript:;" class="list-group-item" tag-data="EMAIL_SEND_FAIL">邮件发送失败<span id="emailFailCount" class="badge" new-count="0">0</span></a> 
			<a href="javascript:;" class="list-group-item" tag-data="CS_REMINDER">用户催单<span id="csReminderCount" class="badge" new-count="0">0</span></a>
		</div>
	</div>
	<div id="modalBody" class="panel-body" style="padding-bottom: 0px;margin-top: 50px;">
		<!-- <div class="panel panel-default">
			<div class="panel-heading">查询条件</div>
			<div class="panel-body">
				<form id="formSearch" class="form-horizontal">
					<div class="form-group" style="margin-top: 15px">
						<label class="control-label col-sm-1" for="sysTradeNo">订单号</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" placeholder="主订单号或子订单号"  id="sysTradeNo">
						</div>
						<div class="col-sm-4" style="text-align: left;">
							<button type="button" style="margin-left: 50px" id="btn_query"
								class="btn btn-primary">查询</button>
						</div>
					</div>
				</form>
			</div>
			
		</div> -->
		<div class="panel panel-default">
			<div class="panel-body"><div id="taskGrid" class="dg"></div></div>
		</div>
</body>
<script type="text/javascript" src="/static/pageJs/js/mainTaskList.js"></script>
</html>