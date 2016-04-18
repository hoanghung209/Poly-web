<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<%@page session="true"%>
<f:set var="baseURL" value="${menu:getContextPath()}" />
<spring:url value="${baseURL}/resources/core/images" var="img" />
<spring:url value="${baseURL}/resources/core/icons" var="icon" />
<div class="transparent_header sticky_header header" id="header" >
  <div class="header_top_bar" >
  	<!-- Change Language  -->
        <div class="pull-right hidden-xs" style="display:none">
          <div class="header_language_url"> 
          	<a href=""><img onclick="changeLang('vn');" title="Vietnam" width="18" src="${baseURL}/resources/core/icons/icon-flag-vietnam.png"/></a>
          	<a href=""><img onclick="changeLang('en');" title="English" width="18" src="${baseURL}/resources/core/icons/icon-flag-english.png"/></a>
          </div>
          <script type="text/javascript">
	function changeLang(lang){		
		$.ajax({
			type: "POST",
			url: "${baseURL}/changeLanguage",
			data: "lang=" + lang,
			success: function(response){				
				location.reload(); 
			}
		});
	}	
</script>
        </div>
    <div class="container">
      <div class="clearfix">         
        <!-- Header Top bar Login -->
        <div class="pull-right hidden-xs">       
          <div class="header_login_url"> 
           <f:if test="${empty sessionScope.email}">
           <a href="#" data-toggle="modal" data-target="#LoginModal"> <i class="fa fa-user"></i>
          	 Đăng nhập </a></f:if>
          <f:if test="${not empty sessionScope.email}"> ${sessionScope.email}</f:if>          
		  <span class="vertical_divider"></span>
		   <f:if test="${empty sessionScope.email}">
		  <a href="#" data-toggle="modal" data-target="#RegisterModal">
		  Đăng ký </a></f:if>
          <f:if test="${not empty sessionScope.email}"> <a href="#" onclick="logout();">Thoát</a></f:if>
		  </div>
        </div>
        <!--MODAL LOGIN REGISTER-->
		
		<div id="RegisterModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style='font-family: "Helvetica Neue", Helvetica, Arial, sans-serif !important;'>Đăng ký</h4>
      </div>
      <div class="modal-body col-sm-12">
        <form action="" method="post" id="frmRegister">
        							<input type="hidden" id="baseURL" value="${baseURL}"> 
									<div class="form-group">										
										<input type="email" onblur="checkEmail();" class="form-control" id="txtEmail" name="txtEmail" placeholder="Email" required>
									</div>
									<div class="form-group">
										
										<input type="password" class="form-control" id="txtPassword" name="txtPassword" placeholder="Mật khẩu" required>
									</div>
										<div class="form-group">
										
										<input type="password" class="form-control" id="txtConfirmPassword" name="txtConfirmPassword" placeholder="Nhập lại mật khẩu" required>
									</div>
									<div class="form-group">
										<input type="text" class="form-control" id="txtName" name="txtName" placeholder="Họ và tên" required>
									</div>
									<div class="form-group">
									
										<input type="text" class="form-control" id="txtPhone" name="txtPhone" onkeypress="return $IsNumbers(event);" placeholder="Số điện thoại" required>
									</div>
									<div class="form-control-static text-center">
										<button type="button" onclick="registerCus();" id="btnRegister" class="btn btn-primary">Đăng ký</button>
										<input type="reset" value="Làm mới" class="btn btn-primary"/>
									</div>
								</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Thoát</button>
      </div>
    </div>

  </div>
</div>
		
		
		
		
		<div id="LoginModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style='font-family: "Helvetica Neue", Helvetica, Arial, sans-serif !important;'>Đăng nhập</h4>
      </div>
      <div class="modal-body col-sm-12">
        <form action="/" method="post" id="frmLogin">
        	<input type="hidden" id="baseURL" value="${baseURL}"> 
									<div class="form-group">
										
										<input type="email" class="form-control" id="txtLoginEmail" name="txtLoginEmail" placeholder="Email" required>
									</div>
									<div class="form-group">
										
										<input type="password" class="form-control" id="txtLoginPassword" name="txtLoginPassword" placeholder="Mật khẩu" required>
									</div>
										
									<div class="form-control-static text-center">
										<button type="button" onclick="login();" id="btnRegister" class="btn btn-primary">Đăng nhập</button>
										<button type="button" onclick="closeLogin();" data-toggle="modal" data-target="#ForgotPassword" class="btn btn-primary">Quên mật khẩu</button>
										
									</div>
								</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Thoát</button>
      </div>
    </div>

  </div>
</div>



<div id="ForgotPassword" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" style='font-family: "Helvetica Neue", Helvetica, Arial, sans-serif !important;'>Quên mật khẩu</h4>
      </div>
      <div class="modal-body col-sm-12">
        <form action="/" method="post" id="frmForgotPass">
									<div class="form-group">										
										<input type="email" class="form-control" id="txtForgotEmail" name="txtForgotEmail" placeholder="Nhập Email để khôi phục mật khẩu" required>
									</div>
									
									<div class="form-control-static text-center">
										<button type="button" onclick="forgot();" id="btnForgot" class="btn btn-primary">Gửi</button>
										<input type="reset" value="Làm mới" class="btn btn-primary"/>
									</div>
								</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Thoát</button>
      </div>
    </div>

  </div>
</div>


		
		<!--END MODAL LOGIN REGISTER-->
        <!-- Header top bar Socials -->
        
        <div class="pull-right xs-pull-left">
          <ul class="top_bar_info clearfix">
            <li><i class="fa fa-clock-o"></i> Thứ Hai - Chủ Nhật 7.30 -20.00</li>
            <li class="hidden-xs hidden-sm"><i class="fa fa-map-marker"></i> 44 Phan Khiêm Ích, Phường Tân Phong, Quận 7, HCMC</li>
            <li><i class="fa fa-phone"></i> +84 08.54107659</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="navmain header_default headerchild">
    <div class="container">
      <div class="col-md-2  col-sm-2 col-lg-2 col-xs-12 ">
        <div class="logo-unit"> <a href="${baseURL}">
          <h1><img class="img-responsive logo_transparent_static visible" src="${icon}/polylogomau.png"  alt=""> <img class="img-responsive logo_colored_fixed hidden" src="${icon}/polylogomau.png"  alt=""> </h1>
          </a> </div>
        
        <!-- Navbar toggle MOBILE -->
        <button type="button" class="navbar-toggle collapsed hidden-lg hidden-md" data-toggle="collapse" data-target="#header_menu_toggler"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      </div>
      <!-- md-3 --> 
     
      <!-- MObile menu -->
      <div class="col-xs-12 col-sm-12 visible-xs">
        <div class="collapse navbar-collapse header-menu-mobile" id="header_menu_toggler">
          <ul class="header-menu clearfix">
		  <li>
              <form role="search" method="post" id="searchform-mobile" action="${baseURL}/search">
                <div class="search-wrapper">
                  <input placeholder="Search..." type="text" class="form-control search-input" value="" id="s" name="s">
                  <button type="submit" class="search-submit"><i class="fa fa-search"></i></button>
                </div>
              </form>
            </li>
            <li ><a href="${baseURL}">Trang chủ</a></li>
                <f:forEach items="${menu:getMenu(-1,1)}" var="item">
               	<f:if test="${!menu:existSub(item.id,1)}">
               		<li><a href="${baseURL}/${item.url}/${item.slug}-${item.hashid}">${item.name}</a>
               	</f:if>
               	<f:if test="${menu:existSub(item.id,1)}">
            	<li class="menu-item-has-children"><a href="${baseURL}/${item.url}/${item.slug}-${item.hashid}">${item.name}</a>
	            	<f:if test="${menu:existSub(item.id,1)}">
		            	<ul class="sub-menu">
		            		<f:forEach items="${menu:getMenu(item.id,1)}" var="sub">           		
				               	<f:if test="${!menu:existSub(sub.id,1)}">
				               		<li><a href="${baseURL}/${sub.url}/${sub.slug}-${sub.hashid}">${sub.name}</a></li>
				               	</f:if>
			                	<f:if test="${menu:existSub(sub.id,1)}">
			                		<li class="menu-item-has-children"><a href="${baseURL}/${sub.url}/${sub.slug}-${sub.hashid}">${sub.name}</a>
				                		<ul class="sub-menu">
						            		<f:forEach items="${menu:getMenu(sub.id,1)}" var="sub2">            		
								                <li ><a href="${baseURL}/${sub2.url}/${sub2.slug}-${sub2.hashid}">${sub2.name}</a></li>
						            		</f:forEach>   
						            	</ul> 
					            	</li>
			                	</f:if>
			                
		            		</f:forEach>   
		            	</ul> 
					</f:if> 					
	            </li>
	            </f:if>
	            </f:forEach>
            
			<li>
			<a href="#" data-toggle="modal" data-target="#LoginModal"> <i class="fa fa-user"></i>
          	 Đăng nhập </a>
			</li>
			<li>
           
           
		  <a href="#" data-toggle="modal" data-target="#RegisterModal">
		  Đăng ký </a>
          
			</li>
          </ul>
        </div>
      </div>
      
      <!-- Desktop menu -->
      <div class="col-md-10 col-sm-10  hidden-xs">
        <div class="row">
          <div class="header_main_menu_wrapper clearfix" >
            <div class="pull-right hidden-xs">
              <div class="search-toggler-unit">
                <div class="search-toggler" data-toggle="modal" data-target="#searchModal"><i class="fa fa-search"></i></div>
              </div>
            </div>
            <div class="collapse navbar-collapse ">
              <ul class="header-menu clearfix">
                <li><a href="${baseURL}"><span class="fa fa-home"></span></a></li>
                <f:forEach items="${menu:getMenu(-1,1)}" var="item">
            	<li class="menu-item-has-children"><a href="${baseURL}/${item.url}/${item.slug}-${item.hashid}">${item.name}</a>
	            	<f:if test="${menu:existSub(item.id,1)}">
		            	<ul class="sub-menu">
		            		<f:forEach items="${menu:getMenu(item.id,1)}" var="sub">           		
				               	<f:if test="${!menu:existSub(sub.id,1)}">
				               		<li><a href="${baseURL}/${sub.url}/${sub.slug}-${sub.hashid}">${sub.name}</a></li>
				               	</f:if>
			                	<f:if test="${menu:existSub(sub.id,1)}">
			                		<li class="menu-item-has-children"><a href="${baseURL}/${sub.url}/${sub.slug}-${sub.hashid}">${sub.name}</a>
				                		<ul class="sub-menu">
						            		<f:forEach items="${menu:getMenu(sub.id,1)}" var="sub2">            		
								                <li ><a href="${baseURL}/${sub2.url}/${sub2.slug}-${sub2.hashid}">${sub2.name}</a></li>
						            		</f:forEach>   
						            	</ul> 
					            	</li>
			                	</f:if>
			                
		            		</f:forEach>   
		            	</ul> 
					</f:if> 
					<div class="magic_line" ></div> 
	            </li>
	            </f:forEach> 	                              
              </ul>
            </div>
          </div>
        </div>
      </div>
      <!-- md-8 desk menu --> 
    </div>
  </div>
  
  <!--row--> 
  
</div>	
<div class="modal fade in" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="searchModal">
  <div class="modal-dialog  fade in" role="document">
    <div class="modal-content">
      <div class="modal-body heading_font">
        <div class="search-title">Tìm kiếm</div>
        <form role="search" method="post" id="searchform" action="${baseURL}/search">
          <div class="search-wrapper">
            <input placeholder="Nhập từ khóa tìm kiếm..." type="text" class="form-control search-input" value="" name="s" id="s">
            <button type="submit" class="search-submit"><i class="fa fa-search"></i></button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="popup_common" tabindex="-1" role="dialog" aria-labelledby="popup_common_title" style="margin-top: 250px;">
		<div class="modal-dialog modal-dialog-cancel" role="document">
			<div class="modal-content modal-content-cancel">
				<div class="modal-header">										
					<h4 class="modal-title" id="popup_common_title">Dialog of xyz</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group form-group-cancel">
							<div class="lable-close-confir" id="popup_common_message">Content</div>																																								
						</div>											  
					</form>
				</div>
				<div class="modal-gift modal-cancel" style="padding-left: 265px;padding-bottom: 10px;">										
					<button type="button" class="btn btn-primary btn-gift-white" id="confirm_common_dlg">Xác nhận</button>
				</div>										    
			</div>
		</div>
	</div>
	
    <!-- Modal -->
<f:if test="${requestScope.popup.is_once==0&&sessionScope.popup_once!=true}">
<div id="myModal" class="modal fade in" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"></h4>
      </div>
      <div class="modal-body">
        <p><a href="${requestScope.popup.url}" target="${requestScope.popup.target}">
         <f:if test="${requestScope.popup.isImg}"><img src="${requestScope.popup.content}" class="img-responsive" /></f:if>
         <f:if test="${!requestScope.popup.isImg}">${requestScope.popup.content}</f:if>
        </a></p>
      </div>
      <div class="modal-footer">
        
      </div>
    </div>

  </div>
</div>
 <script type="text/javascript">
	 				 $(document).ready(function() { 
							$('#myModal').modal('show');
					});
				</script> 
<% session.setAttribute("popup_once",true);%>
</f:if>
  <!-- /.modal -->