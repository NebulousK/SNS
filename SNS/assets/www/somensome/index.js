Ext.ns("main");
Ext.ns("main.MainPanel");


Ext.setup({
    icon: 'icon.png',
    glossOnIcon: false,
    tabletStartupScreen: 'tablet_startup.png',
    phoneStartupScreen: 'phone_startup.png',
    onReady: function() {
        main.initMainPanel();
    	menu.init();                    	
        main.MainPanel.layout.setActiveItem(menu.panel_menu, "cube"); 
    }
});

main.initMainPanel = function()
{
    main.MainPanel = new Ext.Panel({
    	fullscreen: true,
        id:'MainPanel',
        cardSwitchAnimation:"cube",
        layout:'card', 
        
        dockedItems :[{
        	id:'main.toolbar',
            dock: 'bottom',
            xtype: 'toolbar',
            scrollable:true,
            layout: {
            	type: 'hbox',
            	pack: 'center',
        	},
            items: [
                { 
                    cls:'Login',
                    text: '<div align="center"><img src= "'+ local_img + 'dash.png">Dash</div>', 
                    handler: function(btn,event){
                    	main.MainPanel.layout.setActiveItem(dash.panel_dash);
                    	dash.panel_dash.getUserInfo();
					}               
                },               
                {   
                    cls:'Search',
                    text: '<div align="center"><img src= "'+ local_img + 'callme.png">Call me</div>',
                    handler: function(btn,event){
                    	callme.init();
                    	callme.panel_callme.getCallmeList();
                        main.MainPanel.layout.setActiveItem(callme.panel_callme);
                    }
                }]
        }], 
        receiveLocationPos:function(lng,lat)
        {
            daummap.init(lng,lat);
            main.MainPanel.layout.setActiveItem(daummap.panel_map);
        },        
    }); 
}

