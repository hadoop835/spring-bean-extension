package org.springframework.plugin.register;

import org.springframework.plugin.PluginConfig;
import org.springframework.plugin.core.Plugin;
import org.springframework.plugin.core.PluginRegistry;

public abstract class AbstractPluginExecutor {

    /**
     *
     * @param targetClz
     * @param pluginRegistry
     * @param <T>
     * @return
     */
    public <T extends Plugin<PluginConfig>> T execute(Class<T> targetClz, PluginRegistry<T,PluginConfig>  pluginRegistry) {
         return pluginRegistry.getRequiredPluginFor(loadPluginConfig(targetClz));
    }

    /**
     *
     * @param targetClz
     * @return
     */
    protected abstract   PluginConfig loadPluginConfig(Class<?> targetClz);

}
