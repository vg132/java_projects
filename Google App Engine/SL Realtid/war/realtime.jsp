<div id="realTimePage" data-theme="b" data-role="page" data-loadMethod="realTime.getRealTimeData();">
	<div data-role="header" data-theme="b">
		<h1>SL Realtid</h1>
		<a href="#searchPage" data-icon="search" class="ui-btn-right">S�k</a>
		<div data-role="navbar" data-iconpos="left">
			<ul>
				<li><a href="#realTimePage" data-iconpos="left" data-icon="search" class="ui-state-persist ui-btn-active">Realtid</a></li>
				<li><a href="#favoritesPage" data-icon="star" data-iconpos="left">Favoriter</a></li>
				<li><a href="#closeToMePage" data-icon="grid" data-iconpos="left">N�ra mig</a></li>
			</ul>
		</div>
	</div>
	<div data-role="content" data-theme="b">
		<h2>N�rmaste avg�ngar fr�n <span id="stationName"></span></h2>
		<ul id="realTimeListView" data-role="listview" data-theme="d" data-inset="true">
		</ul>		
		<div id="saveSearch" data-role="collapsible" data-theme="b" data-content-theme="c">
   		<h3>Spara som favorit</h3>
   		<p>S�kningen sparad, du hittar den under fliken "Favoriter".</p>
		</div>
	</div>
	<%@ include file="/parts/footer.jsp" %>
</div>