<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
<head> 
<meta charset="UTF-8"> 
<title>マイページ</title> 
</head> 
<body>
  <jsp:include page="/jsp/header.jsp"/>
  <div class="container">
  	<h1>マイページ</h1>
	<hr> 
    <!-- メッセージ -->
    <p class="error-message"><c:out value="${errorMessage}" /></p> 
    <p class="success-message"><c:out value="${successMessage}" /></p>

    <!-- 一覧テーブル --> 
    <tbody>
    <ul class="list">
      <c:forEach var="main" items="${events}"> 
        <li class="table">
          <%-- <h3>${main.id}</h3> --%>
          <div class="tableHeader">
           <h3>${main.name}</h3>
           <nav class="menu">
             <div class="hamburger">
               <span></span>
               <span></span>
               <span></span>
             </div>
             <ul>
             <li>
                <c:url var="reservationUrl" value="/main">
                  <c:param name="action" value="reservationAdd"/>
                  <c:param name="event_id" value="${main.id}"/>
                </c:url>
                <a href="${reservationUrl}" class="button">申込</a>
              </li>
              <li>
                <c:url var="editUrl" value="/main">
                  <c:param name="action" value="edit"/>
                  <c:param name="id" value="${main.id}"/> 
                </c:url>
                <a href="${editUrl}" class="button">編集</a>
              </li>
              <li>
                <form action="<c:url value='/main' />" method="post" style="display:inline;"> 
                  <input type="hidden" name="action" value="eventDelete"> 
                  <input type="hidden" name="id" value="${main.id}">
                  <input type="submit" value="削除" class="button danger" onclick="return confirm('本当に削除しますか？');">
                </form>
              </li>
             </ul>
           </nav>
          </div>
          <p>開催日時 : ${main.startTime}</p>
          <p>終了日時 : ${main.endTime}</p>
        </li>
      </c:forEach> 
    </ul>
      <c:if test="${empty events}"> 
        <tr> 
          <td colspan="4">予約がありません。</td> 
        </tr> 
      </c:if> 
    </tbody>
  </div>
</body> 
</html>