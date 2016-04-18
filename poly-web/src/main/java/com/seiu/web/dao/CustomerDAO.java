package com.seiu.web.dao;

import java.util.List;
import java.util.Map;
import com.seiu.web.common.DBHelper;

public class CustomerDAO
{
  public static int insert(String email, String password, String fullname, String phone)
  {
    String sqlCommand = "INSERT INTO customer(email,password,fullname,phone,status,created_time) VALUES(?,md5(?),?,?,0,NOW())";
    return DBHelper.insertOrUpdate(sqlCommand, new Object[] { email, password, fullname, phone });
  }
  
  public static boolean checkEmail(String email)
  {
    String sqlCommand = "SELECT * FROM customer WHERE email = ?";
    List<Map<String, String>> lstUser = DBHelper.executeQuery(sqlCommand, new Object[] { email });
    return lstUser.size() > 0;
  }
  
  public static boolean login(String email, String password)
  {
    String sqlCommand = "SELECT * FROM customer WHERE email = ? AND password = md5(?)";
    List<Map<String, String>> lstUser = DBHelper.executeQuery(sqlCommand, new Object[] { email, password });
    return lstUser.size() > 0;
  }
  
  public static int resetPassword(String email, String password)
  {
    String sqlCommand = "UPDATE customer SET password = md5(?) WHERE email = ?";
    return DBHelper.insertOrUpdate(sqlCommand, new Object[] { password, email });
  }
}

