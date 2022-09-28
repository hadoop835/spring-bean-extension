package org.springframework.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

import static org.springframework.util.Assert.notNull;

/**
 * @author chenzhh
 */
@Configuration
public class ExtensionScannerConfigurer implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ConfigurableEnvironment configurableEnvironment;

    private  ApplicationContext applicationContext;

    private  String   basePackage;

    /**
     * 处理扫描注解并实现注册
     *
     * @param beanDefinitionRegistry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathExtensionScanner scanner = new ClassPathExtensionScanner(beanDefinitionRegistry);
        //scanner.setBeanNameGenerator(new AnnotationBeanNameGenerator());
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> {
            Service service = metadataReader.getClassMetadata().getClass().getAnnotation(Service.class);
            return service!=null?true:false;
        });
        scanner.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
        scanner.setResourceLoader(this.applicationContext);
         this.basePackage = configurableEnvironment.getProperty("spring.extension.basePackage");
        if(!StringUtils.hasText(basePackage)){
            this.basePackage = configurableEnvironment.getProperty("spring.extension.base-package");
        }
        notNull(basePackage, "application.yaml 'spring.extension.basePackage or spring.extension.base-package' is required");
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ",; \t\n"));


    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    /**
     * 扫描包路径
     *
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {

    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.configurableEnvironment =  applicationContext.getBean(ConfigurableEnvironment.class);
        this.applicationContext = applicationContext;
    }
}
