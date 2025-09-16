<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
	<head> 
		<meta charset="UTF-8"> 
		<title>予約</title> 
		<link rel="stylesheet" href="<c:url value='/style.css' />"> 
	</head>
	<body>
		<a href="<c:url value='/index.jsp' />" class="button secondary">トップに戻る</a> 
		<div class="container"> 
			<h1>予約</h1> 
			<form action="${pageContext.request.contextPath}/main" method="post"> 
				<input type="hidden" name="action" value="reservationAdd">  
				<input type="hidden" name="event_id" value="${event.id}">
				<p> 
					<label for="name">イベント名:</label>  
					<input type="text" name="event_name" value="<c:out value="${event.name}"/>" required>
			  	</p>
				<p>
					<label for="reservation_time">アカウント:</label>  
					<input type="text" name="acount_name" value="<c:out value="${acount.name}"/>" required>
				 </p>
				 <div class="button-group">
			   	 <input type="submit" value="申込"> 
				 <a href="${pageContext.request.contextPath}/main?action=list" class="button secondary">予約一覧に戻る</a>
				 </div> 
			</form> 
		</div> 
	</body> 
</html> 