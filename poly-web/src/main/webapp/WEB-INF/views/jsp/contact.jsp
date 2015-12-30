<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <div class=" col-xs-12 col-sm-12 contentmainright">
    ${lstContent.get(0).get('content') }
    	<div class=" clearfix col-xs-12 col-sm-6 col-lg-6">
        ${lstContent.get(1).get('content') }
      </div>
      <div class="clearfix col-xs-12 col-sm-6 col-lg-6">
        <h3 class="titlecontent2">${lstContent.get(2).get('content') }</h3>
        <form action="" class="form-horizontal" method="get">
          <div class="form-group">
            <input type="text" name = "txtName" class="form-control" placeholder="Họ và tên (*)">
          </div>
            <div class="form-group">
          <input type="text" name = "txtPhone" class="form-control" placeholder="Số điện thoại(*)">
          </div>
          <div class="form-group">
            <textarea  class=" form-control "name="txtMes" id="txtMes" cols="" rows="8" placeholder="Thông điệp"></textarea>
          </div>
          <div class="form-group">
          <div class="button">
          <input type="submit" value="Gửi" class="btn btndk ">
             </div>
             </div>
        </form>        
      </div>  
      <span style="color: red;float: right;">${messs}</span>
   
   </div>
    <!--endcontentmain--> 
    
  </div>
</div>