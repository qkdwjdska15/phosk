<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
	
<head>
	<title>home</title>

</head>
<header>
			<jsp:include page="./userheader.jsp"/>
</header>
<body>
		
	<h2><a href = "/userForm/cardinfo/cardinfoForm"  style=' color:blue;'>카드등록</a></h2>
	<h2><a href = "/userForm/user/register">회원가입</a></h2>
	<h2><a href = "/userForm/user/loginpage" style=' color:blue;'>로그인</a></h2>
	<h2><a href = "/userForm/user/userlist">회원정보조회</a></h2>
	<h2><a href = "/user/usermain">메인페이지</a></h2>
	<h2><a href = "/user/branchpaging">메인페이지+페이징</a></h2>
	<div class = "te">
		<c:if test="${login != null }">						
				<p>${login.member_id}님 환영 합니다.</p>
				<button type="button" onclick="location.href='/logout' ">로그아웃</button>
		</c:if>   	
	</div>
</body>
</html>
