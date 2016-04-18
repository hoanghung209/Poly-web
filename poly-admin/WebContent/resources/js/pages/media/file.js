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
	switch(fcAction) {
		case 'img':		
			$('*[id*=txtIconUrl]').val(filesrc);
			if(!$IsNullOrEmpty(fcCallbackId))
			{
				$('#' + fcCallbackId).attr('src', fileUrl);
			}
			break;
		case 'link':
			$('#' + fcCallbackId).attr('src', fileUrl);
			if(fcCallbackId=='vImageHeader') $('*[id*=txtImageHeader]').val(fileUrl);
			break;
		case 'editor':
			var editor = GetEditorInstance();
			var ceditor = editor; 			
			if (ceditor) 
			{
				if (typeof ceditor.insertHtml != 'undefined') 
				{
                    var embedValue = '';
                    var fileType = GetFileType(fileUrl);
                    switch(fileType) {
                        case 'image': 
                            embedValue += "<img src='" + fileUrl + "' alt=\"\" />";
                            break;
                        case 'audio': 
					        embedValue += "<object width=\"300\" height=\"20\" type=\"application/x-shockwave-flash\" data=\"" + hdFileStorageDomain + "/swf/player_audio.swf\">";
					        embedValue += "<param name=\"movie\" value=\"" + hdFileStorageDomain + "/swf/player_audio.swf\">";
					        embedValue += "<param name=\"FlashVars\" value=\"mp3=" + fileUrl + "\">";
					        embedValue += "<p><a href=\"http://get.adobe.com/flashplayer\">" + LANG.CONSTANT.getFlash +"</a> " + LANG.CONSTANT.toSeeThisPlayer +".</p>";
					        embedValue += "</object>";                      
                            break;
                        case 'video': 
					        embedValue += "<object width=\"480\" height=\"360\" data=\"" + hdFileStorageDomain + "/swf/player.swf\" type=\"application/x-shockwave-flash\">";
					        embedValue += "<param value=\"" + hdFileStorageDomain + "/swf/player.swf\" name=\"movie\">";
					        embedValue += "<param value=\"true\" name=\"allowfullscreen\">";
					        embedValue += "<param value=\"always\" name=\"allowscriptaccess\">";
					        embedValue += "<param value=\"transparent\" name=\"wmode\">";
					        embedValue += "<param value=\"file=" + fileUrl + "\" name=\"flashvars\">";
					        embedValue += "<p><a href=\"http://get.adobe.com/flashplayer\">Get Flash</a> to see this player.</p>";
					        embedValue += "</object>";                        
                            break;
                        default: 
                            embedValue += "<a href='" + fileUrl + "'>" + LANG.CONSTANT.downloadHere + "</a>";
                            break;
                    }
                   
					var html = "<table cellspacing='2' cellpadding='0' border='0' class='tLegend'>";
					html += "<tbody>";
					html += "<tr><td>" + embedValue + "</td></tr>";
					html += "<tr><td><p class='tLegend'>" + LANG.CONSTANT.caption + "</p></td></tr>";
					html += "</tbody>";
					html += "</table>";
				
					ceditor.insertHtml(html);
				}
			}
			break;
	}
}


function OnSuccessUpload(myResult)
{   
    var hdFileStorageDomain = $('#hdFileStorageDomain').val();
	var fileUrl = hdFileStorageDomain + '/' + myResult.Src;  
	
	$('#txtUrl').val(fileUrl);
	OnEmbedFileSuccess(fileUrl, $("#hdFileControlaction").val(),myResult.Src);
    $.fancybox.close();
}; 
function ChangeRandomImage(image, message) {  
	imageCaptcha = document.getElementById(image);
    $(imageCaptcha).attr('src', $(imageCaptcha).attr('src') + Math.floor((Math.random()*10)+1));
    if(message != null && message.length > 0) {
    	$('*[id*=message]').html( message + ' <br/>');
    }
};	
