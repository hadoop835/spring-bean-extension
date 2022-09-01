package org.springframework.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.Set;

import static org.springframework.util.Assert.notNull;

/**
 * @author chenzhh
 */
public class ExtensionScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean {

    private  String  basePackage;

    /**
     * 处理扫描注解并实现注册
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
         ClassPathExtensionScanner  scanner = new ClassPathExtensionScanner(beanDefinitionRegistry);
         scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {


    }

    /**
     * 扫描包路径
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
