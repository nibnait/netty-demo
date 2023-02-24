package cc.tianbin.demo.netty.rpc.protocol.spring.reference;

import cc.tianbin.demo.netty.rpc.protocol.annotation.RemoteReference;
import cc.tianbin.demo.netty.rpc.protocol.spring.reference.config.RpcClientProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpringRpcReferencePostProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, BeanClassLoaderAware {

    private ClassLoader classLoader;

    private ApplicationContext applicationContext;

    private RpcClientProperties rpcClientProperties;

    // 保存发布的引用的 bean 信息
    private final Map<String, BeanDefinition> rpcBeanDefinitionMap = new ConcurrentHashMap<>();

    public SpringRpcReferencePostProcessor(RpcClientProperties rpcClientProperties) {

        this.rpcClientProperties = rpcClientProperties;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 在对象实例化之前调用
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 获取所有 bean 的名称循环
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                // 如果加载的类中有属性加了 RemoteReference 注解的话，就生成代理类
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.classLoader);

                // 遍历 clazz 中的对象中的所有的属性，把 class 中的属性作为参数传递给 parseRpcReference 方法
                ReflectionUtils.doWithFields(clazz, this::parseRpcReference);
            }

        }
        // 注入到容器里面
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.rpcBeanDefinitionMap.forEach((beaName, beanDefinition) -> {
            if (applicationContext.containsBean(beaName)) {
                return;
            }
            registry.registerBeanDefinition(beaName, beanDefinition);
        });
    }

    private void parseRpcReference(Field field) {
        RemoteReference remoteReference = AnnotationUtils.getAnnotation(field, RemoteReference.class);
        if (remoteReference != null) {
            // 如果有这个注解，就要生成代理类
            BeanDefinitionBuilder beanDefinitionBuilder =
                    BeanDefinitionBuilder.genericBeanDefinition(SpringRpcReferenceBean.class);
            beanDefinitionBuilder.addPropertyValue("interfaceClass", field.getType());
            beanDefinitionBuilder.addPropertyValue("serviceAddress", rpcClientProperties.getServiceAddress());
            beanDefinitionBuilder.addPropertyValue("servicePort", rpcClientProperties.getServicePort());
            BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            rpcBeanDefinitionMap.put(field.getName(), beanDefinition);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
