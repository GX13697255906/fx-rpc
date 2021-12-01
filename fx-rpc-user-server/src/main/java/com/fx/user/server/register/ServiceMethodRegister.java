package com.fx.user.server.register;

import com.fx.common.annoation.RpcService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Administrator
 */
@Configuration
public class ServiceMethodRegister implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Service serviceAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), Service.class);
        if (null != serviceAnnotation) {
            Method[] methods = ReflectionUtils.getDeclaredMethods(bean.getClass());
            if (methods != null && methods.length > 0) {
                for (Method method : methods) {
                    RpcService rpcService = AnnotationUtils.findAnnotation(method, RpcService.class);
                    if (rpcService != null) {
                        String path = rpcService.path();
                        CommonMap.pathToBeanMap.put(path, bean);
                        CommonMap.pathToMethodMap.put(path, method);
                    }
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
