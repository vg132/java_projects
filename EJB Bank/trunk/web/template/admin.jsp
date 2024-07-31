<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>VG Software Bank</title>
	</head>
	<body>
		<h3>Admin</h3>
		<hr/>
		<c:if test="${error!=null}">
			<font color="red"><c:out value="${error}"/></font>
			<hr/>
		</c:if>
		Add new customer
		<form method="post" action="addcustomer.bank">
			Name: <input type="text" name="name" /><br/>
			Password: <input type="password" name="password"/><br/>
			<input type="submit" value="Add"/>
		</form>
		<hr/>
			Bank Balance: <b><c:out value="${bankbalance}"/></b>
		<hr/>
		<c:forEach var="customer" items="${customers}">
			<a href="viewcustomer.bank?customer=<c:out value="${customer}"/>"><c:out value="${customer}"/></a> [<a href="deletecustomer.bank?customer=<c:out value="${customer}"/>">Delete</a>]<br/>
		</c:forEach>
	</body>
</html>