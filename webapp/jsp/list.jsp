<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html> 
<html lang="ja"> 
<head> 
<meta charset="UTF-8"> 
<title>イベント一覧</title> 
<link rel="stylesheet" href="<c:url value='/style.css' />"> 
</head> 
<body>
  <div>		
    <h1>イベント一覧</h1> 
  </div>
  <div class="container">
 
    <!-- 検索・ソート --> 
    <form action="<c:url value='/main' />" method="get" class="search-sort-form"> 
      <input type="hidden" name="action" value="list"> 
      <div> 
        <label for="search">検索:</label> 
        <input type="text" id="search" name="search" 
               value="<c:out value='${searchTerm}'/>" 
               placeholder="名前または日時"> 
      </div>
      <div>
        <label for="sortBy">ソート基準:</label>
        <select id="sortBy" name="sortBy">
          <option value="" <c:if test="${sortBy == null || sortBy == ''}">selected </c:if>>選択してください</option> 
          <option value="name" <c:if test="${sortBy == 'name'}">selected</c:if>>名前 </option>
          <option value="time" <c:if test="${sortBy == 'time'}">selected</c:if>>日時</option>
        </select>
        </div> 
      <div> 
        <label for="sortOrder">ソート順:</label> 
        <select id="sortOrder" name="sortOrder"> 
          <option value="asc"  <c:if test="${sortOrder == 'asc'}">selected</c:if>>昇順
</option> 
          <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>降順
</option> 
        </select> 
      </div> 
      <button type="submit" class="button">検索/ソート</button> 
    </form> 
 
    <!-- メッセージ -->
    <p class="error-message"><c:out value="${errorMessage}" /></p> 
    <p class="success-message"><c:out value="${successMessage}" /></p> 

    <!-- CSV操作/クリーンアップ -->
    <div class="button-group"> 
      <a href="<c:url value='/main'><c:param name='action' value='export_csv'/></c:url>" class="button">CSV エクスポート</a>
 
      <form action="<c:url value='/main' />" method="get" style="display:inline;"> 
        <input type="hidden" name="action" value="clean_up" /> 
        <input type="submit" value="過去の予約をクリーンアップ" class="button secondary" 
               onclick="return confirm('本当に過去の予約を削除しますか？');"> 
      </form> 
    </div> 
    <!-- 一覧テーブル --> 
    <tbody>
    <ul class="list">
      <c:forEach var="main" items="${events}"> 
        <li class="table">
          <%-- <h3>${main.id}</h3> --%>
          <div class="flex">
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
                  <c:param name="action" value="reservation"/>
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
                  <input type="hidden" name="action" value="delete"> 
                  <input type="hidden" name="id" value="${main.id}"> 
                  <input type="submit" value="キャンセル" class="button danger" onclick="return confirm('本当にキャンセルしますか？');">
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
 
    <div class="button-group">
    <a href="<c:url value='/index.jsp' />" class="button secondary">トップに戻る</a> 
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