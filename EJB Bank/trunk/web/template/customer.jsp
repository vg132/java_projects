<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>
<c:if test="${type!='customer'}">
	<jsp:forward page="index.jsp" />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>VG Software Bank</title>
	</head>
	<body>
		<h3>Welcome <c:out value="${customer}"/></h3>
		<hr/>
		<c:if test="${error!=null}">
			<font color="red"><c:out value="${error}"/></font>
			<hr/>
		</c:if>
		Your accounts
		<p>
			<c:forEach var="account" items="${accounts}">
				<c:out value="${account.number}"/> - <c:out value="${account.balance}"/>
			</c:forEach>
		</p>
		<hr/>
		Transfer
		<form method="post" action="transfer.bank">
			From Account: 
			<select name="from">
				<c:forEach var="account" items="${accounts}">
					<option value="<c:out value="${account.number}"/>"><c:out value="${account.number}"/></option>
				</c:forEach>
			</select><br/>
			To Account: <input type="text" name="to" /><br/>
			Amount: <input type="text" name="amount"/><br/>
			<input type="submit" value="Transfer"/>
		</form>
		<hr/>
		Deposit
		<form method="post" action="deposit.bank">
			Account: 
			<select name="to">
				<c:forEach var="account" items="${accounts}">
					<option value="<c:out value="${account.number}"/>"><c:out value="${account.number}"/></option>
				</c:forEach>
			</select><br/>
			Amount: <input type="text" name="amount"/><br/>
			<input type="submit" value="Deposit"/>
		</form>		
	</body>
</html>