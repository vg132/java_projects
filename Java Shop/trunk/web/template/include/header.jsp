<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/fmt" prefix="fmt" %>

<div id="top">
	<table width="100%" border="0">
		<tr>
			<td align="left" valign="center">
				<div id="logo">
					<a href="http://www.vgsoftware.com/" title="VG Software Home">VG Software</a>
				</div>
			</td>
			<td align="right" valign="center">
				<table border="0">
					<tr>
						<td valign="center" align="left">
							<img src="images/icons/cart.png"/>
						</td>
						<td align="left">
							<a href="cart.ijs">Cart</a>
						</td>
						<td>
							&nbsp;
						</td>
						<td align="left">
							<img src="images/icons/wishlist.png"/>
						</td>
						<td align="left">
							<a href="wishlist.ijs">Wish list</a>
						</td>
					</tr>
					<tr>
						<td align="left">
							<img src="images/icons/signin.png"/>
						</td>
						<td align="left">
							<c:choose>
								<c:when test="${customer!=null}">
									<a href="account.ijs">My account</a>
								</c:when>
								<c:otherwise>
									<a href="login.ijs">Sign-in</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							&nbsp;
						</td>
						<td align="left">
							<img src="images/icons/information.png"/>
						</td>
						<td align="left">
							<a href="information.php">Information</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>