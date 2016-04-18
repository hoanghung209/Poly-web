
$IsNumbers = function(e) {	
	e = e || window.event;
	if (e.charCode == 0)
		return;	
	if (e.charCode < 48 || e.charCode > 57)
		return false;
	
}
function showpopupCommon(title, message, onclose){
	$('#popup_common').unbind();
	$("#popup_common_title").text(title);
	$("#popup_common_message").text(message);
	$("#popup_common").modal('show');
	if(onclose != null){
		$("#confirm_common_dlg").click(function(){
			$('#popup_common').on('hidden.bs.modal', onclose);
			$("#popup_common").modal('hide');
		});
	}else{
		$("#confirm_common_dlg").click(function(){
			$("#popup_common").modal('hide');
		});
	}
	
}
function validateRegister(){	
	var baseURL = $('#baseURL').val();
	var fullname = $('#regfullname').val();	
	if(fullname.trim() == ''){
		showpopupCommon("Cảnh báo","Tên không được để trống");
		return ;
	}
	var phone = $('#regphone').val();	
	var email = $('#regemail').val();
	if(phone.trim() == ''&&email.trim()==''){
		showpopupCommon("Cảnh báo","Phone hoặc email không được để trống");
		return ;
	}
	if(!validateEmail(email)){
		showpopupCommon("Cảnh báo","Email không đúng định dạng");
		return;
	}
	var address = $('#regaddress').val();	
	 var params = "fullname="+fullname+"&phone="+phone+"&email="+email+"&address="+address;
		$.ajax({
				url : baseURL+"/regtest",
				type : "POST",		
				data : params,	
				success : function(response) {	
					var err_cmt = "";
					switch (response.trim()) {	
						case '1': 
							showpopupCommon("Thông báo","Đăng ký thành công. Chúng tôi sẽ liên hệ với bạn trong 24h.");
							$('#regfullname').val('');
							$('#regphone').val('');
							$('#regemail').val('');
							$('#regaddress').val('');
							break;	
						default : 
							showpopupCommon("Thông báo","Hệ thống đang bận. Vui lòng thử lại sau !");
							break;										
					}					
				}
			});	
	
}
function validateFeedback(){	
	var name = $('#txtFeedbackName').val();	
	var baseURL = $('#baseURL').val();
	if(name.trim() == ''){
		showpopupCommon("Cảnh báo","Tên không được để trống");
		return ;
	}
	var phone = $('#txtFeedbackPhone').val();	
	if(phone.trim() == ''){
		showpopupCommon("Cảnh báo","Phone không được để trống");
		return ;
	}
	var mes = $('#txtFeedbackMes').val();
	if(mes.trim() == ''){
		showpopupCommon("Cảnh báo","Thông điệp không được để trống");
		return ;
	}
	 var params = "txtName="+name+"&txtPhone="+phone+"&txtMes="+mes;
		$.ajax({
				url : baseURL+"/feedback",
				type : "POST",		
				data : params,	
				success : function(response) {	
					var err_cmt = "";
					switch (response.trim()) {	
						case '1': 
							showpopupCommon("Thông báo","Cảm ơn bạn đã gửi thông điệp cho chúng tôi.");
							$('#txtFeedbackName').val('');
							$('#txtFeedbackPhone').val('');
							$('#txtFeedbackMes').val('');							
							break;	
						default : 
							showpopupCommon("Thông báo","Hệ thống đang bận. Vui lòng thử lại sau !");
							break;										
					}					
				}
			});	
	
}

function registerCus(){
	var email = $('#txtEmail').val();	
	var pass = $('#txtPassword').val();
	var repass = $('#txtConfirmPassword').val();
	var name = $('#txtName').val();
	var phone = $('#txtPhone').val();
	var baseURL = $('#baseURL').val();
	if(email.trim() ==''){
		showpopupCommon("Cảnh báo","Email không được để trống");
		return;
	}
	if(!validateEmail(email)){
		showpopupCommon("Cảnh báo","Email không đúng định dạng");
		return;
	}
	if(pass.trim().length < 6){
		showpopupCommon("Cảnh báo","Mật khẩu quá ngắn");
		return;
	}
	
	if(pass!=repass){
		showpopupCommon("Cảnh báo","Nhập lại mật khẩu không đúng");
		return ;
	}
	var params = "txtEmail="+email+"&txtPassword="+pass+"&txtName="+name+"&txtPhone="+phone;
	$.ajax({
			url : baseURL+"/registerCus",
			type : "POST",		
			data : params,	
			success : function(response) {	
				var err_cmt = "";
				switch (response.trim()) {	
					case '1': 
						showpopupCommon("Thông báo","Bạn đã đăng ký thành công.");												
						break;	
					case '2': 
						showpopupCommon("Thông báo","Email đã tồn tại.");												
						break;	
					default : 
						showpopupCommon("Thông báo","Hệ thống đang bận. Vui lòng thử lại sau !");
						break;										
				}		
				$('#RegisterModal').modal('toggle');
			}
		});	
	
}
function checkEmail(){
	var email = $('#txtEmail').val();
	var baseURL = $('#baseURL').val();	
	
	var params = "txtEmail="+email;
	$.ajax({
			url : baseURL+"/checkEmail",
			type : "POST",		
			data : params,	
			success : function(response) {	
				var err_cmt = "";
				switch (response.trim()) {	
					case '1': 
						showpopupCommon("Thông báo","Email đã tồn tại");
						$('#txtEmail').val('');
						break;							
				}					
			}
		});	
	
}

function login(){
	var email = $('#txtLoginEmail').val();
	var pass = $('#txtLoginPassword').val();
	if(email.trim()==''||pass.trim()==''){
		showpopupCommon("Cảnh báo","Email hoặc mật khẩu trống");
		return;
	}
	
	var baseURL = $('#baseURL').val();	
	var url = window.location.href;
	var params = "txtEmail="+email+"&txtPassword="+pass;
	$.ajax({
			url : baseURL+"/login",
			type : "POST",		
			data : params,	
			success : function(response) {	
				var err_cmt = "";
				switch (response.trim()) {	
					case '0': 
						showpopupCommon("Thông báo","Email hoặc mật khẩu không đúng !");						
						break;	
					case '1':
						$('#LoginModal').modal('toggle');
						window.location = url;
						break;
				}	
				
			}
		});	
	
}
function logout(){	
	var baseURL = $('#baseURL').val();	
	var url = window.location.href;
	$.ajax({
			url : baseURL+"/logout",
			type : "POST",
			success : function(response) {	
				var err_cmt = "";
				window.location = url;
			}
		});	
	
}
function forgot(){		
	var email = $('#txtForgotEmail').val();
	var baseURL = $('#baseURL').val();	
	if(email.trim() ==''){
		showpopupCommon("Cảnh báo","Email không được để trống");
		return;
	}
	if(!validateEmail(email)){
		showpopupCommon("Cảnh báo","Email không đúng định dạng");
		return;
	}
	var params = "txtEmail="+email;
	var url = window.location.href;
	$.ajax({
			url : baseURL+"/forgot",
			type : "POST",
			data : params,	
			success : function(response) {	
				$('#ForgotPassword').modal('toggle');
				switch (response.trim()) {	
					case '1': 
						showpopupCommon("Thông báo","Mật khẩu mới đã được gửi tới "+email+" !");				
						break;
					case '0':	
						showpopupCommon("Thông báo","Email không tồn tại !");
						break;
					}						
			}
		});		
}
function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
function closeLogin(){
	$('#LoginModal').modal('toggle');
}
function setCookie(cname, cvalue, exhours) {
    var d = new Date();
    d.setTime(d.getTime() + (exhours*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}