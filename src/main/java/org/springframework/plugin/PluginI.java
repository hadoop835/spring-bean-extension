package org.springframework.plugin;

import org.springframework.plugin.core.Plugin;

/**
 * @author chenzhh
 */
public interface PluginI extends Plugin<PluginConfig> {
    @Override
    default boolean supports(PluginConfig pluginConfig) {
        return this.getClass().getSimpleName().equals(pluginConfig.getValue());
    }
}
