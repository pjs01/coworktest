<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC게시판</title>
<script type="text/javascript">
	function validateForm(form){
		if(form.name.value==""){
			alert("작성자를 입력하세요");
			form.name.focus();
			return false;
		}
		if(form.title.value==""){
			alert("제목을 입력하세요");
			form.title.focus();
			return false;
		}
		if(form.content.value==""){
			alert("내용을 입력하세요");
			form.content.focus();
			return false;
		}
		if(form.pass.value==""){
			alert("비밀번호를 입력하세요");
			form.pass.focus();
			return false;
		}
		//파일크기체크
	 	var inputFile = document.getElementById("file");
        var files = inputFile.files;       
        if(files[0].size>5*1024*1024 ){
        	alert("파일크기는 5Mbyte를 초과할 수 없습니다.");
            return false;
        }      
	}
</script>
</head>
<body>
	<h2>글쓰기</h2>
	<form name="writeFrm" method="post" action="/write.do" enctype="multipart/form-data" onsubmit="return validateForm(this);">
		<table border="1" width="90%">
			<tr>
				<td>작성자</td>
				<td>
					<input type="text" name="name" style="width:150px;">
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
					<input type="text" name="title" style="width:90%;">
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea type="text" name="content" rows="20" style="width:90%;"></textarea>
				</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td>
					<input type="file" name="ofile" id="file">
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="pass" style="width:100px;">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">등록</button>
					<button type="reset">취소</button>
					<button type="button" onclick="location.href='/list.do'">목록</button>
				</td>
				
			</tr>
		</table>
	</form>
</body>
</html>