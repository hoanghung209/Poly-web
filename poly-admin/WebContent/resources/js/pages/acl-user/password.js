$(function(){
	$Id('txtchangepassword').value = "";
	
	var txtOldPassword = $Id('txtOldPassword');
	if(txtOldPassword != null) txtOldPassword.value = ""; 
});
function OnConfirmChangepassword (){
	var txtOldPassword = $Id('txtOldPassword');
	if(txtOldPassword != null) {
	    if($StringIsNullOrEmpty(txtOldPassword.value)) {
    		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f('{' + LANG.CONSTANT.oldPassword + '}'), LANG.CONSTANT.alertDialog, function(){
	        	txtOldPassword.focus();
	        });
	        return false;
	    }		
	}
	
    var password = ($Id('txtchangepassword').value);
    if($StringIsNullOrEmpty(password)) {
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f('{' + LANG.CONSTANT.newPassword + '}'), LANG.CONSTANT.alertDialog, function(){    	
            $Id('txtchangepassword').focus();
        });
        return false;
    } 

    if(password.length <8) {
    	jAlert(LANG.MESSAGE.alertLimitedNumber.f('{' + LANG.CONSTANT.password + '}', 8, 100), LANG.CONSTANT.alertDialog, function() {
            $Id('txtchangepassword').focus();
        });
        return false;
    }
           
    var passed = validatePassword(password, {
	    length:   [8, Infinity],
	    lower:    1,
	    upper:    1,
	    numeric:  1,
	    special:  1,
	    badWords: ["password", "steven", "levithan"],
	    badSequenceLength: 4
    });
        
    if(!passed) {
    	jAlert(LANG.MESSAGE.alertObjectStrong.f('{' + LANG.CONSTANT.password + '}'), LANG.CONSTANT.alertDialog, function(){
            $Id('txtchangepassword').focus();
        });
            return false;

    }
    
    var cfrpassword = ($Id('txtconfirmchangepassword').value);

    if(cfrpassword !=password) {
    	jAlert(LANG.MESSAGE.alertSamePassword, LANG.CONSTANT.alertDialog, function() {
            $Id('txtchangepassword').focus();
        });
        return false;
    }


    return true;
}
function validatePassword (pw, options) {
	// default options (allows any password)
	var o = {
		lower:    0,
		upper:    0,
		alpha:    0, /* lower + upper */
		numeric:  0,
		special:  0,
		length:   [0, Infinity],
		custom:   [ /* regexes and/or functions */ ],
		badWords: [],
		badSequenceLength: 0,
		noQwertySequences: false,
		noSequential:      false
	};

	for (var property in options)
		o[property] = options[property];

	var	re = {
			lower:   /[a-z]/g,
			upper:   /[A-Z]/g,
			alpha:   /[A-Z]/gi,
			numeric: /[0-9]/g,
			special: /[\W_]/g
		},
		rule, i;

	// enforce min/max length
	if (pw.length < o.length[0] || pw.length > o.length[1])
		return false;

	// enforce lower/upper/alpha/numeric/special rules	
	for (rule in re) {
		if ((pw.match(re[rule]) || []).length < o[rule])
			return false;
	}

	// enforce word ban (case insensitive)
	for (i = 0; i < o.badWords.length; i++) {
		if (pw.toLowerCase().indexOf(o.badWords[i].toLowerCase()) > -1)
			return false;
	}

	// enforce the no sequential, identical characters rule
	if (o.noSequential && /([\S\s])\1/.test(pw))
		return false;

	// enforce alphanumeric/qwerty sequence ban rules
	if (o.badSequenceLength) {
		var	lower   = "abcdefghijklmnopqrstuvwxyz",
			upper   = lower.toUpperCase(),
			numbers = "0123456789",
			qwerty  = "qwertyuiopasdfghjklzxcvbnm",
			start   = o.badSequenceLength - 1,
			seq     = "_" + pw.slice(0, start);
		for (i = start; i < pw.length; i++) {
			seq = seq.slice(1) + pw.charAt(i);
			if (
				lower.indexOf(seq)   > -1 ||
				upper.indexOf(seq)   > -1 ||
				numbers.indexOf(seq) > -1 ||
				(o.noQwertySequences && qwerty.indexOf(seq) > -1)
			) {
				return false;
			}
		}
	}

	// enforce custom regex/function rules
	for (i = 0; i < o.custom.length; i++) {
		rule = o.custom[i];
		if (rule instanceof RegExp) {
			if (!rule.test(pw))
				return false;
		} else if (rule instanceof Function) {
			if (!rule(pw))
				return false;
		}
	}

	// great success!
	return true;
};