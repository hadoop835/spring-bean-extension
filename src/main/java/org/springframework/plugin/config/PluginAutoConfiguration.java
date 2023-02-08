package org.springframework.plugin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.PluginExecutor;
import org.springframework.plugin.PluginRepository;
import org.springframework.plugin.register.PluginBootstrap;
import org.springframework.plugin.register.PluginRegister;

/**
 * @author Administrator
 */
@Configuration
public class PluginAutoConfiguration {
    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(PluginBootstrap.class)
    public PluginBootstrap bootstrap() {
        return new PluginBootstrap();
    }

    @Bean
    @ConditionalOnMissingBean(PluginRepository.class)
    public PluginRepository repository() {
        return new PluginRepository();
    }

    @Bean
    @ConditionalOnMissingBean(PluginExecutor.class)
    public PluginExecutor executor() {
        return new PluginExecutor();
    }

    @Bean
    @ConditionalOnMissingBean(PluginRegister.class)
    public PluginRegister register() {
        return new PluginRegister();
    }
}
