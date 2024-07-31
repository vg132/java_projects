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
				<form action="updatecart.ijs" method="post" name="cart">
					<table width="90%">
						<tr class="even">
							<th align="left">
								Title
							</th>
							<th align="left" width="50">
								Quantity
							</th>
							<th align="left">
								Cost
							</th>
							<th align="left">
								Total
							</th>
						</tr>
						<c:forEach var="item" items="${cart.items}" varStatus="status">
							<tr class="<v:colorSwitch odd="odd" even="even"/>">
								<td>
									${item.name}
								</td>
								<td width="50">
									<input type="text" name="quantity_${item.id}" value="${item.quantity}" size="3"/>
								</td>
								<td>
									<fmt:formatNumber groupingUsed="true" maxFractionDigits="2">
										${item.price}
									</fmt:formatNumber>
								</td>
								<td>
									<fmt:formatNumber groupingUsed="true" maxFractionDigits="2">
										<c:out value="${item.price *item.quantity}"/>
									</fmt:formatNumber>
								</td>
							</tr>
						</c:forEach>
						<tr class="<v:colorSwitch odd="odd" even="even"/>">
						<td colspan="3" align="right">
							<b>Total price:</b>
						</td>
						<td>
							<b>
								<fmt:formatNumber groupingUsed="true" maxFractionDigits="2">
									${cart.totalPrice}
								</fmt:formatNumber>
								<v:config key="currency" />
							</b>
						</td>
					</tr>
						<c:if test="${customer!=null}">
							<tr class="<v:colorSwitch odd="odd" even="even"/>">
								<td colspan="3">
								</td>
								<td>
									<fmt:formatNumber groupingUsed="true" maxFractionDigits="2">
										<v:currency price="${cart.totalPrice}"/>
									</fmt:formatNumber>
									${customer.currency}
								</td>
							</tr>
						</c:if>
						<tr>
							<td colspan="4" align="center">
								<center><a href="index.ijs"><img src="images/icons/continueshopping.png"></a>&nbsp;&nbsp;&nbsp;<a href="javascript:document.cart.submit();"><img src="images/icons/updatecart.png"></a>&nbsp;&nbsp;&nbsp;<a href="checkout.ijs"><img src="images/icons/checkout.png"></a></center>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<p>
			&nbsp;
		</p>
	</body>
</html>