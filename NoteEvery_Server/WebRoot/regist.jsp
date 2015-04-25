<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'regist.jsp' starting page</title>
    
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
h1{
	text-align: center;
}
.mytd{
	text-align: center;
}
</style>
  </head>
  
  <body>
  <h1>NoteEvery——Welcome Join Us!</h1>
  <div class="mytd">
    <form action="goregist.action" method="post">
    <table>
    	<tr>
    		<td>用户名：</td>
    		<td><input type="text" name="user.uname"></td>
    	</tr>
    	
    	<tr>
    		<td>密码：</td>
    		<td><input type="text" name="user.pwd"></td>
    	</tr>  
    </table>
    <input type="submit" value="立即注册">
    <a href="login.jsp">登录</a>
    </form>
    </div>
  </body>
</html>
