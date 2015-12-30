package com.vega.fileupload;

import java.util.Map;

import vg.core.configproxy.ConfigProxy;

public class FileConfig {

    private static final Map<String, String> fileConf = ConfigProxy.categories.get("file");
    /**
     * setting width for all size image
     */
    public static final int large_size = Integer.valueOf(fileConf.get("large_size"));
    public static final int medium_size = Integer.valueOf(fileConf.get("medium_size"));
    public static final int small_size = Integer.valueOf(fileConf.get("small_size"));

    public static final int height_large_size = Integer.valueOf(fileConf.get("height_large_size"));
    public static final int height_medium_size = Integer.valueOf(fileConf.get("height_medium_size"));
    public static final int height_small_size = Integer.valueOf(fileConf.get("height_small_size"));

    public static final String server = fileConf.get("server");
    public static final String rootFolder = fileConf.get("rootFolder");    
    public static final String mimeType = fileConf.get("mimeType");

}
