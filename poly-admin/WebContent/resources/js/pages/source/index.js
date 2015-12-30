function OnSave() {
	
	
	return true;
};
function OnChangeMenu(obj, menuid, name, url, parent, ord, position, slug,action) {
	$.fancybox.close();	
	switch(action) {
 	case "1":
 		window.location.href = $('*[id*=lnkMenuDetail]').val().replace('{PARM_PARENT_ID}', menuid);
 		break;
 	case "10":
 		$('*[id*=txtParentId]').val(menuid);
 		$('*[id*=txtParentName]').val(name);	 		
 		break;
}   	
};

function OnSuccessUpload(myResult) {   
    var hdFileStorageDomain = $('#hdFileStorageDomain').val();
	var fileUrl = hdFileStorageDomain + '/' + myResult.Src;  
	$('*[id*=txtIconUrl]').val(fileUrl);
    $.fancybox.close();
};  
