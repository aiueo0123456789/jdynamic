<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
	<head> 
		<meta charset="UTF-8"> 
		<title>予約済み</title> 
	</head>
	<body>
		<jsp:include page="/jsp/header.jsp"/>
		
		<!-- メッセージ -->
    	<p class="error-message"><c:out value="${errorMessage}" /></p> 
	    <p class="success-message"><c:out value="${successMessage}" /></p>

		<div class="container">
			<h1>予約済みのイベント</h1>
			<hr>
			<tbody>
			    <ul class="list">
			      <c:forEach var="main" items="${reservations}"> 
			        <li class="table">
			          <%-- <h3>${main.id}</h3> --%>
			          <div class="tableHeader">
			           <h3>${main.event.name}</h3>
			           <nav class="menu">
			             <div class="hamburger">
			               <span></span>
			               <span></span>
			               <span></span>
			             </div>
			             <ul>
			              <li>
			                <form action="<c:url value='/main' />" method="post" style="display:inline;"> 
			                  <input type="hidden" name="action" value="reservationDelete"> 
			                  <input type="hidden" name="reservation_id" value="${main.id}"> 
			                  <input type="submit" value="キャンセル" onclick="return confirm('本当にキャンセルしますか？');">
			                </form>
			              </li>
			             </ul>
			           </nav>
			          </div>
			          <p>開催日時 : ${main.event.startTime}</p>
			          <p>終了日時 : ${main.event.endTime}</p>
			        </li>
			      </c:forEach> 
			    </ul>
		      <c:if test="${empty reservations}"> 
		        <tr> 
		          <td colspan="4">予約がありません。</td> 
		        </tr> 
		      </c:if> 
		    </tbody>
		 
		    <!-- ページネーション --> 
		    <div class="pagination"> 
		      <c:if test="${currentPage != 1}"> 
		        <c:url var="prevUrl" value="/main"> 
		          <c:param name="action" value="list"/> 
		          <c:param name="page" value="${currentPage - 1}"/>
		          <c:param name="search" value="${searchTerm}"/> 
		          <c:param name="sortBy" value="${sortBy}"/> 
		          <c:param name="sortOrder" value="${sortOrder}"/> 
		        </c:url> 
		        <a href="${prevUrl}">前へ</a> 
		      </c:if> 
		 
		      <c:forEach begin="1" end="${noOfPages}" var="i"> 
		        <c:choose> 
		          <c:when test="${currentPage eq i}"> 
		            <span class="current">${i}</span> 
		          </c:when> 
		          <c:otherwise> 
		            <c:url var="pageLink" value="/main"> 
		              <c:param name="action" value="list"/> 
		              <c:param name="page" value="${i}"/> 
		              <c:param name="search" value="${searchTerm}"/> 
		              <c:param name="sortBy" value="${sortBy}"/> 
		              <c:param name="sortOrder" value="${sortOrder}"/> 
		            </c:url> 
		            <a href="${pageLink}">${i}</a> 
		          </c:otherwise>
		        </c:choose>
		      </c:forEach>
		      <c:if test="${currentPage lt noOfPages}"> 
		        <c:url var="nextUrl" value="/main"> 
		          <c:param name="action" value="list"/> 
		          <c:param name="page" value="${currentPage + 1}"/> 
		          <c:param name="search" value="${searchTerm}"/> 
		          <c:param name="sortBy" value="${sortBy}"/> 
		          <c:param name="sortOrder" value="${sortOrder}"/> 
		        </c:url> 
		        <a href="${nextUrl}">次へ</a> 
		      </c:if> 
		    </div> 
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