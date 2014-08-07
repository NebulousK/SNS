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
                    text: '<div align="center"><img src= "'+ local_img + 'compose.png">love</div>', 
                    handler: function(btn,event){
                    	scrollList.init();
                    	scrollList.panel.getsomeboardList();
                        main.MainPanel.layout.setActiveItem(scrollList.panel); 
					}               
                },               
                {   
                    cls:'Search',
                    text: '<div align="center"><img src= "'+ local_img + 'photo1.png">Photo</div>',
                    handler: function(btn,event){
                    	document.location = "somensome2_1.html?no="+ common_no;
                    	//document.location = "somensome2_1.html";
                    }
                },
                {   
                    cls:'Camera',
                    text: '<div align="center"><img src= "'+ local_img + 'info.png">Calendar</div>',
                    handler: function(btn,event){
                    	cel.init();
                    	main.MainPanel.layout.setActiveItem(cel.panel);   
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

