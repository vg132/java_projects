(function($) {

	$.vStorage = function(type) {
		var storage = window[type + 'Storage'];
		return {
			set : function(key, value) {
				if (storage != null) {
					storage.setItem(key, value);
				}
			},
			get : function(key, defaultValue) {
				var item = null;
				if (storage != null) {
					item = storage.getItem(key);
				}
				return item != null ? item : defaultValue;
			},
			remove : function(key) {
				if (storage != null) {
					storage.removeItem(key);
				}
			},
			clear : function() {
				if (storage != null) {
					storage.clear();
				}
			}
		};
	};
})(jQuery);