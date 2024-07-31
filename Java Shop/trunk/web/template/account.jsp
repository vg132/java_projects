<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.vgsoftware.com/taglibs/vlib" prefix="v" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="include/head.jsp"/>
	</head>
	<body>
		<div id="container">
			<jsp:include page="include/header.jsp"/>
			<jsp:include page="include/menu.jsp"/>
			<div id="content">
				<h3>Your account</h3>
				<ul>
					<li><a href="viewaccount.ijs">View account details</a></li>
					<li><a href="orders.ijs">View orders</a></li>
					<li><a href="logout.ijs">Sign-out</a></li>
				</ul>
			</div>
		</div>
	</body>
</html>