package com.seiu.web.common;

import javax.mail.MessagingException;

import com.seiu.web.dao.CustomerDAO;
import com.seiu.web.utils.ContextUtils;

public class MailThread
extends Thread
{
private String receiver;
private String subject;
private String content;
private String newpass;

public MailThread(String receiver, String subject, String content, String newpass)
{
  this.receiver = receiver;
  this.subject = subject;
  this.content = content;
  this.newpass = newpass;
}

public void run()
{
  try
  {
    MailService.sendMessage(this.receiver, this.subject, this.content);
    if (!ContextUtils.isBlank(this.newpass)) {
      CustomerDAO.resetPassword(this.receiver, this.newpass);
    }
  }
  catch (MessagingException e)
  {
    System.out.println("Loi gui mail toi : " + this.receiver);
  }
}
}
