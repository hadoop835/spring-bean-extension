package org.springframework.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.extension.annotation.Extension;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 扩展spring 注解
 *
 * @author chenzhh
 */
@Component
public class ClassPathExtensionScanner extends ClassPathBeanDefinitionScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathExtensionScanner.class);
    private static final Set<BeanDefinitionHolder> beanDefinitions =new HashSet<BeanDefinitionHolder>();
    /**
     * @param registry
     */
    public ClassPathExtensionScanner(BeanDefinitionRegistry registry) {
        super(registry,true);

    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        processBeanDefinitions(beanDefinitions);
        return beanDefinitionHolders;
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
                String [] beanNames = extension.overrideBeanNames();
                for(String beanName : beanNames){
                    registry.removeBeanDefinition(beanName);
                }
                if(StringUtils.hasText(extension.beanName())){
                    String simpleName =  beanClass.getSimpleName();
                    simpleName = simpleName.substring(0,1).toLowerCase()+simpleName.substring(1);
                    registry.removeBeanDefinition(simpleName);
                    registry.registerBeanDefinition(extension.beanName(),definition);
                }
            }
        }
    }
}
