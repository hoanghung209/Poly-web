package com.seiu.web.dao;

import com.seiu.web.common.DBHelper;

public class RegisterDAO
{
  public static int insert(String fullname, String phone, String email, String address)
  {
    String sqlCommand = "INSERT INTO register(fullname,phone,email,address,status,is_deleted,created_time) VALUES(?,?,?,?,0,0,NOW())";
    return DBHelper.insertOrUpdate(sqlCommand, new Object[] { fullname, phone, email, address });
  }
}