function OnSave() {
	var name = $('*[id*=txtName]').val();	
	if($StringIsNullOrEmpty(name)) {		
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f('{' + LANG.CONSTANT.name + '}'), LANG.CONSTANT.alertDialog, function(){
			$('*[id*=txtName]').focus();			
		});

		return false;
	}	
	
	var description = $('*[id*=txtDescription]').val();
	
	// {{ checkLength
	if(!$StringIsNullOrEmpty(description) && description.length > 200) {
		jAlert(LANG.MESSAGE.MAX_LENGTH.f('{' + LANG.CONSTANT.source + '}', 200), LANG.CONSTANT.alertDialog, function() {			
			$('*[id*=txtDescription]').focus();
		});

		return false;	
	}			
	// }}
	
	return true;
};