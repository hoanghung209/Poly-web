package com.vega.fileupload;

import java.io.Serializable;


/**
 * if(code == 0 ) then  success
 * if(code == 1 ) then system error
 * if(code == 404 ) then khong co data
 * if(code == 2 ) then filename not valid
 * if(code == 3 ) then noi dung file khong hop le
 * @author namts
 */
public class FileUploadInfo  implements Serializable
{
   
    private static final long serialVersionUID = 4769838206253421500L;
    private int code;
    private String desc;

    private String fileID="-1";
    private String name;
    private String small_size_src;
    private String medium_size_src;
    private String large_size_src;
    private String original_src;

    private String size;   
    private String width;          
    private String height;         
    private String path; 
    private String contentType;         
    private String extension;
    private int fileTypeID;


    public String getName() {
            return name;
    }
    public void setName(String name) {
            this.name = name;
    }
    public String getSmall_size_src() {
            return small_size_src;
    }
    public void setSmall_size_src(String small_size_src) {
            this.small_size_src = small_size_src;
    }
    public String getMedium_size_src() {
            return medium_size_src;
    }
    public void setMedium_size_src(String medium_size_src) {
            this.medium_size_src = medium_size_src;
    }
    public String getLarge_size_src() {
            return large_size_src;
    }
    public void setLarge_size_src(String large_size_src) {
            this.large_size_src = large_size_src;
    }
    /**
     * @return the original_src
     */
    public String getOriginal_src() {
            return original_src;
    }
    /**
     * @param original_src the original_src to set
     */
    public void setOriginal_src(String original_src) {
            this.original_src = original_src;
    }
    /**
     * @return the size
     */
    public String getSize() {
            return size;
    }
    /**
     * @param size the size to set
     */
    public void setSize(String size) {
            this.size = size;
    }
    /**
     * @return the width
     */
    public String getWidth() {
            return width;
    }
    /**
     * @param width the width to set
     */
    public void setWidth(String width) {
            this.width = width;
    }
    /**
     * @return the height
     */
    public String getHeight() {
            return height;
    }
    /**
     * @param height the height to set
     */
    public void setHeight(String height) {
            this.height = height;
    }
    /**
     * @return the path
     */
    public String getPath() {
            return path;
    }
    /**
     * @param path the path to set
     */
    public void setPath(String path) {
            this.path = path;
    }
    /**
     * @return the contentType
     */
    public String getContentType() {
            return contentType;
    }
    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
            this.contentType = contentType;
    }
    /**
     * @return the extension
     */
    public String getExtension() {
            return extension;
    }
    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
            this.extension = extension;
    }
    /**
     * @return the fileID
     */
    public String getFileID() {
            return fileID;
    }
    /**
     * @param fileID the fileID to set
     */
    public void setFileID(String fileID) {
            this.fileID = fileID;
    }

    public String getUrl() {
            return FileConfig.server + original_src;
    }
    public String getUrl(String suffix)	{
            String url = "";
            int lastIndex = original_src.lastIndexOf('.');
            if(lastIndex > -1) {
                    url = original_src.substring(0, lastIndex) + "_" + suffix + original_src.substring(lastIndex);
            }
            else {
                    url = original_src;
            }		
            return FileConfig.server + url;
    }		

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the fileTypeID
     */
    public int getFileTypeID() {
        return fileTypeID;
    }

    /**
     * @param fileTypeID the fileTypeID to set
     */
    public void setFileTypeID(int fileTypeID) {
        this.fileTypeID = fileTypeID;
    }
}
