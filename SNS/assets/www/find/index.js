Ext.ns("main");
Ext.ns("main.MainPanel");

Ext.setup({
    icon: 'icon.png',
    glossOnIcon: false,
    tabletStartupScreen: 'tablet_startup.png',
    phoneStartupScreen: 'phone_startup.png',
    onReady: function() {
        main.initMainPanel();	
        list.init();
        main.MainPanel.layout.setActiveItem(list.panel_list); 
    }
});

main.initMainPanel = function()
{
    main.MainPanel = new Ext.Panel({
        fullscreen: true,
        id:'MainPanel',
        cardSwitchAnimation:"cube",
        layout:'card',                 
    }); 
   
};
