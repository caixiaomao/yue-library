package ai.yue.library.base.config.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * 跳过SSL证书校验，{@linkplain org.springframework.boot.actuate.autoconfigure.cloudfoundry.servlet.SkipSslVerificationHttpRequestFactory}
 * <p>{@link SimpleClientHttpRequestFactory} that skips SSL certificate verification.
 * <p>信任自签证书，跳过Hostname检查
 * 
 * @author	ylyue
 * @since	2018年11月10日
 */
public class SkipSslVerificationHttpRequestFactory extends SimpleClientHttpRequestFactory {

	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		if (connection instanceof HttpsURLConnection) {
			prepareHttpsConnection((HttpsURLConnection) connection);
		}
		super.prepareConnection(connection, httpMethod);
	}
	
	private void prepareHttpsConnection(HttpsURLConnection connection) {
		connection.setHostnameVerifier(new SkipHostnameVerifier());
		try {
			connection.setSSLSocketFactory(createSslSocketFactory());
		} catch (Exception ex) {
			// Ignore
		}
	}
	
	private SSLSocketFactory createSslSocketFactory() throws Exception {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new TrustManager[] { new SkipX509TrustManager() }, new SecureRandom());
		return context.getSocketFactory();
	}
	
	private class SkipHostnameVerifier implements HostnameVerifier {

		@Override
		public boolean verify(String s, SSLSession sslSession) {
			return true;
		}

	}
	
	private static class SkipX509TrustManager implements X509TrustManager {

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

	}
	
}