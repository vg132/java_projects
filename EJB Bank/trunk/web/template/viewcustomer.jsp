<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>VG Software Bank</title>
	</head>
	<body>
		<h3>View <c:out value="${customer}"/>s accounts</h3>
		<hr/>
		<c:if test="${error!=null}">
			<font color="red"><c:out value="${error}"/></font>
			<hr/>
		</c:if>
		Add new account
		<form method="post" action="addaccount.bank">
			<input type="hidden" name="customer" value="<c:out value="${customer}"/>"/>
			Number: <input type="text" name="number" /><br/>
			<input type="submit" value="Add"/>
		</form>
		<hr/>
		<c:forEach var="account" items="${accounts}">
			<c:out value="${account.number}"/> - <c:out value="${account.balance}"/> [<a href="deleteaccount.bank?customer=<c:out value="${customer}"/>&number=<c:out value="${account.number}"/>">Delete</a>]<br/>
		</c:forEach>
	</body>
</html>