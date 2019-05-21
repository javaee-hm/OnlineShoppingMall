<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="choice" action="<%=request.getContextPath()%>/UserAdd" method="post">
  用户id<input type="text" name="userid"></input><br>  
  密码<input type="password" name="pwd"></input><br> 
 用户名<input type="text" name="username"></input><br>   
  <input type="submit" value="注册"></input><br>  
</form>
</body>
</html>