package cn.lynx.ctripmall.rest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.codec.binary.Base64;

public class HMACMD5 {
	private static final String FILEPATHKEY = "keyFile";
	private static final String KEY_KEY = "securityKey";
	private static final String APPID_KEY = "appid";
	private static final String DEFAULTKEY = "1C2CFE834E4B489E88CEACE93A7F6371";
	private static final String DEFAULTAPPID = "AT";

	private static final String CLASSNAME = HMACMD5.class.getName();
	private static final String PKG = HMACMD5.class.getPackage().getName();
	private static final Logger LOGGER = Logger.getLogger(PKG);

	private static String secKey = null;
	private static String appid = null;

	private static String MD5(String src, String key) {
		SecretKey sk = new SecretKeySpec(key.getBytes(), "HmacMD5");
		Mac mac = null;
		try {
			mac = Mac.getInstance(sk.getAlgorithm());
			mac.init(sk);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
			return null;
		}

		byte[] code = mac.doFinal(src.getBytes());
		return Base64.encodeBase64String(code);
	}

	public static String MD5(String srcidstr, long timestamp) {
		if (secKey == null || appid == null) {
			synchronized (HMACMD5.class) {
				try {
					String confPath = new InitialContext().lookup(FILEPATHKEY).toString();
					Properties p = new Properties();
					p.load(new FileInputStream(new File(confPath)));
					secKey = p.getProperty(KEY_KEY);
					appid = p.getProperty(APPID_KEY);
				} catch (NamingException | IOException e) {
					LOGGER.logp(Level.SEVERE, CLASSNAME, "MD5",
							"[EXCEPTION MD5] Can't find the correct filepath. Or the property load failed.", e);
					secKey = DEFAULTKEY;
					appid = DEFAULTAPPID;
				}
			}
		}
		
		String srcstr = srcidstr + "&timestamp=" + timestamp + "&appid=" + appid;
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.logp(Level.INFO, CLASSNAME, "MD5", "[INFO MD5] original src string is:" + srcstr);
		}
		return MD5(srcstr, secKey);
	}
}
