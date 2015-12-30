<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <div class="col-sm-9 contentmainright">
       <c:set var="cnt6" value="1"></c:set>
           <c:forEach items="${lstContent }" var="item">
           <c:choose>
	         <c:when test="${item.type==1}"> <h1 class="titlecontent">${item.content}</h1></c:when>
	         <c:when test="${item.type==2}"> <p>${item.content}</p></c:when>
	         <c:when test="${item.type==3}"><c:if test="${not empty item.anchor}"><a name="${item.anchor}"></a></c:if>  <h2 class="titlecontent2">${item.content}</h2></c:when>
	         <c:when test="${item.type==5}"> <p><img src="${item.content}" alt="" class="img-responsive"></p></c:when>
	         <c:when test="${item.type==11}">
	         	<c:if test="${cnt11==1}"><div class="row view clearfix"></c:if>
                    <div class="clearfix col-md-6 col-xs-12 link-view">
                        <a class="bg-a" href="${item.anchor}"> <span class="fa fa-angle-right"></span><span>${item.content}</span></a>
                    </div>
                
                <c:if test="${cnt11==size11}"></div></c:if>
                <c:set var="cnt11" value="${cnt11+1}"></c:set>
	         </c:when>
	         <c:when test="${item.type==12}">
	         	<c:if test="${cnt12==1}"><div class="row pic-icon"></c:if>
                    <div class="col-md-15 col-xs-6">
                    <img src="${item.image}" class="img-responsive" alt="">
                    <p class="lead">${item.content}</p>
                </div>
               
                <c:if test="${cnt12==size12}"></div></c:if>
                <c:set var="cnt12" value="${cnt12+1}"></c:set>
	         </c:when>
	         <c:when test="${item.type==10}"> 
	         
	         <div class="row">
	         				<c:if test="${item.img_pos=='0'}">
	                             <div class="col-md-${item.width}">
	                                <img src="${item.image }" class="img-responsive" alt="">
	                             </div>
                             </c:if>
                             <div class="col-md-${item.text_width}">
                                ${item.content}
                             </div>
                             <c:if test="${item.img_pos=='1'}">
	                             <div class="col-md-${item.width}">
	                                <img src="${item.image }" class="img-responsive" alt="">
	                             </div>
                             </c:if>
             </div>
	         
	         
	         </c:when>
	         <c:when test="${item.type==6}">
	         	<c:if test="${cnt6==1}">
	         		<div class=" clearfix divmuiten">
                                <ul class="mt-pas">                    
	         	</c:if>
	         		<li class="col-md-3">
                        <div class="text-center">
                            <p class="bg-mt-pas<c:if test='${cnt6>1}'>${cnt6-1}</c:if> text-justify">${item.content}</p>
                            <div class="radio">
                                <img src="/poly-web/resources/core/images/elip-${cnt6}.png" class="img-responsive" alt="" />
                            </div>
                        </div>
                    </li>                 
                 <c:if test="${cnt6==mapType.type6}">
	         		 </ul>
                  		<div class="muiten "> </div>
                  	</div>                 
	         	</c:if>
	         	<c:set var="cnt6" value="${cnt6+1}"></c:set>
	         </c:when>	
	         <c:when test="${item.type==7}"> <ul class="ulcolor">${item.content}</ul></c:when>
	         <c:when test="${item.type==8}">
	         	<c:set var="monhoc" value="${fn:split(item.content,';hmh;')}"></c:set>
	         	<c:set var="cnt8" value="1"></c:set>
	         	<div class=" clearfix row monhoc view">
	         		<c:forEach items="${monhoc}" var="nd">
	         			<div class="col-sm-3 text-center "><a href="#"><img src="/poly-web/resources/core/images/ctpas${cnt8}.png" class="img-responsive dislay" alt=""><p class="ctpas">${nd }</p></a></div>
	         			<c:set var="cnt8" value="${cnt8+1}"></c:set>
	         		</c:forEach>
	         	</div>
	         </c:when>
	       	</c:choose>         
         </c:forEach>  
         <c:set var="cnt9" value="1"></c:set>        
         <c:if test="${showStep=='0'}">
         	<div class=" view">                           
                                <ul class="step">
                                     <li class="active col-md-3 col-xs-6"><a data-toggle="pill" href="#step1"><img src="/poly-web/resources/core/images/step-1.png" class="img-responsive imgactive" alt=""><img src="/poly-web/resources/core/images/step-1-bg.png" class="img-responsive imgdefaut" alt=""></a></li>
                                     <li class="col-md-3 col-xs-6"><a data-toggle="pill" href="#step2"><img src="/poly-web/resources/core/images/step-2.png" class="img-responsive imgactive" alt=""><img src="/poly-web/resources/core/images/step-2-bg.png" class="img-responsive imgdefaut" alt=""></a></li>
                                     <li class="col-md-3 col-xs-6"><a data-toggle="pill" href="#step3"><img src="/poly-web/resources/core/images/step-3.png" class="img-responsive imgactive" alt=""><img src="/poly-web/resources/core/images/step-3-bg.png" class="img-responsive imgdefaut" alt=""></a></li>
                                     <li class="col-md-3 col-xs-6"><a data-toggle="pill" href="#step4"><img src="/poly-web/resources/core/images/step-4.png" class="img-responsive imgactive" alt=""><img src="/poly-web/resources/core/images/step-4-bg.png" class="img-responsive imgdefaut" alt=""></a></li>
                                 </ul>
                                
                            <div class="clearfix"></div>
                            <div class="tab-content bd-step ">
	                            <c:forEach items="${lstStep }" var="step">
	                                <div id="step${cnt9 }" class="tab-pane fade <c:if test='${cnt9==1 }'>in active</c:if>">                              
	                                  ${step.content }
	                                </div>
	                                <c:set var="cnt9" value="${cnt9+1}"></c:set>
	                            </c:forEach>
                            </div>
                        </div>
         </c:if>
    </div>
    <!--endcontentmain--> 
    