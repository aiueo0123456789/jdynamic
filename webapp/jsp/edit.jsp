<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
	<head> 
	<meta charset="UTF-8"> 
	<title>予約編集</title> 
	<link rel="stylesheet" href="<c:url value='/style.css' />"> 
	</head> 
	<body> 
		<div class="container"> 
			<h1>イベント編集</h1> 
			<form action="${pageContext.request.contextPath}/main" method="post"> 
				<input type="hidden" name="action" value="update">  
				<input type="hidden" name="id" value="${event.id}"> 
				<p>
					<label for="name">名前:</label>  
					<input type="text" id="name" name="name" value="<c:out value="${event.name}"/>" required> 
					<span class="error-message"><c:out value="${errorMessage}" /></span> 
			  	</p> 
				<p> 
					<label for="main_time">開始日時:</label>  
					<input type="datetime-local" id="main_time" name="satrtTime" value="<c:out value="${event.startTime}"/>" required>
					<span class="error-message"><c:out value="${errorMessage}" /></span>
				 </p>
				 <p> 
					<label for="main_time">終了日時:</label>  
					<input type="datetime-local" id="main_time" name="endTime" value="<c:out value="${event.endTime}"/>" required>
					<span class="error-message"><c:out value="${errorMessage}" /></span>
				 </p>
				 <div class="button-group">
			   	 <input type="submit" value="更新"> 
				 <a href="${pageContext.request.contextPath}/main?action=list" class="button secondary">イベント一覧に戻る</a>
				 </div> 
			</form> 
		</div> 
	</body> 
</html> 