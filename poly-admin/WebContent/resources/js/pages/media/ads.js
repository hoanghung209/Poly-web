


function OnSave()
{
	var nameRn = $('*[id*=txtnameRn]').val().trim(); $('*[id*=txtnameRn]').val(nameRn);
	if($IsNullOrEmpty(nameRn)) 
	{
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f(LANG.CONSTANT.name), LANG.CONSTANT.DIALOG_BOX_TITLE, function(){
			$('*[id*=txtnameRn]').focus();
		});

		return false;
	}
	
	// {{ checkLength NameRn
	if(!$StringIsNullOrEmpty(nameRn) && nameRn.length > 250) {
		jAlert(LANG.MESSAGE.MAX_LENGTH.f(LANG.CONSTANT.name, 250), LANG.CONSTANT.alertDialog, function() {			
			$('*[id*=txtnameRn]').focus();
		});

		return false;	
	}			
	// }}
	
	var nameEn = $('*[id*=txtnameEn]').val().trim(); $('*[id*=txtnameEn]').val(nameEn);
	if($IsNullOrEmpty(nameEn)) 
	{
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f(LANG.CONSTANT.name + '(' + LANG.CONSTANT.english +')'), LANG.CONSTANT.DIALOG_BOX_TITLE, function(){
			$('*[id*=txtnameEn]').focus();
		});

		return false;
	}
	
	// {{ checkLength Nameen
	if(!$StringIsNullOrEmpty(nameEn) && nameEn.length > 250) {
		jAlert(LANG.MESSAGE.MAX_LENGTH.f(LANG.CONSTANT.name + '(' + LANG.CONSTANT.english +')', 250), LANG.CONSTANT.alertDialog, function() {			
			$('*[id*=txtnameEn]').focus();
		});

		return false;	
	}			
	// }}
	
	var namePt = $('*[id*=txtnamePt]').val().trim(); $('*[id*=txtnamePt]').val(namePt);
	if($IsNullOrEmpty(namePt)) 
	{
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f(LANG.CONSTANT.name + '(' + LANG.CONSTANT.portuguese +')'), LANG.CONSTANT.DIALOG_BOX_TITLE, function(){
			$('*[id*=txtnamePt]').focus();
		});

		return false;
	}
	
	// {{ checkLength Namept
	if(!$StringIsNullOrEmpty(namePt) && namePt.length > 250) {
		jAlert(LANG.MESSAGE.MAX_LENGTH.f(LANG.CONSTANT.name + '(' + LANG.CONSTANT.portuguese +')', 250), LANG.CONSTANT.alertDialog, function() {			
			$('*[id*=txtnamePt]').focus();
		});

		return false;	
	}			
	// }}		

    var priority = $('*[id*=txtpriority]').val();
    if($IsNullOrEmpty(priority)) {
    	$('*[id*=txtpriority]').val("0");

        return false;
    } 
    
   
    
    var status = $('*[id*=cboStatus]').val();
    if($IsNullOrEmpty(status)) {
        jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f(LANG.CONSTANT.status), LANG.CONSTANT.DIALOG_BOX_TITLE, function() {
        	$('*[id*=cboparent]').focus();
        });

        return false;
    } 
    
    var target = $('*[id*=txttarget]').val().trim(); $('*[id*=txttarget]').val(target);

	if($IsNullOrEmpty(target)) 
	{
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f('{'+LANG.CONSTANT.web+' '+LANG.CONSTANT.target+'}'), LANG.CONSTANT.DIALOG_BOX_TITLE, function(){
			$('*[id*=txttarget]').focus();
		});

		return false;
	}
	
	
    
    var waptarget = $('*[id*=txtwaptarget]').val().trim(); $('*[id*=txtwaptarget]').val(waptarget);

	if($IsNullOrEmpty(waptarget)) 
	{
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f('{'+LANG.CONSTANT.wap+' '+LANG.CONSTANT.target+'}'), LANG.CONSTANT.DIALOG_BOX_TITLE, function(){
			$('*[id*=txtwaptarget]').focus();
		});

		return false;
	}
	
	var editorRn = CKEDITOR.instances[$("#editorRn").attr('id')];
    if(editorRn)
    	{
    		$('*[id*=editorRn]').val(editorRn.getData());
    	}
    
     var contentRn = $.trim($('*[id*=editorRn]').val());

	if($IsNullOrEmpty(contentRn)) 
	{
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f(LANG.CONSTANT.content), LANG.CONSTANT.DIALOG_BOX_TITLE, function(){
			$('*[id*=editorRn]').focus();
		});

		return false;
	}
	
	var editorEn = CKEDITOR.instances[$("#editorEn").attr('id')];
    if(editorEn)
	{
		$('*[id*=editorEn]').val(editorEn.getData());
	}
    
	var contentEn = $.trim($('*[id*=editorEn]').val());
	
	if($IsNullOrEmpty(contentEn)) 
	{
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f(LANG.CONSTANT.content + '(' + LANG.CONSTANT.english +')'), LANG.CONSTANT.DIALOG_BOX_TITLE, function(){
			$('*[id*=editorEn]').focus();
		});
	
		return false;
	}
	
    var editorPt = CKEDITOR.instances[$("#editorPt").attr('id')];
    if(editorPt)
    	{
    		$('*[id*=editorPt]').val(editorPt.getData());
    	}
    
     var contentPt = $.trim($('*[id*=editorPt]').val());

	if($IsNullOrEmpty(contentPt)) 
	{
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f(LANG.CONSTANT.content + '(' + LANG.CONSTANT.portuguese +')'), LANG.CONSTANT.DIALOG_BOX_TITLE, function(){
			$('*[id*=editorPt]').focus();
		});

		return false;
	}
	
	
    $('#mask').show();
	return true;
};





/* 
 * @end-region private-function------------------------------------------
 */

function GetEditorInstance() {

    var ck_instance_name = false;
    for ( var ck_instance in CKEDITOR.instances ){
        if (CKEDITOR.instances[ck_instance].focusManager.hasFocus){
            ck_instance_name = ck_instance;
            return CKEDITOR.instances[ck_instance_name];  
        }
    }
    for ( var ck_instance in CKEDITOR.instances ){
    	return CKEDITOR.instances[ck_instance];  
    }
    
    
}
function OnEmbedFile(action, callbackId){	
	var url = $('#hdFileControl').val();

	if(action != null) url += '?fcAction=' + action;
	if(callbackId != null) 
	{
		url += '&fcCallbackId=' + callbackId;
	  $("#hdFilecallbackid").val(callbackId);
	}else
		{
		 $("#hdFilecallbackid").val("");
		}
	
	
	$("#lnkEmbedFile").attr('href', url);
	$("#lnkEmbedFile").mouseover();
    $("#lnkEmbedFile").click();
    $("#hdFileControlaction").val(action);
    
    
   
}
function OnEmbedFileSuccess( fileUrl,  fcAction, filesrc) {  
  var fcCallbackId = $("#hdFilecallbackid").val();
	switch(fcAction)
	{
	case 'imgvn':
		
			$('*[id*=txtImageVn]').val(filesrc);
			
			if(!$IsNullOrEmpty(fcCallbackId))
			{
				$('#' + fcCallbackId).attr('src', fileUrl);
			}
			break;
	case 'imgen':
		
		$('*[id*=txtImageEn]').val(filesrc);
		
		if(!$IsNullOrEmpty(fcCallbackId))
		{
			$('#' + fcCallbackId).attr('src', fileUrl);
		}
		break;
		case 'link':
			$('#' + fcCallbackId).attr('src', fileUrl);
			if(fcCallbackId=='vImageHeader') $('*[id*=txtImageHeader]').val(fileUrl);
			break;		
	}
}


function OnSuccessUpload(myResult)
{   
    var hdFileStorageDomain = $('#hdFileStorageDomain').val();
	var fileUrl = hdFileStorageDomain + '/' + myResult.Src;  
	
	OnEmbedFileSuccess(fileUrl, $("#hdFileControlaction").val(),myResult.Src);
    $.fancybox.close();
}; 
