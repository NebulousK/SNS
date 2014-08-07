Ext.ns("menu");

menu.init = function(){
menu.panel_menu = new Ext.form.FormPanel({
    useCurrentLocation: true,               
    scroll:'vertical',
    cardSwitchAnimation:"cube",
    items:[{
        xtype: 'fieldset',
        title: 'Some N somE',
        width:'80%',
        align: 'center',
        layout: {
                align: 'center'
            },
        defaults: {
            required: true,
            height:100,
            labelAlign: 'center',
            labelWidth: '80%' },
        items:[     
        { 
                xtype:'spacer',
                height:'30',
        },      
        {
            align:'center',
            width: '100%',
            defaults: {
                required: true,
                height:80,
                labelAlign: 'center',
            },
            layout: {
                align: 'center',
                type: 'hbox',
                pack: 'center'},  
            flex: 2, style: 'margin: .5em;',
            items:[          
            {
                xtype:'button',
                margin:0,
                cls:'x-btn-text-icon',
                width: '30%',
                textAlign:'center',
                text:'<div align="center"><img src= "'+ local_img + 'dash.png">Dash</div>',
                handler: function(){
                	main.MainPanel.layout.setActiveItem(dash.panel_dash);
                	dash.panel_dash.getUserInfo();
                }                               
            },{ 
                xtype:'spacer',
                width:'5%',
            },{
                xtype:'button',
                width: '30%',
                textAlign:'center',
                text:'<div align="center"><img src= "'+ local_img + 'callme.png">Call me</div>',
                handler: function(){
                	callme.init();
                	callme.panel_callme.getCallmeList();
                    main.MainPanel.layout.setActiveItem(callme.panel_callme);
               }
            },{ 
                xtype:'spacer',
                width:'5%',
            }]
        }]
    }],
    receiveLocationPos:function(lng,lat)
    {
        daummap.init(lng,lat);
        main.MainPanel.layout.setActiveItem(daummap.panel_map);
    },   
});  
}; 


