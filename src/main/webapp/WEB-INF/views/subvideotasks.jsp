<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="/console/index">首页</a> >> <a href="/console/videotasks">视频任务列表</a>
	<table border=1>
		<tr>
		    <th>ID</th>
		    <th>VID</th>
			<th>平台</th>
			<th>标题</th>
			<th>URL</th>
			<th>状态<br>弹幕状态</th>
			<th>添加时间</th>
			<th>评论数<br>弹幕数</th>
			<!-- <th>结束时间</th> -->
		</tr>
		<c:forEach var="subvideotask" items="${subvideotasks }">
			<tr>
				<td><c:out value="${subvideotask.sub_vid }"/></td>
				<td><c:out value="${subvideotask.vid }"/></td>
				<td><c:out value="${subvideotask.platformStr }"/></td>
				<td><c:out value="${subvideotask.title }"/></td>
				<td><c:out value="${subvideotask.page_url }"/></td>
				<td><c:out value="${subvideotask.statusStr }"/><br>
					<c:choose>
						<c:when test="${subvideotask.barrage_status==0}">初始化</c:when>
						<c:when test="${subvideotask.barrage_status==1}">运行中</c:when>
						<c:when test="${subvideotask.barrage_status==2}">完成</c:when>
						<c:when test="${subvideotask.barrage_status==-1}">⼿手动结束</c:when>
						<c:when test="${subvideotask.barrage_status==-2}">异常结束</c:when>
						<c:otherwise>
							- - -
						</c:otherwise>
					</c:choose>
				</td>
				<td><c:out value="${subvideotask.add_time }"/></td>
				<td><c:out value="${subvideotask.count }"/><br>${subvideotask.barrageCount }</td>
				<%-- <td><c:out value="${subvideotask.last_update_time }"/></td> --%>
			</tr>
		</c:forEach>
	</table>
</body>
</html>