package org.springframework.extension.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.extension.ExtensionScannerConfigurer;

import static org.springframework.util.Assert.notNull;

/**
 * @author chenzhh
 */
@Configuration
public class ExtensionAutoConfiguration {
    /**
     *
     * @return
     */
    @Bean
    public ExtensionScannerConfigurer extensionScannerConfigurer(ConfigurableEnvironment configurableEnvironment){
        String basePackage = configurableEnvironment.getProperty("spring.extension.basePackage");
        notNull(basePackage, "application.yaml 'spring.extension.basePackage' is required");
        ExtensionScannerConfigurer extensionScannerConfigurer = new ExtensionScannerConfigurer();
        extensionScannerConfigurer.setBasePackage(basePackage);
        return extensionScannerConfigurer;
    }

}
