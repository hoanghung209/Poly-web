function OnSave() {
	var username = $('*[id*=txtUsername]').val();
	if($StringIsNullOrEmpty(username)) {
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f('{' + LANG.CONSTANT.userName + '}'), LANG.CONSTANT.alertDialog, function(){	
			$('*[id*=txtUsername]').focus();
		});

		return false;
	}
	
	if (username.length < 6) {
		jAlert(LANG.MESSAGE.alertLimitedNumber.f('{' + LANG.CONSTANT.userName + '}', 6, 50), LANG.CONSTANT.alertDialog, function() {
			$('*[id*=txtUsername]').focus();
        });

        return false;
    }

    var fullname = $('*[id*=txtFullName]').val();
    if($StringIsNullOrEmpty(fullname)) {
		jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f('{' + LANG.CONSTANT.fullName + '}'), LANG.CONSTANT.alertDialog, function(){	        
        	$('*[id*=txtFullName]').focus();
        });

        return false;
    } 

    var userID = $('*[id*=txtId]').val();
    if($IsZero(userID)) {
        var password = $('*[id*=txtPassword]').val();
        if($StringIsNullOrEmpty(password)) {
			jAlert(LANG.MESSAGE.MUST_BE_NOT_NULL.f('{' + LANG.CONSTANT.password + '}'), LANG.CONSTANT.alertDialog, function(){	        
            	$('*[id*=txtPassword]').focus();
            });

            return false;
        } 

        if(password.length < 8) {
           jAlert(LANG.MESSAGE.alertLimitedNumber.f('{' + LANG.CONSTANT.password + '}', 8, 100), LANG.CONSTANT.alertDialog, function() {
        	   $('*[id*=txtPassword]').focus();
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
            	$('*[id*=txtPassword]').focus();
            });
             return false;
        }
    }

	var txtPhone = $('*[id*=txtPhone]').val();
	if(!$StringIsNullOrEmpty(txtPhone) && txtPhone.length > 200) {
		jAlert(LANG.MESSAGE.MIN_LENGTH.f(LANG.CONSTANT.phone, 200), LANG.CONSTANT.alertDialog, function() {
			$('*[id*=txtPhone]').focus();
		});

		return false;
	}

	var txtEmail = $('*[id*=txtEmail]').val().trim(); $('*[id*=txtEmail]').val(txtEmail);	
	if(!$StringIsNullOrEmpty(txtEmail) && txtEmail.length > 200) {
	alert(2);
		jAlert(LANG.MESSAGE.MIN_LENGTH.f(LANG.CONSTANT.email, 200), LANG.CONSTANT.alertDialog, function() {
			$('*[id*=txtEmail]').focus();
		});

		return false;
	}
	if(!$StringIsNullOrEmpty(txtEmail) && !validateEmail(txtEmail)) {
		jAlert(LANG.MESSAGE.INVALID_EMAIL.f(LANG.CONSTANT.email), LANG.CONSTANT.alertDialog, function() {
			$('*[id*=txtEmail]').focus();
		});

		return false;
	}    
    
    $('#mask').show();
	return true;
};
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
function validateEmail(email) { 
    var re = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return re.test(email);
} 