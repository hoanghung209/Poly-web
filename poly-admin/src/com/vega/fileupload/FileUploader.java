package com.vega.fileupload;
import java.io.*;
import java.util.List;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import vg.core.configproxy.ConfigProxy;

public class FileUploader {

    private static final Logger logger = ConfigProxy.getLogger(FileUploader.class.getName());

    public static FileUploadInfo UploadFile(byte[] databuffer, String fileName, String contentType) {
        FileUploadInfo fileInfo = new FileUploadInfo();
        logger.info("FileUploadInfo:"+fileName);
        if (databuffer == null) {
            fileInfo.setCode(404);
            fileInfo.setDesc("Khong tim thay du lieu file");
            return fileInfo;
        }

//         if(!FileUtils.isvalidate(fileName))
//         {
//             fileInfo.setCode(2);
//             fileInfo.setDesc("Ten file khong hop le");
//             return fileInfo;
//         }
        
        System.out.println(contentType); 
        
        Tika tika = new Tika();
        contentType = tika.detect(databuffer);
        String mimeType = "$" + contentType + "$";   
        
        //System.err.println("MIME: " + mimeType);
        if (!validateMIME(mimeType)) {
            fileInfo.setCode(3);
            fileInfo.setDesc("Noi dung file khong hop le");
            logger.info("Noi dung file khong hop le: " + mimeType);
            return fileInfo;
        }
        
        
        List<String> paths = FileUtils.GetPaths(databuffer);

        String path = FileUtils.GetPath(databuffer);
       
        if (paths.size() > 2) {
            path = paths.get(0) + "/" + paths.get(1) + "/" + paths.get(2) + "/";
        }
        String folder = FileUtils.GetFolder(fileName);
        path = folder + "/" + path;

        String original_src = path + paths.get(3) + fileName.substring(fileName.lastIndexOf("."));
        String fullpath = FileConfig.rootFolder + original_src;
        logger.info("FileUploadInfo.fullpath:"+fullpath);
        
        try {
            File theDir = new File(FileConfig.rootFolder + path);

            if (!theDir.exists()) {            	
                theDir.mkdirs();                
            }
            
            File thefile = new File(fullpath);
            //java.nio.file.Path pathFile = thefile.toPath();
            //System.out.println(Files.probeContentType(pathFile));
            if (!thefile.exists()) {
                thefile.createNewFile();

                FileOutputStream fop = new FileOutputStream(thefile);
                fop.write(databuffer);
                fop.flush();
                fop.close();

            }
             
            logger.info("FileUploadInfo.upload success");
            fileInfo.setFileID("1");
            fileInfo.setContentType(contentType);
            fileInfo.setName(fileName);
            fileInfo.setPath(fullpath);
            fileInfo.setOriginal_src(original_src);
            fileInfo.setSize(String.valueOf(databuffer.length));
            fileInfo.setFileTypeID(FileUtils.GetFileTypeGroupId(folder));
            fileInfo.setExtension(fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim());
            fileInfo.setDesc("Upload thanh cong");

        } catch (Exception ex) {
            fileInfo.setCode(1);
            fileInfo.setDesc(ex.getMessage());
            logger.error(ex);
            return fileInfo;
        }

        if (FileUtils.IsImage(fileName))//Neu la hinh anh thi tao ra ca ban verion khac nhau
        {

            String large_sizepath = FileConfig.rootFolder + path + paths.get(3) + "_large" + fileName.substring(fileName.lastIndexOf("."));
            String large_size_src = path + paths.get(3) + "_large" + fileName.substring(fileName.lastIndexOf("."));
            try {
                Thumbnails.of(new File(fullpath)).size(FileConfig.large_size, FileConfig.height_large_size).toFile(large_sizepath);
            } catch (IOException ex) {
                logger.error(ex);
            }
            fileInfo.setLarge_size_src(large_size_src);

            String medium_size_path = FileConfig.rootFolder + path + paths.get(3) + "_medium" + fileName.substring(fileName.lastIndexOf("."));
            String medium_size_src = path + paths.get(3) + "_medium" + fileName.substring(fileName.lastIndexOf("."));
            try {
                Thumbnails.of(new File(fullpath)).size(FileConfig.medium_size, FileConfig.height_medium_size).toFile(medium_size_path);
            } catch (IOException ex) {
                logger.error(ex);
            }
            fileInfo.setMedium_size_src(medium_size_src);

            String small_size_path = FileConfig.rootFolder + path + paths.get(3) + "_small" + fileName.substring(fileName.lastIndexOf("."));
            String small_size_src = path + paths.get(3) + "_small" + fileName.substring(fileName.lastIndexOf("."));
            try {
                Thumbnails.of(new File(fullpath)).size(FileConfig.small_size, FileConfig.height_small_size).toFile(small_size_path);
            } catch (IOException ex) {
                logger.error(ex);
            }
            fileInfo.setSmall_size_src(small_size_src);
            logger.info("FileUploadInfo.gen by image size success");
        }
        
        return fileInfo;
    }

    public static FileUploadInfo UploadFile(InputStream fsSource, String fileName, String contentType) {
        return UploadFile(ReadFile(fsSource), fileName, contentType);
    }

    private static boolean validateMIME(String mimeType) {
        /*
        StringBuilder lstMIME = new StringBuilder();
        lstMIME.append("$image/jpeg")
                .append("$image/gif")
                .append("$image/png")
                .append("$image/mpeg")
                .append("$image/bmp")
                .append("$image/bmp")
                .append("$audio/mpeg")
                .append("$audio/x-wav")
                .append("$audio/mid")
                .append("$video/3gpp")
                .append("$video/x-msvideo")
                .append("$video/x-flv")
                .append("$video/mp4")
                .append("$video/x-ms-wmv")
                .append("$application/zip")
                .append("$application/x-rar-compressed")
                .append("$application/msword")
                .append("$application/vnd.ms-excel")
                .append("$application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .append("$application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                .append("$application/x-shockwave-flash")
                .append("$");
        return (lstMIME.indexOf(mimeType) > -1);
        */
        return (FileConfig.mimeType.indexOf(mimeType) > -1);
    }

    public static byte[] ReadFile(InputStream fsSource) {
        byte[] bytes = null;

        try {
            try {
                // Read the source file into a byte array.
                bytes = new byte[fsSource.available()];
            } catch (IOException ex) {
                logger.error(ex);
            }
            int numBytesToRead = 1024;
            try {
                numBytesToRead = (int) fsSource.available();
            } catch (IOException ex) {
                logger.error(ex);
            }
            int numBytesRead = 0;
            while (numBytesToRead > 0) {
                // Read may return anything from 0 to numBytesToRead.
                int n = 0;
                try {
                    n = fsSource.read(bytes, numBytesRead, numBytesToRead);
                } catch (IOException ex) {
                    logger.error(ex);
                }

                // Break when the end of the file is reached.
                if (n == 0) {
                    break;
                }

                numBytesRead += n;
                numBytesToRead -= n;
            }

            return bytes;
        } catch (Exception ioEx) {
            logger.error(ioEx);
            return bytes;
        } finally {
            try {
                fsSource.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }

    }
}
