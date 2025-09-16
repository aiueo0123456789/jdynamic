<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
	<head> 
		<meta charset="UTF-8"> 
		<title>イベントの追加</title> 
	</head>
	<body>
		<jsp:include page="/jsp/header.jsp"/>
		<div class="container">
			<h1>イベントの追加</h1>
			<hr>
			<!-- メッセージ -->
    		<p class="error-message"><c:out value="${errorMessage}" /></p> 
    		<p class="success-message"><c:out value="${successMessage}" /></p>
			<form action="${pageContext.request.contextPath}/main" method="post"> 
				<input type="hidden" name="action" value="eventAdd">
				<p>
					<label for="name">名前:</label> <input type="text" id="name" name="event_name" value="" required>
					<span class="error-message"><c:out value="${errorMessage}" /></span>
				</p>
				<p>
					<label for="main_time">開催日時:</label>
					<input type="datetime-local" id="main_time" name="event_startTime" value="" required>
					<span class="error-message"><c:out value="${errorMessage}" /></span>
				</p>
				<p>
					<label for="main_time">終了日時:</label>  
					<input type="datetime-local" id="main_time" name="event_endTime" value="" required> 
					<span class="error-message"><c:out value="${errorMessage}" /></span> 
				</p>
				<div class="button-group"> 
					<input type="submit" value="追加する">
					<a href="${pageContext.request.contextPath}/main?action=list" class="button secondary">やめる</a>
				</div>
			</form>
		</div>
	</body>
</html>