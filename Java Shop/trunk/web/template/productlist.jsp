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
				<div class="product_list_container">
					<c:forEach var="product" items="${products}">
						<div class="product_list_row">
							<div class="product_list_left">
								<a href="productinfo.ijs?pid=${product.id}">${product.name}</a><br/>
								<b>Release date:</b> ${product.release}<br/>
								<b>Our Price:</b>
								<fmt:formatNumber groupingUsed="true" maxFractionDigits="2">
									${product.price}
								</fmt:formatNumber>
								<v:config key="currency" />
							</div>
							<div class="product_list_right">
								<a href="addcart.ijs?id=${product.id}"><img src="images/icons/addtocart.png"/></a><br/>
								<c:choose>
									<c:when test="${wishlist!=null}">
										<a href="removewishlist.ijs?id=${product.id}"><img src="images/icons/remove.png"/></a>
									</c:when>
									<c:otherwise>
										<a href="addwishlist.ijs?id=${product.id}"><img src="images/icons/addtowishlist.png"/></a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<p>
				&nbsp;
		</p>
	</body>
</html>