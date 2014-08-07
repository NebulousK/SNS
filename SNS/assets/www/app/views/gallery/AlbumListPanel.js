Gallery.views.AlbumListPanel = Ext.extend(Ext.Panel, {
    layout: 'fit',
    initComponent: function() {
        this.store = new Ext.data.Store({
            autoLoad: true,
            model: 'Album'
        });
        
        this.dockedItems = [{
            xtype: 'toolbar',
            dock: 'top',
            title: 'Album List',
            items:[{ xtype: 'spacer' }, { 
                itemId: 'helpButton',
                iconCls: 'home',
                iconMask: true,
                handler: function() {
                	document.location = "somensome2_1.html?no=" + common_no;
                }
            }]
        }];
        
        this.list = new Ext.List({
            itemTpl: '{name}',
            store: this.store
        });

        this.items = [this.list];
        
        Gallery.views.AlbumListPanel.superclass.initComponent.apply(this, arguments);
    } 
});

Ext.reg('gallery-albumlistpanel', Gallery.views.AlbumListPanel);