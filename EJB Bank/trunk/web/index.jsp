<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>
<html>
	<head>
	</head>
	<body>
		<c:choose>
			<c:when test="${type==null}">
				<jsp:forward page="login.jsp" />
			</c:when>
			<c:when test="${type=='admin'}">
				<jsp:forward page="adminindex.bank" />
			</c:when>
			<c:when test="${type=='customer'}">
				<jsp:forward page="customerindex.bank" />
			</c:when>
		</c:choose>
	</body>
</html>