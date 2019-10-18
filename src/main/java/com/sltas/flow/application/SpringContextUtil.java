package com.sltas.flow.application;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.sltas.flow.util.server.ProducerNackConfiguration;
import com.sltas.flow.util.server.ServiceSubtypeEnum;
import com.sltas.flow.util.server.ServiceSubtypeCache;

/**
 * <p>
 * Title: SpringContextUtil
 * </p>
 * <p>
 * Description: 初始化参数 自动注入applicationContext
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇
 * @date 2018年1月23日上午11:29:12
 *  
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		//初始化Application
		if (SpringContextUtil.applicationContext == null) {
			SpringContextUtil.applicationContext = applicationContext;
		}
		//初始化服务子类型缓存
		for(ServiceSubtypeEnum subtype:ServiceSubtypeEnum.values()){
			ServiceSubtypeCache.subtypeCache.put(subtype.getServiceId(), subtype);
        }
		//初始化
		for(ServiceSubtypeEnum ss : ServiceSubtypeCache.subtypeCache.values()){
			ProducerNackConfiguration pnc = ss.getProducerNackConfiguration();
			logger.info("[ApplicationContext Init] serviceType : {} ,serviceId ： {} 检测service配置信息：{}",ss.getServiceType(),ss.getServiceId(),pnc);
			if(pnc != null){
				Method method = ReflectionUtils.findMethod(applicationContext.getBean(pnc.getNackServiceName()).getClass(), pnc.getNackServiceMethod(), pnc.getParamTypes());
				if(method == null){
					throw new IllegalArgumentException("[ApplicationContext Init Error] Service name, method and parameter information are inconsistent");
				}
			}
		}
		logger.info("[ApplicationContext Init End]");
	}

	//获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
