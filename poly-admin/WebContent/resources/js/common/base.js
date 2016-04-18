String.prototype.MyreplaceAll = function(s1, s2) {
	return this.split(s1).join(s2)
}
String.prototype.trim = function() {
	return this.replace(/^\s*/, "").replace(/\s*$/, "");
}

String.prototype.stripTags = function() {
	return this.replace(/<\/?[^>]+>/gi, '').trim();
};

/*
 * $Id Find and return element with id $Id("elementId")
 */
$Id = function(id) {
	return document.getElementById(id);
}

/*
 * $Id Check value is null or '' $StringIsNullOrEmpty(value)
 */
$StringIsNullOrEmpty = function(value) {
	var isNullOrEmpty = true;
	if (value) {
		if (typeof (value) == 'string') {
			if (value.length > 0)
				isNullOrEmpty = false;
		}
	}
	return isNullOrEmpty;
}

$IsNullOrEmpty = function(value) {
	var isNullOrEmpty = true;
	if (value) {
		if (typeof (value) == 'string') {
			if (value.length > 0)
				isNullOrEmpty = false;
		}
	}
	return isNullOrEmpty;
}

$IsNumeric = function(value) {
	var isNumeric = /\D+/.test(value);
	return !isNumeric;
}

/*
 * $Id Check value is null or '' or 0 $IsZero(value)
 */
$IsZero = function(value) {
	var isZero = true;
	if (typeof (value) == 'string') {
		if ($StringIsNullOrEmpty(value)) {
			return isZero;
		} else {
			if (parseInt(value) == 0) {
				return isZero;
			} else {
				return !isZero;
			}
		}
	} else {
		if (parseInt(value) == 0) {
			return isZero;
		} else {
			return !isZero;
		}
	}
}
$IsEnter = function(e) {
    e = e || window.event;    
    return (e.keyCode == 13);	
};

function isPhoneNumber(mobilenumber) {
	if (typeof mobilenumber == "undefined")
		return false;
	var b = mobilenumber.replace(/[\s()+-]|ext\.?/gi, "");
	return /\d{10,}/i.test(b)
}

/*
 * $StringtoDate parameter strdate (dd-MM-yyyy)
 */
$StringtoDate = function(strdate) {
	var myDate = new Date();
	var strMonth = strdate.substring(3, 5) - 1;
	var strDate = strdate.substring(0, 2);
	var strYear = strdate.substring(6, 10);

	myDate.setFullYear(strYear, strMonth, strDate);

	return myDate
}

/*
 * $isValidDate parameter strdate (dd-MM-yyyy) or (d-M-yyyy)
 */
function isValidDate(strDate) {
	// (\d{1,2}) means 4 or 12
	// (\/|-) means either (/ or -), 4-12 or 4/12
	// NOTE: we have to escape / (\/)
	// or else pattern matching will interpret it to mean the end instead of the
	// literal "/"
	// \2 use the 2nd placeholder (\/|-) "here"
	// (\d{2}|\d{4}) means 02 or 2002
	var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{4})$/;
	var matchArray = strDate.match(datePat);

	if (matchArray == null)
		return false;

	// matchArray[0] will be the original entire string, for example, 04-12-2002
	// or 04-12-2002
	var month = matchArray[3]; // (\d{1,2}) - 1st parenthesis set - 4
	var day = matchArray[1]; // (\d{1,2}) - 3rd parenthesis set - 12
	var year = matchArray[4]; // (\d{4}) - 5th parenthesis set 2002

	if (year < 1753)
		return false;
	if (year > 9999)
		return false;
	if (month < 1 || month > 12)
		return false;
	if (day < 1 || day > 31)
		return false;
	if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31)
		return false;
	if (month == 2) {
		var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));

		if (day > 29 || (day == 29 && !isleap))
			return false;
	}
	return true;
}

$ValidDate = function(txt, callback) {
	if (!isValidDate(txt.value)) {
		jAlert('Ngày  không hợp lệ! format:dd-MM-yyyy', 'Alert Dialog',
				function() {
					txt.value = "";
					txt.focus();
				});
		if (callback)
			callback(false);
		return false;
	} else {
		if (callback)
			callback(true);
		return true;
	}
}

/*
 * $DatetoString parameter date return strdate (dd-MM-yyyy)
 */
$DatetoString = function(mdate) {
	var dd = mdate.getDate();
	if (dd < 10)
		dd = '0' + dd;
	var mm = mdate.getMonth() + 1;
	if (mm < 10)
		mm = '0' + mm;
	var yyyy = mdate.getFullYear();
	return dd + "-" + mm + "-" + yyyy

}

GetsStr = function(sStr) {
	var str = sStr;
	var strs = sStr.split(".");
	if (strs.length > 2) {
		str = sStr.MyreplaceAll(".", "");
	} else if (strs.length == 2) {
		var x2 = strs[1];
		if (x2.length == 3) {
			str = sStr.MyreplaceAll(".", "");
		}

	}
	var strs2 = sStr.split(",");
	if (strs2.length > 2) {
		str = sStr.MyreplaceAll(",", "");

	} else if (strs2.length == 2) {
		var x3 = strs2[1];
		if (x3.length == 3) {
			str = sStr.MyreplaceAll(",", "");
		}

	}

	return str.replace(",", ".");
}

function addCommas(nStr) {

	nStr += '';
	var nStrtemp = GetsStr(nStr);
	nStr = nStrtemp.MyreplaceAll(".", ",");
	x = nStr.split(',');
	x1 = x[0];
	x2 = x.length > 1 ? ',' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + '.' + '$2');
	}
	if (x2 != ",00" && !$StringIsNullOrEmpty(x2)) {
		x2 = '0' + x2;
		x2 = x2.MyreplaceAll(",", ".");
		trace(x2)
		x2 = parseFloat(x2).toFixed(2);

		x2 += '';
		x2 = x2.MyreplaceAll(".", ",");
		return x1 + ',' + x2.split(',')[1];

	} else {
		return x1;
	}
}
function UnCommas(txt) {
	var nStr = txt.value;
	nStr = nStr.MyreplaceAll(".", ",");

	x = nStr.split(',');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';

	if (x2 != ".00" && !$StringIsNullOrEmpty(x2)) {
		txt.value = x1 + x2;
	} else {
		txt.value = x1;
	}

}

function GetURLParam(name, valueDefault) {
	name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regexS = "[\\?&]" + name + "=([^&#]*)";
	var regex = new RegExp(regexS);
	var results = regex.exec(window.location.href);
	var txt = "";
	if (results != null && results != '') {
		txt = results[1];	
	} else if(valueDefault != 'undefined' && valueDefault != null) {
		txt = valueDefault;
	}
	return txt;
}

$IsNumbers = function(e) {
	e = e || window.event;
	if (e.charCode == 0)
		return;
	if (e.charCode < 48 || e.charCode > 57)
		return false;

}

jQuery.query = function() {
	var r = {};
	var q = location.search;
	q = q.replace(/^\?/, ''); // remove the leading ?
	q = q.replace(/\&$/, ''); // remove the trailing &
	jQuery.each(q.split('&'), function() {
		var key = this.split('=')[0];
		var val = this.split('=')[1];
		// convert floats
		// if(/^[0-9.]+$/.test(val))
		// val = parseFloat(val);
		// ingnore empty values
		if (val)
			r[key] = val;
	});
	return r;

};

jQuery.validateEmail = function(email) {
	var at = email.lastIndexOf("@");

	// Make sure the at (@) sybmol exists and
	// it is not the first or last character
	if (at < 1 || (at + 1) === email.length)
		return false;

	// Make sure there aren't multiple periods together
	if (/(\.{2,})/.test(email))
		return false;

	// Break up the local and domain portions
	var local = email.substring(0, at);
	var domain = email.substring(at + 1);

	// Check lengths
	if (local.length < 1 || local.length > 64 || domain.length < 4
			|| domain.length > 255)
		return false;

	// Make sure local and domain don't start with or end with a period
	if (/(^\.|\.$)/.test(local) || /(^\.|\.$)/.test(domain))
		return false;

	// Check for quoted-string addresses
	// Since almost anything is allowed in a quoted-string address,
	// we're just going to let them go through
	if (!/^"(.+)"$/.test(local)) {
		// It's a dot-string address...check for valid characters
		if (!/^[-a-zA-Z0-9!#$%*\/?|^{}`~&'+=_\.]*$/.test(local))
			return false;
	}

	// Make sure domain contains only valid characters and at least one period
	if (!/^[-a-zA-Z0-9\.]*$/.test(domain) || domain.indexOf(".") === -1)
		return false;

	return true;
};

$EmailValidDate = function(txt, callback) {
	if (!jQuery.validateEmail(txt.value)) {
		jAlert("email không đúng định dạng!", 'Alert Dialog', function() {
			txt.value = '';
			txt.focus();
		});

		if (callback)
			callback(false);
		return false;

	} else {
		if (callback)
			callback(true);
		return true;
	}
}

function gotoTop() {
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

$Location = function() {
	var pathNames = window.location.pathname.split("/");
	return window.location.protocol + "//" + window.location.host + "/"
			+ pathNames[1] + "/";
	// var port = window.location.port;

	// if ($StringIsNullOrEmpty(port)) {
	//        
	// return window.location.protocol + "//" + window.location.host + "/" +
	// pathNames[1] + "/";
	// }
	// else {

	// return window.location.protocol + "//" + window.location.host + ":" +
	// port + "/" + pathNames[1] + "/";
	// }
}

function check_strlen(obj, id2, strlen) {
	object2 = document.getElementById(id2);
	if (obj != null && object2 != null) {
		if (obj.value.length > strlen) {
			object2.innerHTML = 0;
			// alert("Bạn đã nhập đủ " + strlen + " ký tự.");
			obj.value = obj.value.substr(0, strlen);
			return false;
		}
		object2.innerHTML = parseInt(strlen - obj.value.length);
	}
}

$(document).ready(function() {
	$("#check-all").click(function() {
		if ($(this).is(":checked")) {
			$("input[id^=check-item]").attr("checked", "checked");
		} else {
			$("input[id^=check-item]").removeAttr("checked");
		}
	})

	// $("fieldset.collapsible").collapse();
	// $("fieldset.startClosed").collapse( { closed: true } );

	// Set input button
	$('input[type="button"]').css({
		'cursor' : 'pointer'
	});

	// Set status input text
	$('input[type="text"]').addClass("idleField");
	$('input[type="text"]').focus(function() {
		$(this).removeClass("idleField").addClass("focusField");
		this.select();
	});
	$('input[type="text"]').blur(function() {
		$(this).removeClass("focusField").addClass("idleField");
	});

	// Set status input text
	$('textarea').addClass("idleField");
	$('textarea').focus(function() {
		$(this).removeClass("idleField").addClass("focusField");
		this.select();
	});
	$('textarea').blur(function() {
		$(this).removeClass("focusField").addClass("idleField");
	});

	// Set status select
	$('select').addClass("idleField");
	$('select').focus(function() {
		$(this).removeClass("idleField").addClass("focusField");

	});
	$('select').blur(function() {
		$(this).removeClass("focusField").addClass("idleField");
	});

	$('.btnmms-img').hover(function() {

		var url = $(this).attr('src');
		url = url.replace(".png", "_hover.png");

		$(this).attr('src', url);
	}, function() {
		var url = $(this).attr('src');
		url = url.replace("_hover.png", ".png");

		$(this).attr('src', url);
	});
	
	

});

function checkAllBox(id, pID) {
	$("#" + pID + " :checkbox").attr('checked', $('#' + id).is(':checked'));
}

getIdList = function() {
	var list = "";
	if (!$("input[id^=item]:checked")[0]) {
		return false;
	} else {
		$("input[id^=item]:checked").each(function() {
			list = list + ',' + $(this).attr("value");

		});

		// remove the first comma
		return list.substring(1);
	}
};

$jConfirm = function(button_id, message) {
	if (typeof message == 'undefined' || message == null) {
		message = $('#' + button_id).attr("F");
	}

	jConfirm(message, 'Confirmation Dialog', function(r) {
		if (r) {
			$('#' + button_id).click();
		}
		return r;
	});

	return false;
};

// return the value of the radio button that is checked
// return an empty string if none are checked, or
// there are no radio buttons
function getCheckedValue(radioObj, defaultValue) {
	if (typeof (defaultValue) == 'undefined')
		defaultValue = "";
	if (!radioObj)
		return defaultValue;
	var radioLength = radioObj.length;
	if (radioLength == undefined)
		if (radioObj.checked)
			return radioObj.value;
		else
			return defaultValue;
	for ( var i = 0; i < radioLength; i++) {
		if (radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return defaultValue;
}

// set the radio button with the given value as being checked
// do nothing if there are no radio buttons
// if the given value does not exist, all the radio buttons
// are reset to unchecked
function setCheckedValue(radioObj, newValue) {
	if (!radioObj)
		return;
	var radioLength = radioObj.length;
	if (radioLength == undefined) {
		radioObj.checked = (radioObj.value == newValue.toString());
		return;
	}
	for ( var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
		if (radioObj[i].value == newValue.toString()) {
			radioObj[i].checked = true;
		}
	}
}

// Stop event
Stop = function(e) {
	if (e.stopPropagation != undefined)
		e.stopPropagation();
	else if (e.cancelBubble != undefined)
		e.cancelBubble = true;
	if (e.preventDefault != undefined)
		e.preventDefault();
	else
		e.returnValue = false;
}

jQuery.cookie = function(name, value, options) {
	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires
				&& (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime()
						+ (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires
															// attribute,
															// max-age is not
															// supported by IE
		}
		// CAUTION: Needed to parenthesize options.path and options.domain
		// in the following expressions, otherwise they evaluate to undefined
		// in the packed version for some reason...
		var path = options.path ? '; path=' + (options.path) : '';
		var domain = options.domain ? '; domain=' + (options.domain) : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [ name, '=', encodeURIComponent(value), expires,
				path, domain, secure ].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for ( var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie
							.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};
function GetFileType(src) {
    var extIMAGE = 'jpg jpeg gif png bmp';
    var extAUDIO = 'mp3 wav mid';
    var extVIDEO = '3gp avi flv mp4 wmv';
    var ext = src.substring(src.lastIndexOf(".") + 1);
    if (extIMAGE.indexOf(ext) > -1) return 'image';
    if (extAUDIO.indexOf(ext) > -1) return 'audio';
    if (extVIDEO.indexOf(ext) > -1) return 'video';
    return 'other';
};
jsf.ajax.addOnEvent(function(data) {
    if (data.status == "success") {
        var viewState = getViewState(data.responseXML);

        for (var i = 0; i < document.forms.length; i++) {
            if (!hasViewState(document.forms[i])) {
                createViewState(document.forms[i], viewState);
            }
        }
    }
});

function getViewState(responseXML) {
    var updates = responseXML.getElementsByTagName("update");

    for (var i = 0; i < updates.length; i++) {
        if (updates[i].getAttribute("id") == "javax.faces.ViewState") {
            return updates[i].firstChild.nodeValue;
        }
    }

    return null;
}

function hasViewState(form) {
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].name == "javax.faces.ViewState") {
            return true;
        }
    }

    return false;
}

function createViewState(form, viewState) {
    var hidden;
    
    try {
        hidden = document.createElement("<input name='javax.faces.ViewState'>"); // IE6-8.
    } catch(e) {
        hidden = document.createElement("input");
        hidden.setAttribute("name", "javax.faces.ViewState");
    }

    hidden.setAttribute("type", "hidden");
    hidden.setAttribute("value", viewState);
    hidden.setAttribute("autocomplete", "off");
    form.appendChild(hidden);
}

String.prototype.format = String.prototype.f = function() {
    var s = this,
    i = arguments.length;
    while (i--) {
        s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return s;
};



