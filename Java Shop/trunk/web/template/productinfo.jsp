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
				<div class="contentfloat">
					<div class="product_info_picture">
						<img src="<v:config key="baseurl" />${product.picture}" alt="${product.name}"/>
					</div>
					<div class="product_info_list">
						<b>Name:</b> ${product.name}<br/>
						<b>Product code:</b> ${product.id}<br/>
						<b>Format:</b> ${product.productGroupName}<br/>
						<b>Release date:</b> ${product.release}<br/>
						<b>Region:</b> ${product.regionName}<br/>
						<b>RRP:</b> <span class="rrp">${product.rrp} <v:config key="currency" /></span><br/>
						<b>Our Price:</b>
						<fmt:formatNumber groupingUsed="true" maxFractionDigits="2">
							${product.price}
						</fmt:formatNumber>
					  <v:config key="currency" /><br/>
						<c:if test="${customer!=null}">
							<b>Currency Converter:</b>
							<fmt:formatNumber groupingUsed="true" maxFractionDigits="2">
								<v:currency price="${product.price}"/>
							</fmt:formatNumber>
						 ${customer.currency}<br/>
						</c:if>
						<br/>
						<a href="addcart.ijs?id=${product.id}"><img src="images/icons/addtocart.png"/></a><br/><br/>
						<a href="addwishlist.ijs?id=${product.id}"><img src="images/icons/addtowishlist.png"/></a>
					</div>
				</div>
				<div class="contentfloat">
					<div class="product_description">
						<b>Description:</b> ${product.description}
					</div>
				</div>
			</div>
		</div>
		<p>
			&nbsp;
		</p>
	</body>
</html>