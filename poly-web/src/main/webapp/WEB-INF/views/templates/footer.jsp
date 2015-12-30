<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<spring:url value="/resources/core/images/" var="img" />
<c:set var="baseURL" value="${pageContext.request.requestURL.substring(0, pageContext.request.requestURL.length() - pageContext.request.requestURI.length())}${pageContext.request.contextPath}/" />
<div class="footer">
  <div class="linkfooter ">
    <div class="container">
      <div class="row">
      <c:forEach items="${menu:getMenu(-1,2)}" var="item">
        <div class="col-sm-4">
          <div class="simple_flipper"> <a href="${item.url}" target="_blank"><i class="fa fa-tvi front"></i>${item.name}</a> </div>
        </div>
       </c:forEach>
      </div>
    </div>
  </div>
  <div class="inforfooter container">
    <ul class="clearfix" style="text-transform: uppercase;">
    	<c:forEach items="${menu:getMenu(-1,3)}" var="item">
    		<li><a href="${item.url}">${item.name}</a></li>
    	</c:forEach>     
    </ul>
    <div class="clearfix infof">
      <p>TRUNG TÂM ANH NGỮ POLY</p>
      <p> 44 Phan Khiêm Ích, Phường Tân Phong, Quận 7, HCMC, Việt Nam<br/>
        Tel: +84 08.54107659   |   Email: tracy.tu@koreapolyschool.com</p>
      <p class="social"><a target="_blank" href="https://www.facebook.com/polyflc/?fref=ts"><span class="fa fa-facebook"></span></a><a target="_blank" href="https://www.youtube.com/channel/UCVz8xug416bugRM_9Wote8g"> <span class="fa fa-youtube"></span></a></p>
    </div>
  </div>
  <ul id="right_action_bar">
	<li>
	<a href="#" target="_blank" onclick="
        window.open(
        'https://www.facebook.com/sharer/sharer.php?u=' + encodeURIComponent(window.location.href),
        'fb-share-dialog', 'width=626,height=436'); return false;" data-toggle="popover" data-placement="left" data-content="Share facebook" title="Share lên Facebook">
		<i class="fa fa-lg fa-facebook"></i>
	</a>
	</li>
		<li>
	<a href="#" target="_blank" onclick="
        window.open(
        'https://plus.google.com/share?url=' + encodeURIComponent(window.location.href),
        'width=626,height=436'); return false;" data-toggle="popover" data-placement="left" data-content="Share google" title="Share lên Google+">
		<i class="fa fa-lg fa-google"></i>
	</a>
	</li>
		<li>
	<a href="#" id="click_to_top" title="Lên đầu trang">
		<i class="fa fa-lg fa-chevron-up"></i>
	</a>
	</li>
  </ul>
  <script>
  $(document).ready(function(){
	  $('#right_action_bar').fadeOut();
	  $(window).scroll(function (){
		if ($(this).scrollTop() > $('.header_top_bar').height())
		{
			$('#right_action_bar').fadeIn();
		} else
		{
			$('#right_action_bar').fadeOut();
		}
	});
$('#click_to_top').click(function ()
	{
		$("html, body").animate(
			{
				scrollTop: 0
			}, 600);
		return false;
	});
  });
  </script>
</div>