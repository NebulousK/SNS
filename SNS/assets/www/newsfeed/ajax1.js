/**
 * ajax占쏙옙占�占쏙옙占쏙옙
 */

function getXMLHttpRequest(){
	if(window.ActiveXObject){
		try{
			return new ActiveXObject("MSXML2.XMLHTTP");
		}
		catch(e){
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	else if(window.XMLHttpRequest){
		return new XMLHttpRequest();
	}
	else{
		return null;
	}
}

function sendRequest(url, params, callback, method){
	XMLreq = getXMLHttpRequest();
	
	var httpMethod = method ? method : "GET"; // method占쏙옙 占쏙옙占쏙옙 占쌍다몌옙 method占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 , 占쏙옙占쏙옙 占쏙옙摸占�GET占쏙옙占쏙옙 占쏙옙占쏙옙
	if(httpMethod != "GET" && httpMethod != "POST"){
		httpMethod = "GET";
	}
	
	var httpParams = (params == null || params == "") ? null : params;
	
	var httpUrl = url;
	if(httpMethod == "GET" && httpParams != null){
		httpUrl = httpUrl + "?"+ httpParams;
	}
	
	XMLreq.onreadystatechange = callback;
	XMLreq.open(httpMethod, httpUrl, true);
	XMLreq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charaset=UTF-8");//POST占쏙옙占�占싹쏙옙 占쏙옙占쏙옙.
	XMLreq.send(httpMethod == "POST" ? httpParams : null);// A ? B : C  --> A占쏙옙 B, A占싣니몌옙 C
}

/*
function callback(){
	if(XMLreq.readyState == 4){
		if(XMLreq.status == 200){
			alert(XMLreq.responseText);
		}
		else{
			alert(XMLreq.status);
		}
	}
}
*/
