<div id="closeToMePage" data-theme="b" data-role="page" data-loadMethod="realTime.listCloseToMe();">
	<div data-role="header" data-theme="b">
		<h1>SL Realtid</h1>
		<a href="#searchPage" data-icon="search" class="ui-btn-right">Sök</a>
		<div data-role="navbar" data-iconpos="left">
			<ul>
				<li><a href="#realTimePage" data-icon="search">Realtid</a></li>
				<li><a href="#favoritesPage" data-icon="star">Favoriter</a></li>
				<li><a href="#closeToMePage" data-icon="grid" class="ui-state-persist ui-btn-active">Nära mig</a></li>
			</ul>
		</div>
	</div>
	<div data-role="content" data-theme="b">
		<h2>Nära mig</h2>
		<ul id="closeToMeListView" data-role="listview" data-theme="d" data-inset="true">
		</ul>
	</div>
	<%@ include file="/parts/footer.jsp" %>
</div>