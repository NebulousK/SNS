Ext.ns("mcall");
Ext.ns("mcall.panel_mcall");

mcall.panel_mcall = new Ext.form.FormPanel({
	fullscreen: true,
    useCurrentLocation: true,               
    scroll:'vertical',
    cardSwitchAnimation:"cube",
    width: '100%',
    setUserId:function(user_id)
    {
        this.input_user_id = user_id;
    },
    getUserInfo:function()
    {
        Ext.Ajax.request({
            url: common_url + '/mcall_detail.some?no='+ common_no +'&name=' +  encodeURIComponent(this.input_user_id),
            success: function(response, opts) {
                console.log(response.responseText);
                var JsonData = JSON.parse(response.responseText);
                console.log(JsonData);
                if(JsonData.data.err == "")
                {
                	mcall.panel_mcall.setPersonFields(JsonData.data.callme);
                }
                else
                {
                    alert(JsonData.data.err);
                }                
            }
        });         
    },  
    setPersonFields:function(callme)
    {
        var psnPic =  '<table width="100%"><tr><td><center>' + 
                      '<img src="' + common_url + 
                      '/profile/' +  callme.pic +  '" width="100%"></td></tr></table>';
        Ext.getCmp("call.no").setValue(callme.no);
        Ext.getCmp("call.name").setValue(callme.name);
        Ext.getCmp("call.age").setValue(callme.age);
        Ext.getCmp("call.blood").setValue(callme.blood);
        Ext.getCmp("call.height").setValue(callme.height);
        Ext.getCmp("call.weight").setValue(callme.weight);
        Ext.getCmp("call.addr").setValue(callme.addr);
        Ext.getCmp("call.style").setValue(callme.style);
        Ext.getCmp("call.fashion").setValue(callme.fashion);
        Ext.getCmp("call.hobby").setValue(callme.hobby);
        Ext.getCmp("call.comment").setValue(callme.comment);
        Ext.getCmp("call.user_pic").update(psnPic);
    },
    layout: {
        type: 'vbox',
        pack: 'center',
        align: 'stretch'
    },
    scroll: 'vertical',                         
    items:
    [{
        xtype: 'fieldset',
        title: '나에게 대쉬한 이성',
        pack: 'center',
        defaults: {
            required: true,
            labelAlign: 'left' ,
            labelWidth:'100%',
        },
        items:[
            {
                xtype:'panel',
                id:'call.user_pic',
                xtype: 'fieldset',
                html:'',            
            }]
            },
            {
                xtype:'textfield',
                label:'no ',
                id:'call.no', 
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
                            
            },
            {
                xtype:'textfield',
                label:'이름 ',
                id:'call.name', 
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
                            
            },{
                xtype:'textfield',
                label:'나이 ',
                id:'call.age',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
                            
            },            
            {
                xtype:'textfield',
                id:'call.blood',
                label:'혈액형',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
                            
            },    
            {
                xtype:'textfield',
                id:'call.height',
                label:'키',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false                          
            },           
            {
           	 xtype:'textfield',
                id:'call.weight',
                label:'몸무게',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false   
            }, 
            {
           	 xtype:'textfield',
               id:'call.addr',
               label:'지역',
               disabled : true,
               disabledCls: 'af-item-disabled',
               autoCapitalisze:true,
               useClearIcon:false   
            }, 
            {
         	 xtype:'textfield',
              id:'call.style',
              label:'성격',
              disabled : true,
              disabledCls: 'af-item-disabled',
              autoCapitalisze:true,
              useClearIcon:false   
           }, 
           {
         	 xtype:'textfield',
              id:'call.fashion',
              label:'스타일',
              disabled : true,
              disabledCls: 'af-item-disabled',
              autoCapitalisze:true,
              useClearIcon:false   
           }, 
           {
        	 xtype:'textfield',
             id:'call.hobby',
             label:'취미',
             disabled : true,
             disabledCls: 'af-item-disabled',
             autoCapitalisze:true,
             useClearIcon:false   
           }, 
           {
        	   xtype:'textfield',
               id:'call.comment',
               label:'멘트',
               disabled : true,
               disabledCls: 'af-item-disabled',
               autoCapitalisze:true,
               useClearIcon:false   
           },
           {
        	  layout: {
                   align: 'center',
                   type: 'hbox',
                   pack: 'center'},  
                   flex: 2, 
                   //style: 'margin: .5em;',
           items:[{
                      xtype:'button',
                      ui: 'decline-round',
                      width: '45%',
                      textAlign:'center',
                      text: '수락',    
                      handler:function(){                                     
                    	  Ext.Ajax.request({
                              url: common_url + '/mcallup.some?userID=' + common_no + '&itemID='  +  Ext.getCmp("call.no").getValue() + '&state=1&sex=' + common_sex,
                              success: function(response, opts) {
                            	  window.android3.update();
                                  alert("좋은 인연이 시작되길 바래요!!");  
                                  document.location = "somensome.html";
                              }
                          });  
                      },
                  },{ 
                      xtype:'spacer',
                      width:'5%',
                  },{
                      xtype:'button',
                      width: '45%',
                      ui: 'decline-round',
                      textAlign:'center',
                      text:'거절',
                      handler:function(){                                     
                    	  Ext.Ajax.request({
                    		  url: common_url + '/mcallup.some?userID=' + Ext.getCmp("call.no").getValue() + '&itemID='  + common_no + '&state=2&sex=' + common_sex,
                              success: function(response, opts) {
                                  alert("인연이 아니였나 보군요!");   
                                  callme.init();
                              	  callme.panel_callme.getCallmeList();
                                  main.MainPanel.layout.setActiveItem(callme.panel_callme);
                              }
                          });  
                      },
                  }]
           	}]                          
});     
