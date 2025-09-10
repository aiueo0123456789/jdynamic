<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>サインイン</title>
<link rel="stylesheet" href="<c:url value='/style.css' />">
</head>
<body>
<div class="container">
    <h1>サインイン</h1>
    <input placeholder="表示名">
    <input placeholder="パスワード">
    <div class="button-group">
        <a href="<c:url value='/reservation?action=list' />" class="button secondary">予約一覧に戻る</a>
        <a href="<c:url value='/index.jsp' />" class="button secondary">トップに戻る</a>
    </div>
</div>
</body>
</html>