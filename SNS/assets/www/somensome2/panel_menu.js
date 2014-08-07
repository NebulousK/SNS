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
                xtype:'spacer',
                height:'5%',
            },          
            {
                xtype:'button',
                margin:0,
                cls:'x-btn-text-icon',
                width: '30%',
                textAlign:'center',
                text:'<div align="center"><img src= "'+ local_img + 'compose.png">love board</div>',
                handler: function(){
                	scrollList.init();
                	scrollList.panel.getsomeboardList();
                    main.MainPanel.layout.setActiveItem(scrollList.panel);
                }                               
            },{ 
                xtype:'spacer',
                width:'5%',
            },{
                xtype:'button',
                width: '30%',
                textAlign:'center',
                text:'<div align="center"><img src= "'+ local_img + 'photo1.png">사진첩</div>',
                handler: function(){
               	 	document.location = "somensome2_1.html?no="+ common_no;
                	//document.location = "somensome2_1.html?";
               }
            },{ 
                xtype:'spacer',
                width:'5%',
            },{

                xtype:'button',
                width: '30%',
                textAlign:'center',
                text:'<div align="center"><img src= "'+ local_img + 'info.png">일정 관리</div>',
                handler: function(){
                	cel.init();
                	main.MainPanel.layout.setActiveItem(cel.panel);  
                }
            }]
        }]
    }],
    receiveLocationPos:function(lng,lat)
    {
        daummap.init(lng,lat);
        main.MainPanel.layout.setActiveItem(daummap.panel_map);
    },   
});  
} 


