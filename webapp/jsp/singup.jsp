<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>サインアップ</title>
<link rel="stylesheet" href="<c:url value='/style.css' />">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/>
	<div class="container">
	    <h1>サインアップ</h1>
		<form action="${pageContext.request.contextPath}/main" method="post"> 
			<input type="hidden" name="action" value="singup">  
			<label>
				識別名
			    <input type="number" placeholder="識別名" name="acount_id">
			</label>
			<label>
				表示名
			    <input type="text" placeholder="表示名" name="acount_name">
			</label>
			<label>
				パスワード
			    <input type="passwaord" placeholder="パスワード" name="acount_passwaord">
			</label>
		    <div class="button-group">
			   	<input type="submit" value="作成">
				<a href="${pageContext.request.contextPath}/main?action=list" class="button secondary">予約一覧に戻る</a>
			 </div>
	    </form>
	</div>
</body>
</html>