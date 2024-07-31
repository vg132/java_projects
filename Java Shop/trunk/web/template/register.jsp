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
					<b>Register</b>
					<c:if test="${errormsg!=null}">
						<div class="error">
							<p>
								${errormsg}
							</p>
						</div>
					</c:if>
					</bloc::error>
					<form name="register" action="register.ijs" method="post">
						<input type="hidden" name="register" value="true"/>
						<div class="row">
							<span class="label">Email:</span>
							<span class="formw"><input type="text" size="25" name="email" value="${email}" /></span>
						</div>
						<div class="row">
							<span class="label">Password:</span>
							<span class="formw"><input type="password" size="25" name="password" /></span>
						</div>
						<div class="row">
							<span class="label">Name:</span>
							<span class="formw"><input type="text" size="25" name="name" value="${name}" /></span>
						</div>
						<div class="row">
							<span class="label">Address:</span>
							<span class="formw"><input type="text" size="25" name="address" value="${address}" /></span>
						</div>
						<div class="row">
							<span class="label">City:</span>
							<span class="formw"><input type="text" size="25" name="city" value="${city}" /></span>
						</div>
						<div class="row">
							<span class="label">State:</span>
							<span class="formw"><input type="text" size="25" name="state" value="${state}" /></span>
						</div>
						<div class="row">
							<span class="label">Post code:</span>
							<span class="formw"><input type="text" size="25" name="post_code" value="${post_code}" /></span>
						</div>
						<div class="row">
							<span class="label">Country:</span>
							<span class="formw">
								<select name="country">
									<c:forEach var="item" items="${countrys}">
										<c:choose>
											<c:when test="${item.id eq country}">
												<option value="${item.id}" selected>${item.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${item.id}">${item.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</span>
						</div>
						<div class="row">
							<span class="label">Currency:</span>
							<span class="formw">
								<select name="currency">
									<c:forEach var="item" items="${currency}">
										<c:choose>
											<c:when test="${item.key eq selectedCurrency}">
												<option value="${item.key}" selected>${item.key}</option>
											</c:when>
											<c:otherwise>
												<option value="${item.key}">${item.key}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</span>
						</div>
						<div class="row">
							<span class="label">&nbsp;</span>
							<span class="formw">
								<a href="javascript:document.register.submit();"><img src="images/icons/search.png" alt="login"/></a>
							</span>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>