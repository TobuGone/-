package com.kapphk.pms.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.kapphk.pms.timer.FileClearTask;

/**
 * @author  huzi
 * @date    2018年6月19日	
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext; // Spring应用上下文环境

    /*
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     * 通过传递applicationContext参数初始化成员变量applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext)   throws BeansException {
    	
        SpringContextUtil.applicationContext = applicationContext;
        
        //定时任务 可以在这里测试  
        FileClearTask task = applicationContext.getBean(FileClearTask.class);
        task.getFileClear();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return (T) applicationContext.getBean(clazz);
    }
}
