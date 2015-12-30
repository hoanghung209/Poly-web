package com.vega.fileupload;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vg.core.common.StringUtils;


public class FileUtils {

    private static final String SPECIAL_CHARACTER_PATTERN = ".*[,/'#~@\\x5B\\x5D}{+_)(*&^%$£\"!\\|<>]+.*";

    public static boolean isvalidate(String filename) {

        if (StringUtils.isBlank(filename)) {
            return false;
        }

        Pattern pattern = Pattern.compile(SPECIAL_CHARACTER_PATTERN);

        Matcher matcher = pattern.matcher(filename.toString().trim());

        if (matcher.matches()) {
            return false;
        }
        return true;

    }

    public static String HashFile(byte[] file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(file);
            BigInteger number = new BigInteger(1, messageDigest);
            String hashfile = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32
            // chars.
            while (hashfile.length() < 32) {
                hashfile = "0" + hashfile;
            }
            return hashfile;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    /*
     * <summary>
     * Namts:  detemine path to store file
     * for example: [1e]-[5e]-[42]-[1e5e4212f86d8ecbe5acc956c97fa373]
     * </summary>
     * <param name="file">file content - array of bytes</param>
     *  <returns>hashed path</returns>
     */
    public static List<String> GetPaths(byte[] file) {
        String hashed = HashFile(file);

        List<String> toReturn = new ArrayList<String>(4);
        toReturn.add(hashed.substring(0, 3));
        toReturn.add(hashed.substring(3, 6));
        toReturn.add(hashed.substring(6, 9));
        toReturn.add(hashed);
        return toReturn; // for example: [1e5]-[e42]-[12f]-[1e5e4212f86d8ecbe5acc956c97fa373]
    }

    /**
     * Namts: detemine path to store file
     *
     * @param databuffer
     * @return this path for example:
     * [1e]/[5e]/[42]-[1e5e4212f86d8ecbe5acc956c97fa373]/
     */
    public static String GetPath(byte[] databuffer) {
        List<String> paths = GetPaths(databuffer);
        String path = "";
        if (paths.size() > 2) {
            path = paths.get(0) + "/" + paths.get(1) + "/" + paths.get(2) + "/";
        }
        return path;
    }

    public static boolean IsImage(String filename) {
        boolean isimage = false;
        if (!StringUtils.isBlank(filename)) {
            if (filename.lastIndexOf(".") < filename.length()) {
                String str = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase().trim();

                if (!StringUtils.isBlank(str)) {
                    if (str.equalsIgnoreCase("gif") || str.equalsIgnoreCase("jpg") || str.equalsIgnoreCase("jpeg")
                            || str.equalsIgnoreCase("tiff") || str.equalsIgnoreCase("tif")
                            || str.equalsIgnoreCase("png") || str.equalsIgnoreCase("bmp")) {
                        isimage = true;
                    }
                }
            }
        }

        return isimage;
    }

    public static boolean IsVideo(String filename) {
        boolean isVideo = false;

        if (!StringUtils.isBlank(filename)) {
            if (filename.lastIndexOf(".") < filename.length()) {
                String str = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase().trim();

                if (!StringUtils.isBlank(str)) {
                    if (str.equalsIgnoreCase("avi") || str.equalsIgnoreCase("3gp") || str.equalsIgnoreCase("3gpp")
                            || str.equalsIgnoreCase("h263") || str.equalsIgnoreCase("h264")
                            || str.equalsIgnoreCase("qt") || str.equalsIgnoreCase("mp4") || str.equalsIgnoreCase("flv")) {
                        isVideo = true;
                    }
                }
            }
        }

        return isVideo;
    }

    public static boolean IsAudio(String filename) {
        boolean isAudio = false;
        if (!StringUtils.isBlank(filename)) {
            if (filename.lastIndexOf(".") < filename.length()) {
                String str = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase().trim();

                if (!StringUtils.isBlank(str)) {
                    if (str.equalsIgnoreCase("wav") || str.equalsIgnoreCase("mid") || str.equalsIgnoreCase("midi")
                            || str.equalsIgnoreCase("amr") || str.equalsIgnoreCase("mp3")) {
                        isAudio = true;
                    }
                }
            }
        }

        return isAudio;
    }

    public static String GetFolder(String filename) {
        if (IsImage(filename)) {
            return "image";
        } else if (IsVideo(filename)) {
            return "video";
        } else if (IsAudio(filename)) {
            return "audio";
        } else {
            return "application";
        }
    }

    public static int GetFileTypeGroupId(String foldername) {
        if (foldername.equalsIgnoreCase("image")) {
            return 1;
        } else if (foldername.equalsIgnoreCase("audio")) {
            return 2;
        } else if (foldername.equalsIgnoreCase("video")) {
            return 3;
        } else {
            return 4;
        }
    }

}
