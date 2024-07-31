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
									${item.quantity}
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
				</table>
			</div>
			<div id="content">
				<b>Shipping Address</b><br/><br/>
				<b>Name:</b> ${customer.name}<br/>
				<b>Address:</b> ${customer.address}<br/>
				<b>City:</b> ${customer.city}<br/>
				<b>Post code:</b> ${customer.postCode}<br/>
				<b>State:</b> ${customer.state}<br/>
				<b>Country:</b> ${customer.country}
				<p>
					<a href="sendorder.ijs">Send Order</a>
				</p>
			</div>
		</div>
		<p>
			&nbsp;
		</p>
	</body>
</html>