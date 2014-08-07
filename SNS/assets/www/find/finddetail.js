Ext.ns("detail");
Ext.ns("detail.panel_detail");

detail.panel_detail = new Ext.form.FormPanel({
    useCurrentLocation: true,               
    scroll:'vertical',
    cardSwitchAnimation:"cube",
    width: '100%',
    setUserId:function(user_no){
        this.input_user_no = user_no;
    },
    getUserInfo:function(){
        Ext.Ajax.request({
            url: common_url + '/mfindd.friend?no=' + this.input_user_no,
            success: function(response, opts) {
                console.log(response.responseText);
                var JsonData = JSON.parse(response.responseText);
                console.log(JsonData);
                if(JsonData.data.err == "")
                {
                    detail.panel_detail.setPersonFields(JsonData.data.psn_detail);
                }
                else
                {
                    alert(JsonData.data.err);
                }                
            }
        });         
    },  
    setPersonFields:function(psnDetail)
    {
        var psnPic =  '<table width="100%"><tr><td><center>' + 
                      '<img src="' + common_url + 
                      '/profile/' +  psnDetail.photo +'" height="150"></td></tr></table>';       
        Ext.getCmp("panel_detail.user_id").setValue(psnDetail.id);
        Ext.getCmp("panel_detail.user_name").setValue(psnDetail.name);
        Ext.getCmp("panel_detail.age").setValue(psnDetail.age);
        Ext.getCmp("panel_detail.email").setValue(psnDetail.email);
        Ext.getCmp("panel_detail.birthday").setValue(psnDetail.birthday);
        Ext.getCmp("panel_detail.user_pic").update(psnPic);
        
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
        title: '회원 조회',
        //instructions: '직원명을 입력하시고 조회버튼을 클릭하십시요',
        pack: 'center',
        defaults: {
            required: true,
            labelAlign: 'left' ,
            labelWidth:'40%',
        },
        items:[
            {
                xtype:'panel',
                id:'panel_detail.user_pic',
                xtype: 'fieldset',
                html:'',            
            },    
            {
                xtype:'textfield',
                label:'ID',
                id:'panel_detail.user_id',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
            }, 
            {
                xtype:'textfield',
                id:'panel_detail.user_name',
                label:'이름',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false
            },    
            {
                xtype:'textfield',
                id:'panel_detail.age',
                label:'나이',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false                          
            },    
            {
                xtype:'textfield',
                id:'panel_detail.email',
                label:'email',
                disabled : true,
                disabledCls: 'af-item-disabled',
                autoCapitalisze:true,
                useClearIcon:false                          
            }, 
            {
                xtype:'textfield',
                id:'panel_detail.birthday',
                label:'생일',
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
                     style: 'margin: .5em;',
             items:[{
                        xtype:'button',
                        ui: 'decline-round',
                        width: '45%',
                        textAlign:'center',
                        text: '친구 신청',    
                        handler:function(){ 
                        	Ext.Ajax.request({
                                url: common_url + '/mfindadd.friend?myid=' + common_id + '&id='  + Ext.getCmp("panel_detail.user_id").getValue(),
                                success: function(response, opts) {
                                    alert("친구 신청 이 되었습니다.");   
                                    //main.MainPanel.layout.setActiveItem(list.panel_list); 
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
                        text:'목록',
                        handler:function(){                                     
                           main.MainPanel.layout.setActiveItem(list.panel_list); 
                        },
                    }]
            }]                                  
    }]
});     
