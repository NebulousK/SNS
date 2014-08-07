Ext.ns("message");
Ext.ns("message.panel_message");

var dear;

message.init = function(){    
    Ext.regModel('message', {
    	fields: ['content','sender','no','day','dear','photo']
    });

    var getmessageList2 = function()
    {
        Ext.Ajax.request({
            url: common_url + '/mmemseage.me?id='+ common_id + '&id2=' + dear,
            success: function(response, opts) {
                console.log(response.responseText);
                var JsonData = JSON.parse(response.responseText);
                console.log(JsonData);
                if(JsonData.data.err == ""){
                	setmessage2(JsonData.data.message_list);
                }
                else{
                    alert(JsonData.data.err);
                }                
            }
        });         
    };
    
    var getmessageList3 = function()
    {
        Ext.Ajax.request({
            url: common_url + '/mmemseage.me?id='+ common_id + '&id2=' + dear,
            success: function(response, opts) {
                console.log(response.responseText);
                var JsonData = JSON.parse(response.responseText);
                console.log(JsonData);
                if(JsonData.data.err == ""){
                	setmessage3(JsonData.data.message_list);
                	setTimeout(function() {getmessageList3();}, 2000);
                }
                else{
                    alert(JsonData.data.err);
                }                
            }
        });         
    };
    
    var messageStore = new Ext.data.Store({
        model :'message',               
        data:[
                // 공백
        ],
    });     
    
    function setmessage(Jv_data) {
    	messageStore.add(Jv_data);
    };
   
    var messageList = new Ext.XTemplate(//'+ common_id +'
    		'<tpl for=".">',
    		'<tpl if="sender==\''+ common_id +'\'">',
    	    '<div id="memo_area" class="me">',
    	    '</tpl>',
    	    '<tpl if="dear==\''+ common_id +'\'">',
    	    '<div id="memo_area" class="you">',
    	    '</tpl>',
    		'<div class="nick_area">',
    			'<table border="0" cellpadding="5" cellspacing="0">',
    				'<tr>',
    					'<td align="right"><img class="hu_icon" src="'+ common_url +'/profile/{photo}" width="25" height="25" style="width:25px;height:25px;" /></td>',
    					'<td width="5"></td>',
    					'<td align="left" valign="center">',
    											'{sender}',
    											'<br>',
    						'<span class="date">',
    							'{day}',
    						'</span>',
    					'</td>',
    				'</tr>',
    			'</table>',
    		'</div>',
    					'<div class="tri"> </div>',
    				'<span class="memotext">',
    							'{content}',
    					'</span>',
    					'</div>',
    					'</tpl>'
    	);
    
    var dataview = new Ext.DataView({
    	id:'myview',
        store: messageStore,
        tpl: messageList,
        height: '100%',
        autoScroll: true,
        emptyText: '쪽지가 없습니다.',
        itemSelector:'div.thumb-wrap',
    });
    
    function setmessage2(Jv_data) {
    	messageStore.removeAll();
    	messageStore.add(Jv_data);
    	var aa = dataview.el.dom.scrollHeight - dataview.scroller.size.height;
    	dataview.scroller.moveTo(0, -aa);
    	//dataview.scroller.setOffset({x: 0, y: -aa}, false);
    	//alert(dataview.scroller.getOffset());
    	//alert(dataview.el.dom.scrollHeight);
    	//alert(dataview.scroller.size.height);
    	//alert(dataview.scroller.initialRegion.bottom);
    };
    
    function setmessage3(Jv_data) {
    	messageStore.removeAll();
    	messageStore.add(Jv_data);
    };
    
    setTimeout(function() {getmessageList3();}, 2000);
    
    message.panel_message = new Ext.Panel({
        useCurrentLocation: true,
        fullscreen: true,
        height:'100%',
        cardSwitchAnimation:"cube",
        setUserId:function(user_id)
        {
            this.input_user_id = user_id;
            dear = user_id;
        },
        getmessageList:function()
        {
            Ext.Ajax.request({
                url: common_url + '/mmemseage.me?id='+ common_id + '&id2=' + this.input_user_id,
                success: function(response, opts) {
                    console.log(response.responseText);
                    var JsonData = JSON.parse(response.responseText);
                    console.log(JsonData);
                    if(JsonData.data.err == ""){
                    	setmessage(JsonData.data.message_list);
                    	var aa = dataview.el.dom.scrollHeight - dataview.scroller.size.height;
                    	dataview.scroller.moveTo(0, -aa);
                    }
                    else{
                        alert(JsonData.data.err);
                    }                
                }
            });         
        },
        dockedItems :[{
        	id:'main.toolbar',
            dock: 'bottom',
            xtype: 'toolbar',
            layout: {
            	type: 'hbox',
            	pack: 'center',
        	},
            items: [{   
            	 	xtype:'textfield',
            	 	id:'message.comment',
            	 	autoCapitalisze:true,
            	 	useClearIcon:false,
            	 	width : '80%',
                },                
                { 
                	xtype:'button',
                    ui: 'decline-round',
                    name:'button_login',
                    text: '전송',
                    handler: function(){
                    	 Ext.Ajax.request({
                             url: common_url + '/mmemseagesend.me?dear=' + dear + '&content='  +  Ext.getCmp("message.comment").getValue() + '&photo=' + common_photo + '&id=' + common_id,
                             success: function(response, opts) {
                                alert("전송 완료");  
                                getmessageList2();
                             }
                         });  
                    } 
                }
            ]
        },
        {
            dock: 'top',
            xtype: 'toolbar',
            title: '쪽지함',
            items: [
            {
                cls:'home',
                iconMask: true,
                iconCls:'home',
                text: '',
                handler: function(btn,event){
                	 	friend.init();
                	 	friend.panel_friend.getfriendList();
                	 	main.MainPanel.layout.setActiveItem(friend.panel_friend); 
                    }      
                }
            ]
        }], 
        items: dataview, 
    });
}; 