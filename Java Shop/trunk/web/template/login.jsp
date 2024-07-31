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
				<div class="form">
					<b>Signin</b>
					<c:if test="${errormsg!=null}">
						<div class="error">
							<p>
								${errormsg}
							</p>
						</div>
					</c:if>
					<form name="signin" action="login.ijs" method="post">
						<div class="row">
							<span class="label">Email:</span>
							<span class="formw"><input type="text" size="25" name="email" value="${email}" /></span>
						</div>
						<div class="row">
							<span class="label">Password:</span>
							<span class="formw"><input type="password" size="25" name="password" /></span>
						</div>
						<div class="row">
							<span class="label">Save login:</span>
							<span class="formw"><input type="checkbox" name="savelogin"/></span>
						</div>
						<div class="row">
							<span class="label">&nbsp;</span>
							<span class="formw">
								<a href="javascript:document.signin.submit();"><img src="images/icons/search.png" alt="login"/></a>
							</span>
						</div>
						<div class="row">
							Don't have an account? <a href="preregister.ijs">Click here</a>.<br/>
							Can't remember your password? <a href="lostpassword.php">Click here</a>.
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>