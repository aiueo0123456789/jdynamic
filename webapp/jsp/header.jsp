<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
<head> 
<meta charset="UTF-8">  
<link rel="stylesheet" href="<c:url value='/style.css' />">
</head> 
<body>
	<div class="header">
		<div></div> <!-- padding -->
      	<li><a href="${pageContext.request.contextPath}/index.jsp" class="button secondary">トップ</a></li>
      	<li><a href="${pageContext.request.contextPath}/main?action=reservationsList" class="button secondary">予約済み</a></li>
      	<c:choose>
		    <c:when test="${empty activeAcount}">
		      	<li><a href="${pageContext.request.contextPath}/jsp/singin.jsp" class="button secondary">ログイン</a></li>
		    </c:when>
		    <c:otherwise>
		        <li><a href="${pageContext.request.contextPath}/jsp/singout.jsp" class="button secondary">ログアウト</a></li>
		    </c:otherwise>
		</c:choose>
      	<li><a href="${pageContext.request.contextPath}/jsp/singup.jsp" class="button secondary">会員登録</a></li>
	</div>
</body>
</html>