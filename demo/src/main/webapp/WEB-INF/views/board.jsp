<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�Խñ�</title>
</head>
<body>
	<h3>�Խñ۸��</h3>
	<table calss="table">
		<tr>
			<th>No</th>
			<th>����</th>
			<th>����</th>
			<th>�ۼ���¥</th>
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