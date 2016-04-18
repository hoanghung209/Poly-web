/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.web.servlet;

import com.vega.fileupload.FileUploadInfo;
import com.vega.fileupload.FileUploader;
import com.vega.security.AccessControl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import vg.core.common.StringUtils;
import vg.core.configproxy.ConfigProxy;

/**
 *
 * @author namts
 */
@SuppressWarnings("serial")
public class FileUploadServlet extends HttpServlet {

    private final Logger logger = ConfigProxy.getLogger(FileUploadServlet.class.getName());

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("rawtypes")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Begin processRequest from: " + getRemoteIp(request));

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();        
        String result = "";
        if(AccessControl.HasPermission("file:upload", request)||AccessControl.HasPermission("system:administrator", request)){
        try {

            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

            /*
             * Set the size threshold, above which content will be stored on disk.
             */
            fileItemFactory.setSizeThreshold(100 * 1024 * 1024); // 1 MB

            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);

            /*
             * Parse the request
             */
            List items = uploadHandler.parseRequest(request);
            Iterator itr = items.iterator();

            logger.info("Request.size: " + items.size());

            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                //if (item.get().length >= 1024 && !StringUtils.isBlank(item.getName())) {
                if (!StringUtils.isBlank(item.getName())) {
                    FileUploadInfo fileUploadInfo = FileUploader.UploadFile(item.get(), item.getName(), item.getContentType());

                    if (fileUploadInfo != null) {
                        result = " ({ \"FileID\":\"" + fileUploadInfo.getFileID()
                                + "\",\"Url\":\"" + fileUploadInfo.getUrl()
                                + "\",\"FileTypeID\":\"" + fileUploadInfo.getFileTypeID()
                                + "\",\"FolderID\":\"0"
                                + "\",\"Name\":\"" + fileUploadInfo.getName()
                                + "\",\"Src\":\"" + fileUploadInfo.getOriginal_src()
                                + "\",\"Size\":\"" + fileUploadInfo.getSize()
                                + "\",\"ContentType\":\"" + fileUploadInfo.getContentType()
                                + "\",\"Extension\":\"" + fileUploadInfo.getExtension()
                                + "\",\"Width\":\"" + fileUploadInfo.getWidth()
                                + "\",\"Height\":\"" + fileUploadInfo.getHeight()
                                + "\",\"Message\":\" " + fileUploadInfo.getDesc() + "\" } )";

                    } else {

                        result = " ({ \"FileID\":\"-1"
                                + "\",\"Url\":\""
                                + "\",\"FileTypeID\":\"0"
                                + "\",\"FolderID\":\"0"
                                + "\",\"Name\":\""
                                + "\",\"Src\":\""
                                + "\",\"Size\":\"0"
                                + "\",\"ContentType\":\""
                                + "\",\"Extension\":\""
                                + "\",\"Width\":\"0"
                                + "\",\"Height\":\"0"
                                + "\",\"Message\":\" Upload khong thanh cong hoac file khong hop le \" })";
                    }

                    break;
                }

            }
            if (StringUtils.isBlank(result)) {
                result = " ({ \"FileID\":\"-1"
                        + "\",\"Url\":\""
                        + "\",\"FileTypeID\":\"0"
                        + "\",\"FolderID\":\"0"
                        + "\",\"Name\":\""
                        + "\",\"Src\":\""
                        + "\",\"Size\":\"0"
                        + "\",\"ContentType\":\""
                        + "\",\"Extension\":\""
                        + "\",\"Width\":\"0"
                        + "\",\"Height\":\"0"
                        + "\",\"Message\":\" Upload khong thanh cong hoac file khong hop le \" })";

            }
            out.println(result);
        } catch (Exception ex) {
            logger.error("Error encountered while uploading file", ex);
        } finally {
            out.close();
        }

        logger.info("End processRequest");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getRemoteIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
