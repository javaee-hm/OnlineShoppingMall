<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="choice" action="<%=request.getContextPath()%>/checklogin" method="post">
  用户id<input type="text" name="userid"></input><br>  
  密码<input type="password" name="pwd"></input><br>  
<label><input name="identity" type="radio" value="0" />顾客 </label> 
<label><input name="identity" type="radio" value="1" />管理员 </label> 
  <input type="submit" value="登陆"></input> 
  <a href="<%=request.getContextPath()%>/jsp/register.jsp">注册</a>
</form>
</body>
</html>