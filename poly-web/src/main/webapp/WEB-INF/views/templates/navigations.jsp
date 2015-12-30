<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<spring:url value="/resources/core/images" var="img" />
<spring:url value="/resources/core/icons" var="icon" />
<%@page session="true"%>
<c:set var="baseURL" value="${pageContext.request.requestURL.substring(0, pageContext.request.requestURL.length() - pageContext.request.requestURI.length())}${pageContext.request.contextPath}/" />
<div class="transparent_header sticky_header header" id="header" >
  <div class="header_top_bar" >
  	<!-- Change Language  -->
        <div class="pull-right hidden-xs" style="display:none">
          <div class="header_language_url"> 
          	<a href=""><img onclick="changeLang('vn');" title="Vietnam" width="18" src="/poly-web/resources/core/icons/icon-flag-vietnam.png"/></a>
          	<a href=""><img onclick="changeLang('en');" title="English" width="18" src="/poly-web/resources/core/icons/icon-flag-english.png"/></a>
          </div>
          <script type="text/javascript">
	function changeLang(lang){		
		$.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath}/changeLanguage",
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
          <div class="header_login_url"> <a href=""> <i class="fa fa-user"></i>Đăng nhập </a> <span class="vertical_divider"></span> <a href="">Đăng ký</a> </div>
        </div>
        
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
        <div class="logo-unit"> <a href="/poly-web">
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
            <li ><a href="/">Trang chủ</a></li>
                <f:forEach items="${menu:getMenu(-1,1)}" var="item">
               	<f:if test="${!menu:existSub(item.id,1)}">
               		<li><a href="${item.url}">${item.name}</a>
               	</f:if>
               	<f:if test="${menu:existSub(item.id,1)}">
            	<li class="menu-item-has-children"><a href="${item.url}">${item.name}</a>
	            	<f:if test="${menu:existSub(item.id,1)}">
		            	<ul class="sub-menu">
		            		<f:forEach items="${menu:getMenu(item.id,1)}" var="sub">           		
				               	<f:if test="${!menu:existSub(sub.id,1)}">
				               		<li><a href="${sub.url}">${sub.name}</a></li>
				               	</f:if>
			                	<f:if test="${menu:existSub(sub.id,1)}">
			                		<li class="menu-item-has-children"><a href="${sub.url}">${sub.name}</a>
				                		<ul class="sub-menu">
						            		<f:forEach items="${menu:getMenu(sub.id,1)}" var="sub2">            		
								                <li ><a href="${sub2.url}">${sub2.name}</a></li>
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
              <form role="search" method="get" id="searchform-mobile" action="http://masterstudy.stylemixthemes.com/">
                <div class="search-wrapper">
                  <input placeholder="Search..." type="text" class="form-control search-input" value="" name="s">
                  <button type="submit" class="search-submit"><i class="fa fa-search"></i></button>
                </div>
              </form>
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
                <li><a href="/poly-web"><span class="fa fa-home"></span></a></li>
                <f:forEach items="${menu:getMenu(-1,1)}" var="item">
            	<li class="menu-item-has-children"><a href="${item.url}">${item.name}</a>
	            	<f:if test="${menu:existSub(item.id,1)}">
		            	<ul class="sub-menu">
		            		<f:forEach items="${menu:getMenu(item.id,1)}" var="sub">           		
				               	<f:if test="${!menu:existSub(sub.id,1)}">
				               		<li><a href="${sub.url}">${sub.name}</a></li>
				               	</f:if>
			                	<f:if test="${menu:existSub(sub.id,1)}">
			                		<li class="menu-item-has-children"><a href="${sub.url}">${sub.name}</a>
				                		<ul class="sub-menu">
						            		<f:forEach items="${menu:getMenu(sub.id,1)}" var="sub2">            		
								                <li ><a href="${sub2.url}">${sub2.name}</a></li>
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