Ext.ns("callme");
Ext.ns("callme.panel_callme");

callme.init = function(){
    var CallmeList;
    var CallmeStore;
    
    Ext.regModel('callme', {
        fields: ['photo','name','addr']
    });     

    CallmeStore = new Ext.data.Store({
        model :'callme',               
        data:[
                // 공백
        ],
    });     
    
    CallmeList = new Ext.List({
        id:'CallmeList',
        store:CallmeStore,
        height:'100%',
        blockRefresh:true,
        onItemDisclosure: {
            handler: function(record, btn, index) {
            	main.MainPanel.layout.setActiveItem(mcall.panel_mcall);
            	mcall.panel_mcall.setUserId(record.get('name'));
            	mcall.panel_mcall.getUserInfo();
            	//mcall.panel_mcall.setPersonFields();
            }
        },
        itemTpl:'<div><div style="float: left"><img src="'+ common_url +'/profile/{photo}" width="50px" height="50px"/></div><div style="float: left"><div style="float: left"><strong>이름 : {name}</strong></div><div style="clear: both; height:2px"></div><div style="float: left"> 사는곳 : {addr} </div></div></div>',
    });        

    function setCallmeList(Jv_data) {
        CallmeStore = new Ext.data.Store({
            model :'callme',
            data:Jv_data,
        });
        Ext.getCmp('CallmeList').bindStore(CallmeStore);    
    };
               
    callme.panel_callme = new Ext.Panel({
        useCurrentLocation: true,
        fullscreen: true,
        cardSwitchAnimation:"cube",
        
        getCallmeList:function()
        {
            Ext.Ajax.request({
                url: common_url + '/mcall.some?no='+ common_no,
                success: function(response, opts) {
                    console.log(response.responseText);
                    var JsonData = JSON.parse(response.responseText);
                    console.log(JsonData);
                    if(JsonData.data.err == "")
                    {
                    	setCallmeList(JsonData.data.callme_list);
                    }
                    else
                    {
                        alert(JsonData.data.err);
                    }                
                }
            });         
        },
        items:
        [{
            xtype: 'fieldset',
            instructions: '상세정보를 보고 싶으시면 리스트를 클릭하십시요',
            defaults: {                
                labelAlign: 'left' }
            ,
            items:CallmeList
        }]
    });
};   

