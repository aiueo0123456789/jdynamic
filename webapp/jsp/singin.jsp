<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>サインイン</title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"/> 
	<div class="container">
	    <h1>サインイン</h1>
		<hr>
	    <form action="${pageContext.request.contextPath}/main" method="post"> 
			<input type="hidden" name="action" value="singin">  
			<label>
				識別名
			    <input type="number" placeholder="識別名" name="acount_id">
			</label>
			<label>
				パスワード
			    <input type="passwaord" placeholder="パスワード" name="acount_passwaord">
			</label>
		    <div class="button-group">
		    	<input type="submit" value="ログイン">
		    </div>
		 </form>
	</div>
</body>
</html>