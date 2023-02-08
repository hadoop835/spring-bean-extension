package org.springframework.plugin.register;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.plugin.PluginI;
import org.springframework.plugin.PluginRepository;
import org.springframework.plugin.annotation.Plugin;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Administrator
 */
@Component
public class PluginRegister  {
    @Resource
    private PluginRepository pluginRepository;

    public void doRegistration(PluginI pluginI) {
        Class<?> extensionClz = pluginI.getClass();
        if (AopUtils.isAopProxy(pluginI)) {
            extensionClz = ClassUtils.getUserClass(pluginI);
        }
        Plugin extensionAnn = AnnotationUtils.findAnnotation(extensionClz, Plugin.class);
        Class<?>[] classes =  ClassUtils.getAllInterfacesForClass(extensionClz);
        for(Class<?> clazz : classes){
          Plugin plugin =  pluginRepository.getPluginRepo().get(clazz);
            if(Objects.nonNull(plugin)){
                if(extensionAnn.order()>plugin.order()){
                    pluginRepository.getPluginRepo().put(clazz,extensionAnn);
                }
            }else{
                pluginRepository.getPluginRepo().put(clazz,extensionAnn);
            }
        }
    }
}
