<%@ page contentType="text/html; charset=utf-8" import="com.zheng.ucenter.dao.model.UcenterUser "%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>SignUp</title>
</head>
<body>
<h2 align="center">欢迎注册</h2>
<form name=loginForm action="/userLogin/signup" method=post>
    <table align="center">
        <tr>
            <td>用户名：</td><td><input type=text name=nickname /></td>
        </tr>
        <tr>
            <td>密码：</td><td><input type=password name=password /></td>
        <tr/>
        <tr>
            <td colspan="2",align="center">
                <input type="submit" value="submit" />
                <input type="reset" value="reset"/>
            </td>
        </tr>
    </table>

</form>
</body>
</html>