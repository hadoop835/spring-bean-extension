package org.springframework.plugin;

import org.springframework.plugin.annotation.Plugin;
import org.springframework.plugin.register.AbstractPluginExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenzhonghua
 */
@Component
public class PluginExecutor extends AbstractPluginExecutor {
    @Resource
    private PluginRepository pluginRepository;


    @Override
    protected PluginConfig loadPluginConfig(Class<?> targetClz) {
        Plugin plugin =  pluginRepository.getPluginRepo().get(targetClz);
        PluginConfig pluginConfig = new PluginConfig();
        pluginConfig.setValue(plugin.value());
        return pluginConfig;
    }
}
