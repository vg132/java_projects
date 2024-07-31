var realTime = realTime || {};

$(function() {
	realTime.init();
});

realTime = {

	init : function() {
	},

	listStations : function() {
		if ($('#searchListView').data('loaded') == false) {
			$.mobile.showPageLoadingMsg();
			var stationData = JSON.parse($.vStorage('local').get('stationData_v2', null));
			if (stationData == null) {
				$.ajax({
					url : '/search',
					type : 'POST',
					contentType : 'application/x-www-form-urlencoded; charset=utf-8',
					success : function(data) {
						$('#searchListView').empty();
						$.vStorage('local').set('stationData_v2', JSON.stringify(data));
						realTime.loadStationData(data);
						$('#searchListView').data('loaded', true);
					},
					complete : function() {
						$('#searchListView').listview('refresh');
						$.mobile.hidePageLoadingMsg();
					}
				});
			} else {
				realTime.loadStationData(stationData);
				$('#searchListView').data('loaded', true);
				$('#searchListView').listview('refresh');
				$.mobile.hidePageLoadingMsg();
			}
		}
	},

	loadStationData : function(data) {
		var currentLetter = '';
		$.each(data, function() {
			if (currentLetter != this.name.toUpperCase()[0]) {
				currentLetter = this.name.toUpperCase()[0];
				$('#searchListView').append('<li data-role="list-divider">' + currentLetter + '</li>');
			}
			$('#searchListView').append('<li><a class="closeToMeLink" href="#realTimePage" data-siteId="' + this.siteId + '" data-typeId="' + this.transportationType + '"><img src="/images/type_' + this.transportationType + '.png" class="ui-li-icon" />' + this.name + '</a></li>');
		});
	},

	listFavorites : function() {
		$.mobile.showPageLoadingMsg();
		var value = $.vStorage('local').get('favorites', null)
		if (value != null) {
			var items = value.split('|');
			$('#favoritesListView').empty();
			for (i = 0; i < items.length; i += 3) {
				$('#favoritesListView').append('<li><a class="closeToMeLink" href="#realTimePage" data-siteId="' + items[i] + '" data-typeId="' + items[i + 1] + '"><img src="/images/type_' + items[i + 1] + '.png" class="ui-li-icon" />' + items[i + 2] + '</a></li>');
			}
			$('#favoritesListView').listview('refresh');
		}
		$.mobile.hidePageLoadingMsg();
	},

	listCloseToMe : function() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(realTime.geoSuccess, realTime.geoError);
		} else {
			error('not supported');
		}
	},

	geoSuccess : function(position) {
		$.mobile.showPageLoadingMsg();
		$.ajax({
			url : '/closeToMe',
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			data : {
				lat : position.coords.latitude,
				lng : position.coords.longitude
			},
			success : function(data) {
				$('#closeToMeListView').empty();
				$.each(data, function() {
					$('#closeToMeListView').append('<li><a class="closeToMeLink" href="#realTimePage" data-siteId="' + this.siteId + '" data-typeId="' + this.transportationType + '"><img src="/images/type_' + this.transportationType + '.png" class="ui-li-icon" />' + this.name + '</a></li>');
				});
			},
			error : function(response, error) {
				$('#closeToMeListView').html('Unable to load search results.');
			},
			complete : function() {
				$('#closeToMeListView').listview('refresh');
				$.mobile.hidePageLoadingMsg();
			}
		});
	},

	getRealTimeData : function() {
		var siteId = $('#realTimePage').data('siteid');
		var typeId = $('#realTimePage').data('typeid');

		if (siteId == null || typeId == null) {
			siteId = $.vStorage('local').get('lastSiteId', null);
			typeId = $.vStorage('local').get('lastTransportationType', null);
			if (siteId == null || typeId == null) {
				return;
			}
		}
		$.mobile.showPageLoadingMsg();
		$.ajax({
			url : '/realTimeService',
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			data : {
				siteId : siteId,
				typeId : typeId
			},
			success : function(data) {
				if (data != null) {
					$('#realTimePage').data('siteid', data.station.siteId);
					$('#realTimePage').data('typeid', data.station.transportationType);
					$('#realTimePage').data('name', data.station.name);
					$.vStorage('local').set('lastSiteId', data.station.siteId);
					$.vStorage('local').set('lastTransportationType', data.station.transportationType);
					$('#stationName').text(data.station.name);
					$('#realTimeListView').empty();
					if (data.station.transportationType == 3) {
						$.each(data.departures, function() {
							$('#realTimeListView').append('<li>' + this.destination + '</li>');
						});
					} else {
						var currentDirection = 0;
						$.each(data.departures, function() {
							if (currentDirection != this.direction) {
								if (this.direction == 1) {
									$('#realTimeListView').append('<li data-role="list-divider">Södergående Tåg</li>');
								} else {
									$('#realTimeListView').append('<li data-role="list-divider">Norrgående Tåg</li>');
								}
								currentDirection = this.direction;
							}
							$('#realTimeListView').append('<li>' + this.destination + ' - ' + this.displayTime + '</li>');
						});
					}
				}
			},
			complete : function() {
				$('#realTimeListView').listview('refresh');
				$.mobile.hidePageLoadingMsg();
			}
		});
	},

	geoError : function(error) {
		console.log('Error');
	}
};

$(document).bind('pagechange', function(event, data) {
	eval($(data.toPage).data('loadmethod'));
});

$('.closeToMeLink').live('click', function() {
	$('#realTimePage').data('typeid', $(this).data('typeid'));
	$('#realTimePage').data('siteid', $(this).data('siteid'));
});

$('#stationSearchInput').live('keyup', function(){
	var value = $('#stationSearchInput').val().toLowerCase();
	if(value=='' || value.length>2) {
		$('ul#searchListView').hide();
		$('ul#searchListView li').each(function(){
			var station = $('div a',$(this)).text().toLowerCase();
			if(station.indexOf(value)==0) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
		$('ul#searchListView').show();
	}
});

$('#saveSearch').live('expand', function() {
	var value = $.vStorage('local').get('favorites', '')

	var siteId = $('#realTimePage').data('siteid');
	var typeId = $('#realTimePage').data('typeid');
	var name = $('#realTimePage').data('name');

	var items = value.split('|');
	for (i = 0; i < items.length; i += 3) {
		if (items[i] == siteId && items[i + 1] == typeId) {
			return;
		}
	}
	if (value.length != 0) {
		value += '|';
	}
	value += siteId + '|' + typeId + '|' + name;
	$.vStorage('local').set('favorites', value);
});