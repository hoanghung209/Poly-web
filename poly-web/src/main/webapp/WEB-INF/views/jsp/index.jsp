<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<c:set var="baseURL" value="${menu:getContextPath()}" />
<style>
.headerchild{
	position: absolute;
	padding: 0px;
	background: transparent;
}
.headerchild ul>li>a{
	color: #fff !important;
    font-weight: bold;
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif !important;
}
.header_main_menu_wrapper .header-menu > li > ul.sub-menu > li a{
	color:#878787 !important;
}
.header_default.fixed ul>li a{
	color:#009DEF !important;
}
.modal{
z-index:9999999;
}
@media (max-width:768px){
.headerchild{
position:static ;
}
.header_default ul>li a {
    color: #009DEF !important;
}
}


</style>

<div class="slider">
  <div class="tp-banner-container">
    <div class="tp-banner" >
      <ul>
        <!-- SLIDE  --> 
        <!-- SLIDE  -->
        <c:forEach items="${lstBanner }" var="slide">
        <li data-transition="fade" data-masterspeed="1000" data-delay="6000" > 
          <!-- MAIN IMAGE --> 
          <img src="${slide.image }"  alt="slidebg1"  data-bgfit="contain" data-bgposition="left top" data-bgrepeat="no-repeat" width="100%"> 
          <!-- LAYERS --> 
          <!-- LAYER NR. 1 -->
          <div class="tp-caption lightgrey_divider skewfromrightshort sft  fadeout caption1"
						data-customin="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
						data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0;scaleY:0;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
						data-speed="500"
						data-start="1200"
						data-easing="Power3.easeInOut"
						data-splitin="chars"
						data-splitout="chars"
						data-elementdelay="0.08"
						data-endelementdelay="0.08"
						style="z-index: 2; max-width: auto; max-height: auto; white-space: nowrap;top: ${slide.desc_top};left: ${slide.desc_left};text-align:${slide.text_align}">${slide.desc } </div>
          <div class="tp-caption lightgrey_divider skewfromrightshort fade fadein caption2"
						data-speed="500"
						data-start="1300"
						data-easing="Power4.easeOut"
						style="top: ${slide.more_top};left: ${slide.more_right};"><a target="_blank" href="${baseURL}/${slide.url}"><span>${slide.more}</span></a></div>
        </li>
      </c:forEach>
      </ul>
      <div class="tp-bannertimer"></div>
    </div>
  </div>
 


  <script type="text/javascript">
 				 $(document).ready(function() {  						
						   $('.tp-banner').revolution(
						{
							delay:6000,
							startwidth:1124,
							startheight:591,
							fullWidth:"on",
						    autoHeight:"on",
						navigationType:"bullet",
						navigationArrows:"solo",
						navigationStyle:"round",
							hideBulletsOnMobile:"off",
						hideArrowsOnMobile:"off"							
						});						
				});
			</script> 			
  <!-- END REVOLUTION SLIDER --> 
</div>
<!--slide-->
<div class="container">
  <div class="boxborder clearfix overflowed_content">
    <div class="row">
    <c:forEach items="${lstIcon}" var="icon">
      <div class="wpb_column col-sm-3 icon_box "> <a href=" ${icon.anchor }"><img src=" ${icon.image }"/>
        ${icon.content }
        </a> </div>
      </c:forEach>      
    </div>
  </div>
  <!--end khoa hoc Poly-->
  <div class="boxborder clearfix boxwhy">
    <div class="title1 clearfix">
     ${caption }
    </div>
    <div class="row why">
      <ul>
      <c:forEach items="${lst13}" var="item">
        <li class="col-md-15  col-sm-6">
          <div class=" clearfix textinfo">
            <div class="pic"> <img src="${item.image}" class="img-responsive" alt=""> </div>
          	${item.content }
           <div class="show_more"> <a target="_blank" class="btn btn-default" href="${item.anchor }" title="View more">Xem thêm</a> </div>
          </div>
        </li>
        </c:forEach>
      </ul>
    </div>
    <div class="info-2 clearfix">
      <div class="row">
        <div class="col-md-6">
          <div class="embed-responsive embed-responsive-16by9">
            <iframe src="https://www.youtube.com/embed/9rpWzl-6AiM"allowfullscreen></iframe>
          </div>
        </div>
        <div class="col-md-6">
          <div class="tit1">
            <h3>cảm nghĩ <span>của phụ huynh</span></h3>
          </div>
          <div class="jcarousel-wrapper camnghi">
            <div class="jcarousel">
              <ul>
              <c:forEach items="${lstFeedback}" var="feed">
                <li> <img class="img-circle"  src="${feed.avatar}" alt="">
                  <p class="name">${feed.name }</p>
                  <p>${feed.chuc }</p>
                  <p class="text-justify">${feed.content }</p>
                </li>   
                </c:forEach>            
              </ul>
            </div>
            <a href="#" class="jcarousel-control-prev">&lsaquo;</a> <a href="#" class="jcarousel-control-next">&rsaquo;</a> </div>
        </div>
      </div>
    </div>
  </div>
  <!--end taisaochon Poly-->
  <div class="thuvien clearfix">
    <div class="title1 clearfix">
      <h2>Thư viện Poly</h2>
    </div>
    <ul class="nav nav-pills">
      <li class="active"><a data-toggle="pill" href="#tvanh">hình ảnh
        <div class="magic_line" ></div>
        </a></li>
      <li><a data-toggle="pill" href="#tvvideo">video
        <div class="magic_line" ></div>
        </a></li>
    </ul>
    <div class="tab-content">
      <div id="tvanh" class="tab-pane fade in active">
        <ul class="row listanh">
        <c:set var="dem" value="1"></c:set>
        <c:forEach items="${lstImage}" var="img">
          <li class=" col-sm-4 col-md-4">
            <div class="clearfix pic-hv"> <a href="${baseURL}/image/${img.slug}-${img.hashid}"><img src="${img.img_avatar}"  alt="">
              <div class="text-hv"> <span>${img.title}</span> </div>
              </a> </div>
          </li>
          
          <c:set var="dem" value="${dem+1 }"></c:set>
         </c:forEach>         
        </ul>
      </div>
      <div id="tvvideo" class="tab-pane">
        <ul class="row listanh">
        <c:set var="dem" value="1"></c:set>
        <c:forEach items="${lstVideo}" var="video">
          <li class=" col-sm-4 col-md-4">
            <div class="clearfix pic-hv"> <a href="${baseURL}/video/${video.slug}-${video.hashid }"><img src="${video.img_avatar }"  alt="">
              <div class="text-hv"> <span class="fa fa-play-circle"></span> </div>
              </a> </div>
          </li>
          
          <c:set var="dem" value="${dem+1 }"></c:set>
          </c:forEach>       
        </ul>
      </div>
    </div>
  </div>
</div>
<style>
.img_pop img{
width:100%;
}
</style>
<!-- Modal -->
<c:if test="${requestScope.popup.is_once==1}">
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
         <c:if test="${requestScope.popup.isImg}"><img src="${requestScope.popup.content}" class="img-responsive" /></c:if>
         <c:if test="${!requestScope.popup.isImg}">${requestScope.popup.content}</c:if>
        </a></p>
      </div>
      <div class="modal-footer">
        
      </div>
    </div>

  </div>
</div>
<script type="text/javascript">
	 				 $(document).ready(function() { 					 
	 					//modal						
							$('#myModal').modal('show');
					});
				</script> 
</c:if>
  <!-- /.modal -->
  