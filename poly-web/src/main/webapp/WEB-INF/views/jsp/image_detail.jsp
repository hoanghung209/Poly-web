<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <div class="col-sm-9 contentmainright">
           <div class="title1 clearfix">
      		<h2 style="font-size:32px;">${mapImage.title }</h2>
			</div>
    		  
          <div class="tab-content">
          <div id="tv_anh">
              <!--myCarousel-->
			  
			  <div id="myCarousel" class="carousel slide" data-ride="carousel" style="padding:5px;">
			  

				<div class="carousel-inner" role="listbox">
				<c:set var="cnt" value="1"></c:set>
				
				<c:forEach items="${lstImage}" var="img">
					<div class="item <c:if test='${cnt==1 }'> active</c:if>">
					<img src="${img.url}" alt="${img.alt}" title="${img.title}" class="img-responsive" />
					<div class="carousel-caption">
					  <h3></h3>
					  <p></p>
					</div>
				  </div>
				  <c:set var="cnt" value="${cnt+1}"></c:set>
				</c:forEach>
  
				</div>

				<!-- Left and right controls -->
				
				<div>
				<a style="background:transparent;" class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
				  <span class="" aria-hidden="true"></span>
				  <span class="sr-only">Previous</span>
				  <i class="fa fa-lg fa-chevron-left" style="top: 50%;left: 10%;position: absolute;border: 2px solid #fff;padding: 8px;border-radius: 50px;padding-right: 10px;text-align: center;"></i>
				</a>
				<a style="background:transparent;" class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
				  <span class="" aria-hidden="true"></span>
				  <span class="sr-only">Next</span>
				  <i class="fa fa-lg fa-chevron-right" style="top: 50%;right: 10%;position: absolute;border: 2px solid #fff;padding: 8px;border-radius: 50px;padding-left: 10px;text-align: center;"></i>
				</a>
				</div>
				
				<!--myCarousel-->
				<ol class="carousel-indicators">
				<c:set var="mycnt" value="0"></c:set>
				<c:forEach items="${lstImage}" var="img">
				  <li data-target="#myCarousel" data-slide-to="${mycnt}" <c:if test="${mycnt==0 }">class="active"</c:if>></li>
				  <c:set var="mycnt" value="${mycnt+1 }"></c:set>				 
				 </c:forEach>
				</ol>
			</div>
			  
            </div>                      
        </div>
    </div>
    