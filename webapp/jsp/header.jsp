<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
<head> 
<meta charset="UTF-8">  
	<link rel="stylesheet" href="<c:url value='/style.css' />">
	<link rel="stylesheet" href="https://unpkg.com/modern-css-reset/dist/reset.min.css"/>
</head> 
<body>
    <c:if test="${not empty activeAcount}">
   		<div class="headerHeader">
   			<div></div> <!-- padding -->
   			<p>
    			アカウント名 : ${activeAcount.name}
   			</p>
   		</div>
    </c:if>
	<div class="header">
		<div></div> <!-- padding -->
      	<li>
      		<c:url var="goToTopUrl" value="/main">
				<c:param name="action" value="goToTop" />
			</c:url>
			<a href="${goToTopUrl}">トップ</a>
		</li>
      	<li><a href="${pageContext.request.contextPath}/main?action=reservationsList" class="secondary">予約済み</a></li>
      	<c:choose>
		    <c:when test="${empty activeAcount}">
		      	<li><a href="${pageContext.request.contextPath}/jsp/singin.jsp" class="secondary">ログイン</a></li>
		    </c:when>
		    <c:otherwise>
		    	<li>
		        	<a href="${pageContext.request.contextPath}/main?action=singout" class="secondary">ログアウト</a>
		    	</li>
		    </c:otherwise>
		</c:choose>
      	<li><a href="${pageContext.request.contextPath}/jsp/singup.jsp" class="secondary">会員登録</a></li>
	</div>
  <script>
document.addEventListener("DOMContentLoaded", function () {
  const menus = document.querySelectorAll(".menu");
  for (const menu of menus) {
	  const hamburger = menu.querySelector(".hamburger");
	  hamburger.addEventListener("click", function () {
	    menu.classList.toggle("open");
	  });
  }
});
  </script>
</body>
</html>