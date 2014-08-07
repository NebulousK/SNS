Ext.ns("dash");
Ext.ns("dash.panel_dash");

dash.panel_dash = new Ext.form.FormPanel({
	fullscreen: true,
    useCurrentLocation: true,               
    scroll:'vertical',
    cardSwitchAnimation:"cube",
    width: '100%',
    getUserInfo:function()
    {
        Ext.Ajax.request({
            url: common_url + '/mdash.some?no='+ common_no +'&sex=' + common_sex, //common_url + '/mdash.some?no=532&sex=man',
            success: function(response, opts) {
                console.log(response.responseText);
                var JsonData = JSON.parse(response.responseText);
                console.log(JsonData);
                if(JsonData.data.err == "")
                {
                    dash.panel_dash.setPersonFields(JsonData.data.psn_detail);
                }
                else
                {
                    alert(JsonData.data.err);
                }                
            }
        });         
    },  
    setPersonFields:function(psndash)
    {
        var psnPic =  '<table width="100%"><tr><td><center>' + 
                      '<img src="' + common_url + 
                      '/profile/' +  psndash.pic +  '" width="100%"></td></tr></table>';
        Ext.getCmp("dash.name").setValue(psndash.name);
        Ext.getCmp("dash.age").setValue(psndash.age);
        Ext.getCmp("dash.blood").setValue(psndash.blood);
        Ext.getCmp("dash.height").setValue(psndash.height);
        Ext.getCmp("dash.weight").setValue(psndash.weight);
        Ext.getCmp("dash.addr").setValue(psndash.addr);
        Ext.getCmp("dash.style").setValue(psndash.style);
        Ext.getCmp("dash.fashion").setValue(psndash.fashion);
        Ext.getCmp("dash.hobby").setValue(psndash.hobby);
        Ext.getCmp("dash.comment").setValue(psndash.coment);
        Ext.getCmp("dash.no").setValue(psndash.no);
        Ext.getCmp("dash.value").setValue(psndash.value);
        Ext.getCmp("dash.user_pic").update(psnPic);
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
        title: '오늘의 추천 이성',
        pack: 'center',
        defaults: {
            required: true,
            labelAlign: 'left' ,
            labelWidth:'100%',
        },
        items:[
            {
                xtype:'panel',
                id:'dash.user_pic',
                xtype: 'fieldset',
                html:'',            
            }]
            },
            {
                xtype:'textfield',
                label:'no',
                id:'dash.no', 
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false,
                //hidden:true            
            },
            {
                xtype:'textfield',
                label:'이름 ',
                id:'dash.name', 
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
                            
            },{
                xtype:'textfield',
                label:'나이 ',
                id:'dash.age',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
                            
            },            
            {
                xtype:'textfield',
                id:'dash.blood',
                label:'혈액형',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
                            
            },    
            {
                xtype:'textfield',
                id:'dash.height',
                label:'키',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false                          
            },           
            {
           	 xtype:'textfield',
                id:'dash.weight',
                label:'몸무게',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false   
            }, 
            {
           	 xtype:'textfield',
               id:'dash.addr',
               label:'지역',
               disabled : true,
               disabledCls: 'af-item-disabled',
               autoCapitalisze:true,
               useClearIcon:false   
            }, 
            {
         	 xtype:'textfield',
              id:'dash.style',
              label:'성격',
              disabled : true,
              disabledCls: 'af-item-disabled',
              autoCapitalisze:true,
              useClearIcon:false   
           }, 
           {
         	 xtype:'textfield',
              id:'dash.fashion',
              label:'스타일',
              disabled : true,
              disabledCls: 'af-item-disabled',
              autoCapitalisze:true,
              useClearIcon:false   
           },
           {
        	 xtype:'textfield',
             id:'dash.hobby',
             label:'취미',
             disabled : true,
             disabledCls: 'af-item-disabled',
             autoCapitalisze:true,
             useClearIcon:false   
           },
           {
               xtype: 'sliderfield',
               id:'dash.value',
               value:2,
               maxValue: 5,
               minValue: 0,
               label: '이상형 점수',
               autoCapitalisze:true,  
               useClearIcon:false,
               height: '10%'
           },
           {
               xtype:'textareafield',
               id:'dash.comment',
               placeHolder:'작업멘트를 날리세요',
               width : '100%',
               useClearIcon:false,
           },
           {
                xtype:'button',
                ui: 'decline-round',                
                name:'dash.button_close',
                width:'100%',
               	text:'대쉬!!!!',
                handler:function(){   
                	var txt = Ext.getCmp('dash.comment').getValue();
                	Ext.Ajax.request({
                        url: common_url + '/mdashup.some?userID=' + common_no + '&itemID='  +  Ext.getCmp("dash.no").getValue() + '&value=' + Ext.getCmp("dash.value").getValue() + '&coment=' + txt,
                        success: function(response, opts) {
                            alert("대쉬 하였습니다. 좋은 결과가 있길 바래요!!");   
                        }
                    });  
           },
     }]                                  
});     
