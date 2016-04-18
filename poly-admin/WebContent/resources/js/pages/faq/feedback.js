function OnSave() {
	
	
	return true;
};

function OnSuccessUpload(myResult) {   
    var hdFileStorageDomain = $('#hdFileStorageDomain').val();
	var fileUrl = hdFileStorageDomain + '/' + myResult.Src;  
	$('*[id*=txtImage]').val(fileUrl);
    $.fancybox.close();
};  