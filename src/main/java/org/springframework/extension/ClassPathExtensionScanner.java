package org.springframework.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.extension.annotation.Extension;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 扩展spring 注解
 *
 * @author chenzhh
 */
public class ClassPathExtensionScanner extends ClassPathBeanDefinitionScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathExtensionScanner.class);
    private static final Set<BeanDefinitionHolder> beanDefinitions = new HashSet<BeanDefinitionHolder>();


    /**
     * @param registry
     */
    public ClassPathExtensionScanner(BeanDefinitionRegistry registry) {
        super(registry, true);

    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        beanDefinitionHolders.addAll(beanDefinitions);
        processBeanDefinitions(beanDefinitions);
        return beanDefinitions;
    }

    @Override
    protected void postProcessBeanDefinition(AbstractBeanDefinition beanDefinition, String beanName) {
        super.postProcessBeanDefinition(beanDefinition, beanName);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(beanDefinition, beanName);
        beanDefinitions.add(definitionHolder);
    }

    protected void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        AbstractBeanDefinition definition;
        BeanDefinitionRegistry registry = getRegistry();
        for (BeanDefinitionHolder definitionHolder : beanDefinitions) {
            definition = (AbstractBeanDefinition) definitionHolder.getBeanDefinition();
            Class<?> beanClass = null;
            try {
                beanClass = this.getResourceLoader().getClassLoader().loadClass(definition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(beanClass.isAnnotationPresent(Extension.class)){
                Extension extension =  beanClass.getAnnotation(Extension.class);
                if (Objects.nonNull(extension)) {
                    String[] beanNames = extension.overrideBeanNames();
                    for (String beanName : beanNames) {
                        if (registry.containsBeanDefinition(beanName)) {
                            registry.removeBeanDefinition(beanName);
                        }
                    }
                    if (StringUtils.hasText(extension.beanName())) {
                        String simpleName = definition.getBeanClass().getSimpleName();
                        simpleName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
                        if (registry.containsBeanDefinition(simpleName)) {
                            registry.removeBeanDefinition(simpleName);
                        }
                        registry.registerBeanDefinition(extension.beanName(), definition);
                    }
                }
            }
        }
    }
}
