<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC게시판</title>
</head>
<body>
	<h2>상세보기</h2>
	<table border="1" width="90%">
		<colgroup>
			<col width="15%"><col width="35%">
			<col width="15%"><col width="*">
		</colgroup>
		<tr>
			<td>번호</td><td>${dto.idx}</td>
			<td>작성자</td><td>${dto.name}</td>
		</tr>
		<tr>
			<td>작성일</td><td>${dto.postdate}</td>
			<td>조회수</td><td>${dto.visitcount}</td>
		</tr>
		<tr>
			<td>제목</td>
			<td colspan="3">${dto.title}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td colspan="3" height="100">${dto.content}</td>
		</tr>
		<tr>
			<td>첨부파일</td>
			<td>
				<c:if test="${not empty dto.ofile}">
				${dto.ofile}
				<a href="/download.do?ofile=${dto.ofile}&sfile=${dto.sfile}&idx=${dto.idx}">[다운로드]</a>
				</c:if>
			</td>
			<td>다운로드수</td>
			<td>${dto.downcount}</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<button type="button" onclick="location.href='/pass.do?mode=edit&idx=${param.idx}'">수정</button>
				<button type="button" onclick="location.href='/pass.do?mode=delete&idx=${param.idx}'">삭제</button>
				<button type="button" onclick="location.href='/list.do'">목록</button>
			</td>
		</tr>
	</table>
</body>
</html>