<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="menu" uri="/WEB-INF/mytag/MenuHelper.tld" %>
<c:set var="baseURL" value="${menu:getContextPath()}" />  
      <div class="thuvien clearfix">
           <div class="title1 clearfix">
      		<h2>Thư viện Poly</h2>
    	
    		</div>
    		   <ul class="nav nav-pills">
          <li class="active"><a data-toggle="pill" href="#tvanh">hình ảnh
                <div class="magic_line" ></div>
                
                </a></li>
          <li><a data-toggle="pill" href="#tvvideo">video
                <div class="magic_line" ></div></a></li>
        </ul>
          <div class="tab-content">
          <div id="tvanh" class="tab-pane fade in active">
              
            <ul class="row listanh">
            <c:set var="dem" value="1"></c:set>
            <c:forEach items="${lstImage}" var="img">
             <li class=" col-sm-4 col-md-4">
                <div class="clearfix pic-hv">
                  <a href="${baseURL}/image/${img.slug}-${img.hashid}"><img src="${img.img_avatar}"  alt="">
                  <div class="text-hv">
                    <span>${img.title }</span>
                  </div>
                  </a>
                </div>
                
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
                <div class="clearfix pic-hv">
                  <a href="${baseURL}/video/${video.slug}-${video.hashid}"><img src="${video.img_avatar}"  alt="">
                  <div class="text-hv">
                    <span class="fa fa-play-circle"></span>
                  </div>
                  </a>
                </div>
                
              </li>
               
          <c:set var="dem" value="${dem+1 }"></c:set>
              </c:forEach>
              
              </ul>
              </div>                 
        </div>
        </div>  
    <!--endcontentmain--> 
    