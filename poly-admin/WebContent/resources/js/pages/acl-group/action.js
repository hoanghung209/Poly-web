$(function(){
	$("#btnRestrict").attr("disabled", "disabled");
	$("#btnAllow").attr("disabled", "disabled");
	
	$("#btnRemoveRestrict").attr("disabled", "disabled");
	$("#btnRemoveAllow").attr("disabled", "disabled");
});
function SelectAllRestrictAction(o) {
    if (typeof o == 'undefined')
        return;
    var value = '';
    var c = document.getElementsByName("cbRestrictAction");
    for (var i = 0, n = c.length; i < n; i++) {
        c[i].checked = o.checked;
        if (o.checked) {
            value += ','+ c[i].value ;
        }
    }
    if(value.length > 0) value = value.substring('1');
    

    $Id("txthdRestrictAction").value = value;

    if (o.checked) {
        $("#btnRemoveRestrict").removeAttr("disabled");
    }
    else {
        $("#btnRemoveRestrict").attr("disabled", "disabled");
    }
};
function CheckItemRestrictAction() {
    var c = document.getElementsByName("cbRestrictAction");
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
    $Id("txthdRestrictAction").value = value;

    var o = document.getElementById("cbAllRestrictAction");
    if (o == null) return;
    o.checked = checkAll;
    if (hasItemChecked)
        $("#btnRemoveRestrict").removeAttr("disabled");
    else
        $("#btnRemoveRestrict").attr("disabled", "disabled");
};


function SelectAllAllowAction(o) {
    if (typeof o == 'undefined')
        return;
    var value = '';
    var c = document.getElementsByName("cbAllowAction");
    for (var i = 0, n = c.length; i < n; i++) {
        c[i].checked = o.checked;
        if (o.checked) {
            value +=','+ c[i].value ;         
        }
    }    
    if(value.length > 0) value = value.substring('1');
    $Id("txthdAllowAction").value = value;
    
    if (o.checked)
        $("#btnRemoveAllow").removeAttr("disabled");
    else
        $("#btnRemoveAllow").attr("disabled", "disabled");
};

function CheckItemAllowAction() {
    var c = document.getElementsByName("cbAllowAction");
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
    $Id("txthdAllowAction").value = value;

    var o = document.getElementById("cbAllAllowAction");
    if (o == null) return;
    o.checked = checkAll;
    if (hasItemChecked)
        $("#btnRemoveAllow").removeAttr("disabled");
    else
        $("#btnRemoveAllow").attr("disabled", "disabled");
};

function SelectAllFreeAction(o) {
    if (typeof o == 'undefined')
        return;
    var value = '';
    var c = document.getElementsByName("cbFreeAction");
    for (var i = 0, n = c.length; i < n; i++) {
        c[i].checked = o.checked;
        if (o.checked) {
            value +=','+ c[i].value ;         
        }
    }    
    if(value.length > 0) value = value.substring('1');
    $Id("txthdFreeAction").value = value;
    
    if (o.checked) {
        $("#btnRestrict").removeAttr("disabled");
    	$("#btnAllow").removeAttr("disabled");
    } else {
    	$("#btnRestrict").attr("disabled", "disabled");
    	$("#btnAllow").attr("disabled", "disabled");
    }        
};

function CheckItemFreeAction() {
    var c = document.getElementsByName("cbFreeAction");
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
    $Id("txthdFreeAction").value = value;

    var o = document.getElementById("cbAllFreeAction");
    if (o == null) return;
    o.checked = checkAll;
    if (hasItemChecked) {
        $("#btnRestrict").removeAttr("disabled");
		$("#btnAllow").removeAttr("disabled");
    } else {
    	$("#btnRestrict").attr("disabled", "disabled");
    	$("#btnAllow").attr("disabled", "disabled");
    }
};

function OnRestrict() {
    var lstId = ($Id("txthdFreeAction").value);
    if ($StringIsNullOrEmpty(lstId)) {
		jAlert(LANG.MESSAGE.MUST_BE_CHECKED.f('{' + LANG.CONSTANT.actionNotUsed + '}'), LANG.CONSTANT.alertDialog);
    	return false;
    }
    return true;
};
function OnAllow() {
     var lstId = ($Id("txthdFreeAction").value);
     if ($StringIsNullOrEmpty(lstId)) {
    	 jAlert(LANG.MESSAGE.MUST_BE_CHECKED.f('{' + LANG.CONSTANT.actionNotUsed + '}'), LANG.CONSTANT.alertDialog);
    	return false;
     }
     return true;
 };

 function OnRemoveRestrict() {
     var lstId = ($Id("txthdRestrictAction").value);
     if ($StringIsNullOrEmpty(lstId)) {
    	 jAlert(LANG.MESSAGE.MUST_BE_CHECKED.f('{' + LANG.CONSTANT.actionRestrict + '}'), LANG.CONSTANT.alertDialog);
     	return false;
     }
     return true;
 };
 function OnRemoveAllow() {
      var lstId = ($Id("txthdAllowAction").value);
      if ($StringIsNullOrEmpty(lstId)) {
    	  jAlert(LANG.MESSAGE.MUST_BE_CHECKED.f('{' + LANG.CONSTANT.actionAllow + '}'), LANG.CONSTANT.alertDialog);
     	return false;
      }
      return true;
  };