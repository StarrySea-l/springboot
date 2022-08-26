package com.springboot.dome.util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * SpringMVC 上下文工具
 * @ClassName: ContextHelper
 * @Description:
 */
public class ContextHelper {

	private static final Logger logger = LoggerFactory.getLogger(ContextHelper.class);
	
	
	
	public static HttpServletResponse getResponse(){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(null!=attrs){
			HttpServletResponse response = attrs.getResponse();
			return response;
		}
		return null;
	}
	
	/**
	 * 获取当前HttpServletRequest
	 * @Title: getRequest
	 * @Description:
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(null!=attrs){
			HttpServletRequest request = attrs.getRequest();
			return request;
		}
		return null;
	}
	
	/**
	 * 获取请求参数
	 * @Title: getRequestParam
	 * @Description:
	 * @param paramName
	 * @return
	 */
	public static String getRequestParam(String paramName){
		HttpServletRequest request = getRequest();
		return null!=request?request.getParameter(paramName):null;
	}
	
	/**获取请求头
	 * @Title: getRequestHeader
	 * @Description:
	 * @param headerName
	 * @return
	 */
	public static String getRequestHeader(String headerName){
		HttpServletRequest request = getRequest();
		return null!=request?request.getHeader(headerName):null;
	}
	
	/**
	 * 获取当前session
	 * @Title: getSession
	 * @Description:
	 * @return
	 */
	public static HttpSession getSession(){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(null!=attrs){
			HttpServletRequest request = attrs.getRequest();
			return request.getSession();
		}
		return null;
	}
	
	/**
	 * 获取当前sessionId
	 * @Title: getSessionId
	 * @Description:
	 * @return
	 */
	public static String getSessionId(){
		HttpSession session = getSession();
		return null==session?null:session.getId();
	}
	
	/**
	 * 获取当前session中的数据
	 * @Title: getSessionAttr
	 * @Description:
	 * @param attrName
	 * @return
	 */
	public static Object getSessionAttr(String attrName){
		HttpSession session = getSession();
		return null==session?null:session.getAttribute(attrName);
	}
	
	/**
	 * 在当前session中设置属性值
	 * @Title: setSessionAttr
	 * @Description:
	 * @param attrName
	 * @param value
	 */
	public static void setSessionAttr(String attrName,Object value){
		HttpSession session = getSession();
		if(null!=session){
			session.setAttribute(attrName, value);
		}
	}
	
	/**
	 * 删除SESSION中的属性
	 * @Title: removeSessionAttr
	 * @Description:
	 * @param attrName
	 */
	public static void removeSessionAttr(String attrName){
		HttpSession session = getSession();
		if(null!=session){
			session.removeAttribute(attrName);
		}
	}
	
	/**
	 * 获取当前请求路径
	 * @Title: getRequestUrl
	 * @Description:
	 * @return
	 */
	public static String getRequestUrl(boolean withQueryString){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(null!=attrs){
			HttpServletRequest request = attrs.getRequest();
			String url = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort() +request.getContextPath() + request.getServletPath();
			if(443 == request.getServerPort()){
				url = "https://" + request.getServerName() + request.getContextPath() + request.getServletPath();
			}
			if(withQueryString && StrUtil.isNotBlank(request.getQueryString())){
				url = url + "?" + request.getQueryString();
			}
			return url;
		}
		return null;
	}
	
	/**
	 * 获取原始请求uri地址（未经过nginx代理前的，需要在nginx中设置 proxy_set_header X-Original-URI $uri; 带下来
	 * @Title: getOriginalRequestUrl
	 * @Description:
	 * @param withQueryString 是否包括url查询参数
	 * @param excludeParams 需要排除的参数...
	 * @return
	 */
	public static String getOriginalRequestUrl(boolean withQueryString,String... excludeParams){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(null!=attrs){
			HttpServletRequest request = attrs.getRequest();
			String uri = request.getHeader("X-Original-URI");
			String host = request.getHeader("X-Original-Host");
			logger.info("获取原始请求路径，X-Original-URI：{}，X-Original-Host：{}",uri,host);
			if(StrUtil.isBlank(uri) || StrUtil.isBlank(host)){
				return getRequestUrl(withQueryString);
			}
			String url = request.getScheme() +"://" + host + uri;
			if(443 == request.getServerPort()){
				url = "https://" + host + uri;
			}
			if(withQueryString && StrUtil.isNotBlank(request.getQueryString())){
				if(null!=excludeParams && excludeParams.length>0){
					String queryString = filterQueryString(request.getQueryString(),excludeParams);
					url = url + "?" + queryString;
				}else{
					url = url + "?" + request.getQueryString();
				}
			}
			return url;
		}
		return null;
	}
	
	/**
	 * 过滤url参数中的参数,返回过滤掉指定参数后的查询参数字符串
	 * @Title: filterQueryString
	 * @Description:
	 * @param queryParamStr 查询字符串，例如:para1=value1&para2=value2
	 * @param excludeParams 要排除的参数名，例如:para1
	 * @return
	 */
	public static String filterQueryString(String queryParamStr,String... excludeParams){
		StringBuilder sb = new StringBuilder();
		String[] qps = queryParamStr.split("&");
		for(int i=0;i<qps.length;i++){
			String para = qps[i];
			boolean isExclude = false;
			for(int j=0;j<excludeParams.length;j++){
				if(StrUtil.isNotBlank(excludeParams[j])){
					String ep = excludeParams[j] + "=";
					if(para.startsWith(ep)){
						isExclude = true;
						break;
					}
				}
			}
			if(!isExclude){
				if(i>0){
					sb.append("&");
				}
				sb.append(para);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取请求的ip地址
	 * @Title: getIpAdrress
	 * @Description:
	 * @param request
	 * @return
	 */
    public static String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StrUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StrUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
    
    /**
     * 获取当前请求的IP地址
     * @Title: getIpAddress
     * @Description:
     * @return
     */
    public static String getIpAddress(){
    	HttpServletRequest request = getRequest();
    	if(null!=request){
    		return getIpAdrress(request);
    	}
    	return null;
    }
    
    /**
     * 判断当前请求是否由微信浏览器发起
     * @Title: isWeixinRequest
     * @Description:
     * @return
     */
    public static boolean isWeixinRequest(){
        /** 拦截微信请求，避免生成无效的流水号 */
    	HttpServletRequest request = getRequest();
    	if(null==request){
    		return false;
    	}
        String userAgent = request.getHeader("User-Agent");
        if(StrUtil.isBlank(userAgent)){
            userAgent = request.getHeader("user-agent");
        }
        userAgent = userAgent.toLowerCase();
        return StrUtil.contains(userAgent, "micromessenger");
    }
    
    
    /**
     * 获取指定的SpringBean
     * @Title: getSpringBean
     * @Description:
     * @param beanId
     * @return
     */
    public static Object getSpringBean(String beanId){
    	HttpServletRequest request = getRequest();
    	WebApplicationContext webApplicationContext = null;
    	if(null==request){
    		webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
    	}else{
    		webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
    	}
    	if(null==webApplicationContext){
    		return null;
    	}
    	Object bean = null;
    	try {
    		bean = webApplicationContext.getBean(beanId);
		} catch (NoSuchBeanDefinitionException e) {
		}
    	return bean;
    }
}
