<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
	<head>
		<meta charset="UTF-8">
		<title>簡易予約システム</title>
		<link rel="stylesheet" href="style.css">
	</head>
	<body>
		<jsp:include page="/jsp/header.jsp"/>
		<div class="container">
			<h1>マイページ</h1>
			<hr>
			<h2>CSV インポート</h2> 
			<form action="main" method="post" enctype="multipart/form-data"> 
				<input type="hidden" name="action" value="import_csv">
				<p> 
					<label for="csvFile">CSV ファイルを選択:</label> <input type="file" 
					id="csvFile" name="csvFile" accept=".csv" required> 
				</p> 
				<div class="button-group"> 
					<input type="submit" value="インポート"> 
				</div> 
			</form> 
			<p class="error-message"> 
				<c:out value="${errorMessage}" /> 
			</p> 
			<p class="success-message"> 
				<c:out value="${successMessage}" /> 
			</p> 
			<div class="button-group"> 
				<a href="${pageContext.request.contextPath}/main?action=eventsList" class="button secondary">イベント一覧を見る</a> 
			</div>
			<div class="button-group"> 
				<a href="${pageContext.request.contextPath}/main?action=eventAdd" class="button secondary">イベント追加</a> 
			</div>
			<div class="button-group"> 
				<a href="${pageContext.request.contextPath}/main?action=acountsList" class="button secondary">アカウント一覧を見る</a> 
			</div>
		</div> 
	</body> 
</html>