package org.springframework.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.extension.annotation.Extension;

import java.util.Arrays;
import java.util.Set;

/**
 * 扩展spring 注解
 *
 * @author chenzhh
 */
public class ClassPathExtensionScanner extends ClassPathBeanDefinitionScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathExtensionScanner.class);

    /**
     * @param registry
     */
    public ClassPathExtensionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        processBeanDefinitions(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    protected void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        AbstractBeanDefinition definition;
        BeanDefinitionRegistry registry = getRegistry();
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (AbstractBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            Class<?> beanClass = null;
            try {
                beanClass = ClassLoader.getSystemClassLoader().loadClass(beanClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (beanClass.isAnnotationPresent(Extension.class)) {
                Extension extension = beanClass.getAnnotation(Extension.class);
                String [] beanNames = extension.name();
                for(String beanName : beanNames){
                    registry.removeBeanDefinition(beanName);
                }
            }
        }
    }
}
