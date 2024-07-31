<div id="realTimePage" data-theme="b" data-role="page" data-loadMethod="realTime.getRealTimeData();">
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
		<h2>Närmaste avgångar från <span id="stationName"></span></h2>
		<ul id="realTimeListView" data-role="listview" data-theme="d" data-inset="true">
		</ul>		
		<div id="saveSearch" data-role="collapsible" data-theme="b" data-content-theme="c">
   		<h3>Spara som favorit</h3>
   		<p>Sökningen sparad, du hittar den under fliken "Favoriter".</p>
		</div>
	</div>
	<%@ include file="/parts/footer.jsp" %>
</div>