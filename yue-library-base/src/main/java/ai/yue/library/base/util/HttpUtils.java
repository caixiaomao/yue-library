package ai.yue.library.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

import ai.yue.library.base.util.servlet.ServletUtils;
import cn.hutool.core.lang.Console;

/**
 * @deprecated 请使用 {@linkplain ServletUtils}
 * @author	孙金川
 * @since	2018年12月18日
 */
@Deprecated
public class HttpUtils {
	
	public static final String HTTP_TCP_NAME = "http://";
	public static final String HTTPS_TCP_NAME = "https://";
	
	/**
	 * HttpAspect请求切入点
	 */
	public static final String POINTCUT = "@annotation(org.springframework.web.bind.annotation.RequestMapping)"
			+ " || @annotation(org.springframework.web.bind.annotation.GetMapping)"
			+ " || @annotation(org.springframework.web.bind.annotation.PostMapping)"
			+ " || @annotation(org.springframework.web.bind.annotation.PutMapping)"
			+ " || @annotation(org.springframework.web.bind.annotation.PatchMapping)"
			+ " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)";
	
	/**
	 * 获得当前请求上下文中的{@linkplain ServletRequestAttributes}
	 * @return ServletRequestAttributes
	 */
	public static ServletRequestAttributes getRequestAttributes() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
	}
	
	/**
	 * 获得当前请求上下文中的{@linkplain HttpServletRequest}
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获得当前请求上下文中的{@linkplain HttpServletResponse}
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	
	/**
	 * 获得当前请求{@linkplain HttpSession}
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 获得当前请求的服务器的URL地址
	 * <p>
	 * 示例一：http://localhost:8080<br>
	 * 示例二：http://localhost:8080/projectName<br>
	 * @return 当前请求的服务器的URL地址
	 */
	public static String getServerURL() {
		HttpServletRequest request = getRequest();
		String serverName = request.getServerName();// 服务器地址
		int serverPort = request.getServerPort();// 端口号
		String contextPath = request.getContextPath();// 项目名称
		return HTTP_TCP_NAME + serverName + ":" + serverPort + contextPath;
	}
	
	/**
	 * 打印请求报文
	 * <p>
	 * 注意：打印不包括：异步请求内容、数据流
	 */
	public static void printRequest() {
		// 1. 打印请求信息
		HttpServletRequest request = getRequest();
		Console.error("========开始-打印请求报文========");
		Console.log();
		Console.log("打印请求信息：");
		Console.log("RemoteAddr：{}", request.getRemoteAddr());
		Console.log("Method：{}", request.getMethod());
		Console.log("AuthType：{}", request.getAuthType());
		
		// 2. 打印服务器信息
		Console.log();
		Console.log("打印服务器信息：");
		Console.log("ServerURL：{}", getServerURL());
		Console.log("RequestURL：{}", request.getRequestURL());
		Console.log("RequestedSessionId：{}", request.getRequestedSessionId());
		
		// 3. 打印请求参数
		Console.log();
		Console.log("打印请求参数：");
		Console.log("QueryString：{}", request.getQueryString());
		Console.log("ParameterMap：{}", JSONObject.toJSONString(request.getParameterMap()));
		
		// 4. 打印请求头
		Console.log();
		Console.log("打印请求头：");
		Console.log("Headers：");
		request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
			StringBuilder headerValues = new StringBuilder();
			request.getHeaders(headerName).asIterator().forEachRemaining(headerValue -> {
				headerValues.append(headerValue);
			});;
			Console.log("　　{}：{}", headerName, headerValues);
		});;
		
		// 5. 打印Cookie
		Console.log();
		Console.log("Cookies：");
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				Console.log("　　{}：{}", cookie.getName(), cookie.getValue());
			}
		}
		Console.error("========结束-打印请求报文========");
	}
	
}
