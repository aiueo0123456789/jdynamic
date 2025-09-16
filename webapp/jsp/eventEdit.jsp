<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>イベント編集</title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp" />
	<div class="container">
		<h1>イベント編集</h1>
		<hr>
		<form action="${pageContext.request.contextPath}/main" method="post">
			<input type="hidden" name="action" value="eventEdit">
			<input type="hidden" name="id" value="${event.id}">
			<label for="name">名前:
				<input type="text" id="name" name="name" value="<c:out value="${event.name}"/>" required>
				<span class="error-message"><c:out value="${errorMessage}" /></span>
			</label>
			<label for="main_time">開始日時:
				<input type="datetime-local" id="startTime" name="startTime" value="<c:out value="${event.startTime}"/>" required>
				<span class="error-message"><c:out value="${errorMessage}" /></span>
			</label>
			<label for="main_time">終了日時:
				<input type="datetime-local" id="endTime" name="endTime" value="<c:out value="${event.endTime}"/>" required>
				<span class="error-message"><c:out value="${errorMessage}" /></span>
			</label>
			<div class="button-group">
				<input type="submit" value="保存">
				<a href="${pageContext.request.contextPath}/main?action=list" class="button secondary">イベント一覧に戻る</a>
			</div>
		</form>
	</div>
</body>
</html>
