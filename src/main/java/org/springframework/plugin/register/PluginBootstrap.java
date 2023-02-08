package org.springframework.plugin.register;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.plugin.PluginI;
import org.springframework.plugin.annotation.Plugin;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class PluginBootstrap implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    private PluginRegister pluginRegister;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
           this.applicationContext = applicationContext;
    }
    @PostConstruct
    public  void  init(){
        Map<String, Object> pluginBeans = applicationContext.getBeansWithAnnotation(Plugin.class);
        pluginBeans.values().forEach(plugin->{pluginRegister.doRegistration((PluginI)plugin);});
    }
}
