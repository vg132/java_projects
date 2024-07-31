<div id="searchPage" data-theme="b" data-role="page" data-loadMethod="realTime.listStations();">
	<div data-role="header" data-theme="b">
		<h1>SL Realtid</h1>
		<a href="#searchPage" data-icon="search" class="ui-btn-right">Sök</a>
		<div data-role="navbar" data-iconpos="left">
			<ul>
				<li><a href="#realTimePage" data-iconpos="left" data-icon="search" class="ui-state-persist ui-btn-active">Realtid</a></li>
				<li><a href="#favoritesPage" data-icon="star" data-iconpos="left">Favoriter</a></li>
				<li><a href="#closeToMePage" data-icon="grid" data-iconpos="left">Nära mig</a></li>
			</ul>
		</div>
	</div>
	<div data-role="content" data-theme="b">
		<h2>Sök station</h2>
		<form>
			<div>
				<input id="stationSearchInput" placeholder="Sök station..." data-type="search" class="ui-input-text ui-body-d" />
			</div>
		</form>		
		<ul id="searchListView" data-loaded="false" data-role="listview" data-filter="false" data-inset="true" data-filter-placeholder="Sök station..." data-filter-theme="d" data-theme="d" data-divider-theme="d">
		</ul>
	</div>
	<%@ include file="/parts/footer.jsp" %>
</div>