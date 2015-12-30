package com.seiu.web.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jconfig.Configuration;

public class Config {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger
	        .getLogger(Config.class);
	public static int TIME_RELOAD_CACHE_CONTENT = 300;
	public static boolean enable_cache_query = false;
	public static boolean enable_cache_content = true;
	public static int QUERY_CACHE_TIME = 300;
	public static boolean hashId = false;
	public static Integer PAGE_SIZE = 25;

	// Search 
	public static Integer SEARCH_KEYWORD_CACHE_EXPIRED = 60;
	public static Integer MAX_SEARCH_RESULT = 100;
	public static String CONTENT_SUGGESTER_NAME = "content_suggester";

	// images static
	public static String DEFAULT_IMAGE_LINK = "default.png";
	public static String static_url_original = "http://192.168.51.149:8888/";
	public static String static_url_resize = "http://192.168.51.149:8888/resize/";
	public static String static_url_crop = "http://192.168.51.149:8888/crop/";
	public static long time_cached_img = 7200000;

	public static int MAX_SIZE_HISTORY_IN_PROFILE = 6;
	public static Map<String, String> mTopicId2Type = new HashMap<String, String>();
	public static String OTP_MESSAGE = "Quy khach dang thuc hien dang ky tai khoan tren website http://alome.vn. Ma xac nhan cua quy khach la %s";

	//system config
	public static int http_connection_timeout = 300000;
	public static int http_socket_timeout = 300000;
	public static int COOKIE_AGE = 60 * 60 * 24 * 7;
	//api server
	public static String register_url = "http://192.168.51.68:8081/api/registerPackage";
	public static String cancel_url = "http://192.168.51.68:8081/api/cancelSubPackage";
	public static String send_sms_url = "http://192.168.51.68:8081/api/sendSMS";
	public static String send_gift_url = "";
	public static String API_SOURCE = "WEB";
	public static int API_TYPE = 8;
	public static int gift_amount = 5000;

	// Streaming
	public static String domainStreaming = "http://192.168.51.149:88/";
	public static String keyGetUrlStreaming = "09Alomesec90";
	public static String rootPathStreaming = "/alome_vms";
	public static String contentExtension = ".mp3";

	public static String configPath;
	public static Configuration config;

	public static boolean ALLOW_PENDING_PLAY_CONTENT = false;
	public static Map<String, String> mMobiPrefix = new HashMap<String, String>();

	public static void loadConfig() {

		String mobiPrefix = "84120,84121,84122,84126,84128,8490,8493";

		if (!org.apache.commons.lang.StringUtils.isBlank(mobiPrefix)) {
			String[] prefix = mobiPrefix.split(",|;");
			if (prefix != null) {
				for (String p : prefix) {
					mMobiPrefix.put(p, "1");
				}
			}
		}
	}

	public static void load(String path) {
		configPath = path;
		config = ConfigUtils.loadConfig("wap_config", configPath);
		initConfig();
	}

	public static void initConfig() {

		// config_frontend config
		hashId = getBoolean("hash_id", hashId, "config_frontend");
		OTP_MESSAGE = getProperty("otp_message", OTP_MESSAGE, "config_frontend");
		PAGE_SIZE = getInt("page_size", PAGE_SIZE, "config_frontend");
		ALLOW_PENDING_PLAY_CONTENT = getBoolean("allow_pending_play_content",
		        ALLOW_PENDING_PLAY_CONTENT, "config_frontend");

		// img_static
		DEFAULT_IMAGE_LINK = getProperty("default_img_link", DEFAULT_IMAGE_LINK, "img_static");
		static_url_original = getProperty("static_url_original", static_url_original, "img_static");
		static_url_resize = getProperty("static_url_resize", static_url_resize, "img_static");
		static_url_crop = getProperty("static_url_crop", static_url_crop, "img_static");
		time_cached_img = getLong("time_cached_img", time_cached_img, "img_static");

		// system_config config
		http_connection_timeout = getInt("http_connection_timeout", http_connection_timeout,
		        "system_config");
		http_socket_timeout = getInt("http_socket_timeout", http_socket_timeout, "system_config");
		COOKIE_AGE = getInt("cookie_age", COOKIE_AGE, "system_config");

		// streaming
		domainStreaming = getProperty("domain_streaming", domainStreaming, "streaming");
		keyGetUrlStreaming = getProperty("key_get_url_streaming", keyGetUrlStreaming, "streaming");
		rootPathStreaming = getProperty("root_path_streaming", rootPathStreaming, "streaming");
		contentExtension = getProperty("content_extension", contentExtension, "streaming");

		// search config
		SEARCH_KEYWORD_CACHE_EXPIRED = getInt("search_keyword_cache_expired",
		        SEARCH_KEYWORD_CACHE_EXPIRED, "search");
		MAX_SEARCH_RESULT = getInt("max_search_result", MAX_SEARCH_RESULT, "search");
		CONTENT_SUGGESTER_NAME = getProperty("content_suggester_name", CONTENT_SUGGESTER_NAME,
		        "search");
		// api
		register_url = getProperty("register_url", register_url, "api");
		cancel_url = getProperty("cancel_url", cancel_url, "api");
		send_sms_url = getProperty("send_sms_url", send_sms_url, "api");
		send_gift_url = getProperty("send_gift_url", send_gift_url, "api");
		API_SOURCE = getProperty("api_source", API_SOURCE, "api");
		API_TYPE = getInt("api_type", API_TYPE, "api");
		gift_amount = getInt("gift_amount", gift_amount, "api");

	}

	public static String getImgVersion() {
		return String.valueOf(System.currentTimeMillis() / time_cached_img);
	}

	private static String getProperty(String key, String defaultValue, String category) {
		String rs = config.getProperty(key, defaultValue, category);
		logger.debug("Load config ==> " + key + ":" + rs);
		return rs;
	}

	private static Boolean getBoolean(String key, boolean defaultValue, String category) {
		Boolean rs = config.getBooleanProperty(key, defaultValue, category);
		logger.debug("Load config ==> " + key + ":" + rs);
		return rs;
	}

	private static Integer getInt(String key, Integer defaultValue, String category) {
		Integer rs = config.getIntProperty(key, defaultValue, category);
		logger.debug("Load config ==> " + key + ":" + rs);
		return rs;
	}

	private static Long getLong(String key, Long defaultValue, String category) {
		Long rs = config.getLongProperty(key, defaultValue, category);
		logger.debug("Load config ==> " + key + ":" + rs);
		return rs;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
