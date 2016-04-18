<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
#tvvideo .text-hv {
	background:transparent !important;
}
.pic-hv{
	cusor:pointed;
}
.pic-hv a{
	opacity:.9;
	cursor: pointer;
}
</style>
    <div class="col-sm-9 contentmainright">
           <div class="title1 clearfix">
      		<h2 style="font-size:32px;">${mapVideo.title}</h2>
			</div>
    		  
          <div class="tab-content">
          <div id="tv_video">
				<div id="tvvideo">
              
            <ul class="row listanh">
             <c:forEach items="${lstVideo }" var="video">
              <li class=" col-sm-4 col-md-4">
                <div class="clearfix pic-hv">
                  <a class="a_video" data-toggle="modal" data-target="#VideoModal${video.id }">
				  <img src="${video.url}" alt="">
                  <div class="text-hv">
                    <span class="fa fa-play-circle"></span>
                  </div>
                  </a>
                </div>
                <div class="col-sm-12 text-justify">
					${video.title}
				</div>
              </li>              
              </c:forEach>
              </ul>
              </div>
          </div>                      
        </div>
    </div>
    <!--endcontentmain--> 
    
	<!-- Modal HTML -->
	<c:forEach items="${lstVideo }" var="video">
    <div id="VideoModal${video.id }" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body">
					<div class="embed-responsive embed-responsive-16by9">
                    <iframe class="videoFrame" src="${video.embed }" allowfullscreen></iframe>
					</div>
                </div>
            </div>
        </div>
    </div>
	</c:forEach>
	
<script>
$(document).ready(function(){
	
	
    $(".modal").on('hidden.bs.modal', function (e) {
		    $(this).find('iframe').attr("src", $(this).find('iframe').attr("src"));
		});

});
</script>