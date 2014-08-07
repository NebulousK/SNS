/**
 * This file sets application-wide settings and launches the application when everything has
 * been loaded onto the page. By default we just render the applications Viewport inside the
 * launch method (see app/views/Viewport.js).
 */ 
var Request={
	getParameter:function(parameterName){
		var _queryString   = null;
		var _parameter     = null;
		var _pair          = null;
		var _value         = null;
		var _parameterName = null;
		_queryString = window.location.search.substring(1);
		_parameter   = _queryString.split("&");
		if( _parameter.length == 0 || _parameter == null){
			alert('parameter not found');
		}else{
			for(var i=0; i<_parameter.length; i++){
				_pair          = _parameter[i].split('=');
				_parameterName = _pair[0];
				_value         = _pair[1];
				if(_parameterName == parameterName){
					return _value;	
				} 
			}//end-for
		}
	}//end-getParameter
}
var common_url = "http://54.178.192.82/homepage"; //"http://192.168.10.31/homepage";
var common_no = Request.getParameter("no");

Gallery = new Ext.Application({
    name: "Gallery",
    defaultUrl: 'Album/index',
    launch: function() {
        this.viewport = new Gallery.Viewport();
    }
});
