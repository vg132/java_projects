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
				<c:forEach var="productGroup" items="${products}">
					<div class="contentfloat">
						<b><center><a href="productlist.ijs?pgid=${productGroup.id}">${productGroup.name}</a></center></b><br/>
						<c:forEach var="product" items="${productGroup.items}">
							<div class="productInfo">
								<a href="productinfo.ijs?pid=${product.id}">
									<img src="<v:config key="baseurl" />${product.smallPicture}" alt="${product.name}"/><br/>
									${product.name}<br/>
									<fmt:formatNumber groupingUsed="true" maxFractionDigits="2">
										${product.price}
									</fmt:formatNumber>
									<v:config key="currency" />
								</a>
							</div>
						</c:forEach>
					</div>
				</c:forEach>			
			</div>
		</div>
		<p>
			&nbsp;
		</p>
	</body>
</html>