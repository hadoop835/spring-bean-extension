# spring-bean-extension
spring-bean-extension

spring plugin 插件使用
#1、定义接口必须实现PluginI接口
#2、在实现类上实现Plugin注解，order值越大优先执行
#3、类使用注入
  @Autowired
  private PluginRegistry<接口类,PluginConfig> plugins;
  @Autowired
  private PluginExecutor pluginExecutor;
  调用
  接口类 = pluginExecutor.execute(接口类.class,plugins);
 #4、在启动入口注册
 @EnablePluginRegistries(value = {ShopPlugin.class, UserPlugin.class, ExtShopPlugin.class})
  
