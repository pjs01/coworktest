<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC게시판</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<style>
	a{
		text-decoration:none; /* 링크 밑줄 없애기 */
	}
	.btn-primary{
		background-color:orange !important;
		border:1px solid orange;		
	}
	.btn-primary:hover{		
		border:1px solid gray;		
	}
</style>
</head>
<body>
	<div class="container">
		<h2 style="margin:50px 0;">목록</h2>
		
		<!-- 검색폼 ------------------------------------------------------>
		<form method="get">
			<table width="100%">
				<tr>
					<td align="center">
						<div class="input-group mb-3" style="width:50%;">
							<select name="searchField" class="form-select" style="max-width:150px">
								<option value="title" style="width:10px;">제목</option>
								<option value="content" style="width:10px;">내용</option>
							</select>
							<input type="text" name="searchWord" class="form-control">
							<input class="btn btn-primary btn-sm" type="submit" value="검색">
						</div>
						
					</td>
				</tr>
			</table>
		</form>	
		<!-- 검색폼.end -->
		
		<table class="table" border="1" width="90%">
			<tr align="center">
	            <th width="10%">번호</th>
	            <th width="*">제목</th>
	            <th width="15%">작성자</th>
	            <th width="10%">조회수</th>
	            <th width="15%">작성일</th>
	            <th width="8%">첨부</th>
	        </tr>
		<c:choose>
	  	<c:when test="${empty boardLists}">
	  		<tr>
			  	<td colspan="6" align="center">
			  		등록된 게시물이 없습니다.
			  	</td>
			</tr>
	  	</c:when>
	   	<c:otherwise>
		    <c:forEach items="${boardLists}" var="row" varStatus="loop">
		    <tr align="center">
		    	<td>${row.idx}</td>
		    	<td>
		    		<a href="/view.do?idx=${row.idx}">${row.title}</a>
		    	</td>
		    	<td>${row.name}</td>
		    	<td>${row.visitcount}</td>
		    	<td>${row.postdate}</td>
		    	<td>
		    	<c:if test="${not empty row.ofile}">
		    		<a href="/download.do?ofile=${row.ofile}&sfile=${row.sfile}&idx=${row.idx}">[Down]</a>
		    	</c:if>
		    	</td>	    
		    </tr>	    
		    </c:forEach>
		</c:otherwise>
	    </c:choose>
		</table>
		
		<!-- 페이지번호. 글쓰기 ------------------------------------------------>
		<table width="100%">
			<tr align="center">
				<td>
					${map.pagingImg}
				</td>
				<td width="100">
					<button type="button" class="btn btn-primary btn-sm" onclick="location.href='/write.do'">글쓰기</button>
				</td>
			</tr>
		</table>
	</div>

	
</body>
</html>