$(function(){
	$("#btnAssign").attr("disabled", "disabled");
	$("#btnUnAssign").attr("disabled", "disabled");
});
function SelectAll(o) {
    if (typeof o == 'undefined')
        return;
    var value = '';
    var c = document.getElementsByName("cbUser");
    for (var i = 0, n = c.length; i < n; i++) {
        c[i].checked = o.checked;
        if (o.checked) {
            value += ','+ c[i].value ;
        }
    }
    if(value.length > 0) value = value.substring('1');
    

    $Id("txthdUser").value = value;

    if (o.checked) {
        $("#btnAssign").removeAttr("disabled");
    }
    else {
        $("#btnAssign").attr("disabled", "disabled");
    }
};

function SelectAllDelete(o) {
    if (typeof o == 'undefined')
        return;
    var value = '';
    var c = document.getElementsByName("cbUserAssign");
    for (var i = 0, n = c.length; i < n; i++) {
        c[i].checked = o.checked;
        if (o.checked) {
            value +=','+ c[i].value ;         
        }
    }    
    if(value.length > 0) value = value.substring('1');
    $Id("txthdUserAssign").value = value;
    
    if (o.checked)
        $("#btnUnAssign").removeAttr("disabled");
    else
        $("#btnUnAssign").attr("disabled", "disabled");
};

function CheckItem(obj) {
    var c = document.getElementsByName("cbUser");
    var checkAll = true;
    var value = '';   
    var hasItemChecked = false;
    for (var i = 0; i < c.length; i++) {
        if (!c[i].checked) {
            checkAll = false;
        }
        else {
            value +=','+ c[i].value ;
            hasItemChecked = true;
        }
    }
    if(value.length > 0) value = value.substring('1');
    $Id("txthdUser").value = value;
 
    var o = document.getElementById("cbxSelectAll");
    if (o == null) return;
    o.checked = checkAll;

    if (hasItemChecked)
        $("#btnAssign").removeAttr("disabled");
    else
        $("#btnAssign").attr("disabled", "disabled");
};

function CheckItemDelete() {
    var c = document.getElementsByName("cbUserAssign");
    var checkAll = true;
    var value = '';
    var hasItemChecked = false;
    for (var i = 0; i < c.length; i++) {
        if (!c[i].checked) {
            checkAll = false;
        }
        else {
            value +="," + c[i].value ;          
            hasItemChecked = true;
        }
    }
    if(value.length > 0) value = value.substring('1');
    $Id("txthdUserAssign").value = value;

    var o = document.getElementById("cbUserDelete");
    if (o == null) return;
    o.checked = checkAll;
    if (hasItemChecked)
        $("#btnUnAssign").removeAttr("disabled");
    else
        $("#btnUnAssign").attr("disabled", "disabled");
};

function OnAssign() {
    var listuser = ($Id("txthdUser").value);
    if ($StringIsNullOrEmpty(listuser)) {
    	jAlert(LANG.MESSAGE.MUST_BE_CHECKED.f('{' + LANG.CONSTANT.userNotInGroup + '}'), LANG.CONSTANT.alertDialog);
    	return false;
    }
    return true;
};
function OnUnAssign() {
     var listuser = ($Id("txthdUserAssign").value);
     if ($StringIsNullOrEmpty(listuser)) {
    	 jAlert(LANG.MESSAGE.MUST_BE_CHECKED.f('{' + LANG.CONSTANT.userInGroup + '}'), LANG.CONSTANT.alertDialog);
    	return false;
     }
     return true;
 };