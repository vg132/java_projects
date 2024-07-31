<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/fmt" prefix="fmt" %>
<div id="leftnav">
	<div class="floatright">
		<b>Search</b>
		<form action="search.ijs" method="post" name="search">
			<table cellspacing="0">
				<tr>
					<td colspan="2" align="left">
						<input type="text" name="search_term" size="19"/>
					</td>
				</tr>
				<tr>
					<td align="left">
						<select name="product">
							<option value="-1">All Platforms</option>
							<c:forEach var="item" items="${menuProductGroups}">
								<option value="${item.id}"><c:out value="${item.name}"/></option>
							</c:forEach>
						</select>
					</td>
					<td align="right">
						<a href="javascript:document.search.submit();"><img src="images/icons/search.png"/></a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="floatright">
		<b>Select platform</b><br/>
		<c:forEach var="item" items="${menuProductGroups}">
			<a href="productlist.ijs?pgid=${item.id}">${item.name}</a><br/>
		</c:forEach>
	</div>
	<c:if test="${categorys!=null}">
		<div class="floatright">
			<a href="productlist.ijs?pgid=${pgid}">All</a><br/>
			<c:forEach var="category" items="${categorys}">
				<a href="productlist.ijs?pgid=${pgid}&cid=${category.id}">${category.name}</a><br/>
			</c:forEach>
		</div>
	</c:if>
</div>