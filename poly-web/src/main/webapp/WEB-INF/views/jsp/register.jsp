<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <div class="col-sm-9 contentmainright">      
           <c:forEach items="${lstContent }" var="item">
           <c:choose>
	         <c:when test="${item.type==1}"> <h1 class="titlecontent">${item.content}</h1></c:when>
	         <c:when test="${item.type==2}"> <p>${item.content}</p></c:when>
	         <c:when test="${item.type==3}"><c:if test="${not empty item.anchor}"><a name="${item.anchor}"></a></c:if>  <h2 class="titlecontent2">${item.content}</h2></c:when>
	         <c:when test="${item.type==5}"><div class="col-md-3"> <img src="${item.image}" class="img-responsive dislay" alt=""> </div></c:when>
	         <c:when test="${item.type==7}"> <ul class="ulcolor">${item.content}</ul></c:when>	        
	       	 
	       	</c:choose>         
         </c:forEach> 
          <div class="col-md-9 vien-1 ">
          <ul class="nav nav-tabs nav-justified">
            <li class="active"><a data-toggle="tab" href="#muctieu">${lstStep.get(0).get('title')}</a></li>
            <li ><a data-toggle="tab" href="#report">${lstStep.get(1).get('title')}</a></li>
            <li><a data-toggle="tab" href="#regist">${lstStep.get(2).get('title')}</a></li>
          </ul>
          <div class="tab-content space">
            <div id="muctieu" class="tab-pane fade in active">
           ${lstStep.get(0).get('content')}
            </div>
            <div id="report" class="tab-pane fade">
              ${lstStep.get(1).get('content')}
            </div>
            <div id="regist" class="tab-pane fade">
              <form action="regtest" method="POST" role="form">
                <legend>
                <p class="titlecontent2"> ${lstStep.get(2).get('content')}</p>
                </legend>
                <div class="form-group">
                  <label class="sr-only" for="">ho va ten</label>
                  <input type="text" class="form-control" id="fullname" name="fullname" placeholder=" Họ và tên">
                </div>
                <div class="form-group">
                  <label class="sr-only" for=""> số điện thoại </label>
                  <input type="text" class="form-control" id="phone" name="phone" placeholder="Số điện thoại">
                </div>
                <div class="form-group">
                  <label class="sr-only" for="">email</label>
                  <input type="text" class="form-control" id="email" name="email" placeholder="Email">
                </div>
                <div class="form-group">
                  <label class="sr-only" for=""> địa chỉ</label>
                  <input type="text" class="form-control" id="address" name= "address" placeholder=" Địa chỉ" style="height: 8em;" >
                </div>
                <p class="p-from"> Chúng tôi sẽ liên lạc lại ngay với bạn sau 24h </p>
                <button type="submit" id="rstest" class="btn btn-primary btn-lg pull-right">GỬI</button>
              </form>
            </div>
          </div>
        </div>
    </div>
    <!--endcontentmain--> 
    