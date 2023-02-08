package org.springframework.plugin;

import org.springframework.plugin.annotation.Plugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 */
public class PluginRepository {
    public Map<Class<?>, Plugin> getPluginRepo() {
        return pluginRepo;
    }
    private Map<Class<?>,Plugin> pluginRepo = new ConcurrentHashMap<Class<?>,Plugin>();
}
