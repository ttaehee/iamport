<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시글</title>
</head>
<body>
	<h3>게시글목록</h3>
	<table calss="table">
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>내용</th>
			<th>작성날짜</th>
		</tr>
		<c:foreach var="board" items="${list}">
		<tr>
		 	<td>${board.boardNo}</td>
		 	<td>${board.title}</td>
		 	<td>${board.content}</td>
		 	<td><fmt.formatDate value="${board.regDate}" pattern="MM/dd"/></td>
		 </tr>
		 </c:foreach>
	</table>

</body>
</html>