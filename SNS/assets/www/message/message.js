Ext.ns("friend");
Ext.ns("friend.panel_friend");

friend.init = function(){
    var friendList;
    var friendStore;
    
    Ext.regModel('friend', {
        fields: ['photo','id','no']
    });     

    friendStore = new Ext.data.Store({
        model :'friend',               
        data:[
                // 공백
        ],
    });     
    
    friendList = new Ext.List({
        id:'friendList',
        store:friendStore,
        height:'100%',
        blockRefresh:true,
        onItemDisclosure: {
            handler: function(record, btn, index) {	
            	message.init();
            	message.panel_message.setUserId(record.get('id'));
            	message.panel_message.getmessageList();
            	main.MainPanel.layout.setActiveItem(message.panel_message);
            	message.panel_message.setActiveItem(message.panel_message.messageList2);
            }
        },
        itemTpl:'<div style="float: left"><input type="hidden" name="no" value="{no}"/><img src="{photo}" width="50px" height="50px" /></div><div style="float: left; height:50px;line-height:50px;">&nbsp;&nbsp;&nbsp;<strong>이름 : {id}</strong></div>',
    });        

    function setfriendList(Jv_data) {
        friendStore = new Ext.data.Store({
            model :'friend',
            data: Jv_data,
        });
        Ext.getCmp('friendList').bindStore(friendStore);    
    };
               
    friend.panel_friend = new Ext.Panel({
        useCurrentLocation: true,
        fullscreen: true,
        cardSwitchAnimation:"cube",
        
        getfriendList:function()
        {
            Ext.Ajax.request({
                url: common_url + '/mList.friend?id='+ common_id,
                success: function(response, opts) {
                    console.log(response.responseText);
                    var JsonData = JSON.parse(response.responseText);
                    console.log(JsonData);
                    if(JsonData.data.err == "")
                    {
                    	setfriendList(JsonData.data.friend_list);
                    }
                    else
                    {
                        alert(JsonData.data.err);
                    }                
                }
            });         
        },
        items: friendList
    });
};   



