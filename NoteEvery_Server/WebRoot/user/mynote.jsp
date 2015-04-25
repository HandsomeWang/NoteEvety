<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'mynote.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
* {
	font-family: 微软雅黑;
}

.title {
	color: green;
	font-size: 25px;
	text-align: left;
}

li{
	float: left;
	width: 400px;
	height: 200px;
	margin-bottom: 5px;
	margin-left: 5px;
	padding:10px;
	background: #FFDEAD ;
}
ul {
	list-style-type: none;

}
.time{
	color: red;
	font-size: 17px;
	font-weight: bold;
}
.in{
	width: 350px;
	height: 70px;
}
.col{
	width: 70px;
}
tr{
	height: 40px;
}
.new{
	color: blue;
}
</style>
  </head>
  
  <body>
  <form action="addnote.action" method="post">
    <ul>
    	<li>
				<table>
					<tr>
						<td class="new">添加新的note：</td>
					</tr>
					<tr>
						<td><input class="in" type="text" name="newnote"/></td>
					</tr>
					
					<tr>
						<td class="col">
							<input type="submit" value="添加">
						</td>
					</tr>
				</table>
			</li>
		<c:forEach items="${ln}" var="notelist">
			<li>
				<table>
					<tr>
						<td class="col">时间：</td>
						<td class="time">${notelist.ndate}</td>
					</tr>
					<tr>
						<td class="col">内容：</td>
						<td>${notelist.ncontent}</td>
					</tr>
					
					<tr>
						<td class="col"></td>
						<td >
							<a href="goedit.action?nid=${notelist.nid}">修改</a>
							<a href="delete.action?nid=${notelist.nid}">删除</a>
						</td>
					</tr>
				</table>
			</li>
		</c:forEach>
	</ul>
	</form>
  </body>
</html>
