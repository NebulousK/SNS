Ext.ns("photo");
Ext.ns("photo.panel");

photo.init = function() {
	
    Ext.regModel('photo', {
    	fields: ['html']
    });
   
    
    Ext.regModel("Album", {
        fields: [
            {name: "name", type: "string"},
            {name: "path", type: "string"},
            {name: "last_modified", type: "string"}
        ],
        
        proxy: {
            type: 'ajax',
            url: 'get-album.php',
            reader: {
                root: 'albums',
                type: 'json'
            }
        }
    });
    var orgData = [];
    
    var store = new Ext.data.Store({
            model: 'photo',                
            data: orgData,
        });             
	 
    function setphotoList(Jv_data) {
    	orgData = Jv_data;
    	store.add(orgData);
    }
  
    photo.panel = new Ext.Carousel({
        fullscreen: true,
        defaults   : {
            styleHtmlContent: true
        },
        getphotoList:function()
        {
            Ext.Ajax.request({
                url: common_url +'/mluvphoto.some?no='+ common_no,
                success: function(response, opts) {
                    console.log(response.responseText);
                    var JsonData = JSON.parse(response.responseText);
                    console.log(JsonData);
                    if(JsonData.data.err == "")
                    {
                        setphotoList(JsonData.data.photo_list);    
                    }
                    else
                    {
                        alert(JsonData.data.err);
                    }
                }
            });         
        },
        items: store,
    });
}
