var common_url = "http://54.178.192.82/homepage";//"http://192.168.219.129:8080/homepage";//
var local_img = "./img/";
var android_img = "./img/";
var iphone_img = "";
var common_id = window.android.id();
var common_no = window.android.no();
var common_sex = window.android.sex();
var common_photo = window.android.photo();
var common_name = window.android.name();
var common_some = window.android.some();

local_img = android_img;

var user_agent = navigator.userAgent.toLowerCase();
var mobile_phones = new Array( 'android', 'iphone', 'ipod', 'ipad');

for (var i = 0; i < mobile_phones.length; i++)
{
    if (user_agent.indexOf(mobile_phones[i]) != -1)
    {
       if(i ==0 )
       	local_img = android_img;
       else
       	local_img = iphone_img;
    }     
}

window.onload=callAndroid;