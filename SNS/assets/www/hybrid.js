
		function callPhone(phoneNum)
		{
			
			if(window.NativeBridge == undefined)
			{
				alert('모바일 단말과 연결되어 있지 않습니다.');
				return;
			}
			window.NativeBridge.callPhone(phoneNum);
		}
		function callSms(phoneNum, smsCont)
		{ 			
			if(window.NativeBridge == undefined)
			{
				alert('모바일 단말과 연결되어 있지 않습니다.');
				return;
			}
			window.NativeBridge.callSms(phoneNum, smsCont);
		}
		function callCamera()
		{			
			if(window.NativeBridge == undefined)
			{
				alert('모바일 단말과 연결되어 있지 않습니다.');
				return;
			}
			window.NativeBridge.callCamera();
		}
		function callNetworkState(callbackFunc)
		{			
			if(window.NativeBridge == undefined)
			{
				alert('모바일 단말과 연결되어 있지 않습니다.');
				return;
			}
			window.NativeBridge.callNetworkState(callbackFunc);
			// callbackFunc(networkState);
		}	
		function receiveNetworkState(networkState)
		{
			alert(networkState);
		}
		
		function callLocationPos(callbackFunc)
		{			
			if(window.NativeBridge == undefined)
			{
				alert('모바일 단말과 연결되어 있지 않습니다.');
				return;
			}
			window.NativeBridge.callLocationPos(callbackFunc);
			// callbackFunc(lng, lat);
		}
		function receiveLocationPos(lng, lat)
		{
			alert(lng + "," +  lat);
		}		
		
		function callMoviePlayer(movieUrl)
		{			
			if(window.NativeBridge == undefined) 
			{
				alert('모바일 단말과 연결되어 있지 않습니다.');
				return;
			}
			window.NativeBridge.callMoviePlayer(movieUrl);
		}	
		
		function callFileUpload(httpaddr, filename)
		{
			if(window.NativeBridge == undefined) 
			{
				alert('모바일 단말과 연결되어 있지 않습니다.');
				return;
			}
			window.NativeBridge.callFileUpload(httpaddr, filename);		
		
		}	