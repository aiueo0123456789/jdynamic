<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント一覧</title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp" />
	<div class="container">
		<h1>アカウント一覧</h1>
		<hr>
		<!-- メッセージ -->
		<p class="error-message">
			<c:out value="${errorMessage}" />
		</p>
		<p class="success-message">
			<c:out value="${successMessage}" />
		</p>
		<!-- 検索・ソート -->
		<form action="<c:url value='/main' />" method="get" class="search-sort-form">
			<input type="hidden" name="action" value="acountsList"> <label
				for="search">検索: <input type="text" id="search"
				name="search" value="<c:out value='${searchTerm}'/>"
				placeholder="表示名前">
			</label>
			<button type="submit" class="button">検索</button>
		</form>

		<!-- メッセージ -->
		<p class="error-message">
			<c:out value="${errorMessage}" />
		</p>
		<p class="success-message">
			<c:out value="${successMessage}" />
		</p>
		<!-- 一覧テーブル -->
		<tbody>
			<ul class="list">
				<c:forEach var="main" items="${acounts}">
					<li class="table">
						<%-- <h3>${main.id}</h3> --%>
						<div class="tableHeader">
							<h3>${main.name}</h3>
							<nav class="menu">
								<div class="hamburger">
									<span></span> <span></span> <span></span>
								</div>
								<ul>
									<li>
										<c:url var="editUrl" value="/main">
											<c:param name="action" value="acountEdit" />
											<c:param name="id" value="${main.id}" />
										</c:url> <a href="${editUrl}">編集</a></li>
									<li>
										<form action="<c:url value='/main' />" method="post"
											style="display: inline;">
											<input type="hidden" name="action" value="acountDelete">
											<input type="hidden" name="id" value="${main.id}"> <input
												type="submit" value="削除"
												onclick="return confirm('本当にアカウントを削除しますか？');">
										</form>
									</li>
								</ul>
							</nav>
						</div>
					</li>
				</c:forEach>
			</ul>
			<c:if test="${empty acounts}">
				<tr>
					<td colspan="4">アカウントがありません。</td>
				</tr>
			</c:if>
		</tbody>

		<!-- ページネーション -->
		<div class="pagination">
			<c:if test="${currentPage != 1}">
				<c:url var="prevUrl" value="/main">
					<c:param name="action" value="list" />
					<c:param name="page" value="${currentPage - 1}" />
					<c:param name="search" value="${searchTerm}" />
					<c:param name="sortBy" value="${sortBy}" />
					<c:param name="sortOrder" value="${sortOrder}" />
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
							<c:param name="action" value="list" />
							<c:param name="page" value="${i}" />
							<c:param name="search" value="${searchTerm}" />
							<c:param name="sortBy" value="${sortBy}" />
							<c:param name="sortOrder" value="${sortOrder}" />
						</c:url>
						<a href="${pageLink}">${i}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${currentPage lt noOfPages}">
				<c:url var="nextUrl" value="/main">
					<c:param name="action" value="list" />
					<c:param name="page" value="${currentPage + 1}" />
					<c:param name="search" value="${searchTerm}" />
					<c:param name="sortBy" value="${sortBy}" />
					<c:param name="sortOrder" value="${sortOrder}" />
				</c:url>
				<a href="${nextUrl}">次へ</a>
			</c:if>
		</div>

	</div>
</body>
</html>