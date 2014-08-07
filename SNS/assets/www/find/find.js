Ext.ns("list");
Ext.ns("list.panel_list");

list.init = function(){
    var FriendList;
    var FriendStore;
    
    Ext.regModel('friend', {
        fields: ['photo','name','no','addr']
    });     

    FriendStore = new Ext.data.Store({
        model :'friend',               
        data:[],
    });      
    
    FriendList = new Ext.List({
		id:'FriendList',
        store:FriendStore,                      
        onItemDisclosure: {
            handler: function(record, btn, index) {  
                main.MainPanel.layout.setActiveItem(detail.panel_detail); 
                detail.panel_detail.setUserId(record.get('no'));
                detail.panel_detail.getUserInfo();
            }
        },
        itemTpl:'<div><div style="float: left"><img src="'+ common_url +'/profile/{photo}" width="50px" height="50px"/></div><div style="float: left"><div style="float: left"><strong>이름 : {name}</strong></div><div style="clear: both; height:2px"></div><div style="float: left"> 사는곳 : {addr} </div></div></div>',
    });    
          
    function setFriendList(Jv_data) {
        FriendStore = new Ext.data.Store({
            model :'friend',
            data:Jv_data,
        });
        Ext.getCmp('FriendList').bindStore(FriendStore);    
    };
               
    list.panel_list = new Ext.Panel({
        fullscreen: true,               
        scroll:'vertical',
        cardSwitchAnimation:"cube",
        scroll: 'vertical',
        items:
        [{
            xtype: 'fieldset',
            title: '회원 검색',
            defaults: {
                required: true,
                labelAlign: 'left' }
            ,
            items:[{
                layout: {
                    type: 'hbox',
                    pack: 'center',
                    
                },  
                items:[{
                    xtype:'textfield',
                    id:'user_name',
                    width:'50%',
                    placeHolder:'회원 이름 입력',
                    autoCapitalisze:true,
                    useClearIcon:false
                                
                },{
                    xtype:'spacer',
                    width:'10%',
                },{
                    xtype:'button',
                    ui: 'decline-round',
                    name:'button_search',
                    width:'30%',
                    handler:function(){                 
                    Ext.Ajax.request({
                        url: common_url  + '/mfind.friend?find=' + encodeURIComponent(Ext.getCmp("user_name").getValue()) + '&name=happyhiphop&no=532',
                        success: function(response, opts) {
                            console.log(response.responseText);
                            var JsonData = JSON.parse(response.responseText);
                            console.log(JsonData);
                            if(JsonData.data.err == "")
                            {
                                setFriendList(JsonData.data.friend_list);
                            }
                            else
                            {
                                alert(JsonData.data.err);
                            }
                        }
                    });                     
                    },                      
                    text:'조회'
                }]                                      
            }]
        },
        {
            xtype: 'fieldset',
            instructions: '상세정보를 보고 싶으시면 리스트를 클릭하십시요',
            defaults: {
                labelAlign: 'left' },
            items:FriendList
        }]
    });       
}
   