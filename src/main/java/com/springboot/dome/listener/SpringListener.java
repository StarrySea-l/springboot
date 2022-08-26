package com.springboot.dome.listener;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("springListener")
public class SpringListener implements ApplicationListener<ContextRefreshedEvent> {


	private ApplicationContext webApplicationContext;

	public List<String> getRequestMappingUrls(){
		Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(webApplicationContext,HandlerMapping.class, true, false);
		List<String> requestUrls = new ArrayList<String>();
		System.out.println("*************开始输出uri******************");
		for (HandlerMapping handlerMapping : allRequestMappings.values()){
            //只需要RequestMappingHandlerMapping中的URL映射
            if (handlerMapping instanceof RequestMappingHandlerMapping){
            	RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()){
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    Set<String> urls = patternsCondition.getPatterns();
                    for(String url:urls){
                    	System.out.println(url);
                    	requestUrls.add(url);
                    }
                }
            }
		}
		System.out.println("*************结束输出uri******************");
		return requestUrls;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		webApplicationContext = event.getApplicationContext();
		List<String> allMappingUrl = getRequestMappingUrls();

//		if(event.getApplicationContext().getParent()!=null){
//			Map<String, String> filterChainDefinitionMap = myShiroFilterFactoryBean.getFilterChainDefinitionMap();
//			DefaultFilterChainManager manager = myShiroFilterFactoryBean.getDefaultFilterChainManager();
//
//			String chainDef = "perms[\"UNDEFINED_URL\"]";
//			allMappingUrl.stream().forEach(url -> {
//				if(!filterChainDefinitionMap.containsKey(url)){
//					System.out.println("***发现未定义的RequestMappingUrl,url=" + url);
//					manager.createChain(url, chainDef);
//				}
//			});
//		}

		System.out.println("spring 加载完毕..");
	}

}
