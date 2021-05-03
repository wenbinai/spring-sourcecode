
package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**
 * The root interface for accessing a Spring bean container.
 * <p>
 * 访问spring bean 容器的根接口
 *
 * <p>This is the basic client view of a bean container;
 * <p>
 * BeanFactory 是用户视角的bean 容器
 * <p>
 * further interfaces such as {@link ListableBeanFactory} and
 * {@link org.springframework.beans.factory.config.ConfigurableBeanFactory}
 * <p>
 * are available for specific purposes.
 * ListableBeanFactroy 和 ConfigurableBeanFactory 是用来实现更多目的
 *
 * <p>This interface is implemented by objects that hold a number of bean definitions, each uniquely identified by a String name.
 * <p>
 * 这个接口被包含多个bean definitions(每个bean definition 都有一个唯一的name)的对象实现
 * <p>
 * Depending on the bean definition,the factory will return either an independent instance of a contained object(the Prototype design pattern),
 * or a single shared instance (a superior alternative to the Singleton design pattern, in which the instance is a singleton in the scope of the factory).
 * Which type of instance will be returned depends on the bean factory configuration: the API is the same.
 * <p>
 * 根据bean definition, bean factory 将在原型模式下返回一个独立的实例, 单例模式下, 返回一个共享的实例 (主要根据factory configuration, 两种模式下的API 是相同的)
 * <p>
 * Since Spring 2.0, further scopes are available depending on the concrete application context (e.g. "request" and "session" scopes in a web environment).
 * <p>
 * Spring2.0 后, 根据具体的application context,  可以提供更多scope的bean
 *
 * <p>The point of this approach is that the BeanFactory is a central registry
 * of application components, and centralizes configuration of application
 * components (no more do individual objects need to read properties files,
 * for example). See chapters 4 and 11 of "Expert One-on-One J2EE Design and
 * Development" for a discussion of the benefits of this approach.
 * <p>
 * 这种方法的重点是BeanFactory是应用程序组件的中央注册表, 并集中了应用程序组件的配置(例如, 不再需要单个对象读取属性文件)
 *
 * <p>Note that it is generally better to rely on Dependency Injection ("push" configuration) to configure application objects through setters
 * or constructors, rather than use any form of "pull" configuration like a BeanFactory lookup.
 * <p>
 * 注意: 依赖注入最好通过setter/construct配置, 而不是使用任何形式的"pull"配置(例如BeanFactory查找)
 * <p>
 * Spring's Dependency Injection functionality is implemented using this BeanFactory interface and its subinterfaces.
 * <p>
 * Spring 的依赖注入功能是使用此beanfactory 接口及其子接口实现的
 *
 * <p>Normally a BeanFactory will load bean definitions stored in a configuration source (such as an XML document), and use the {@code org.springframework.beans} package to configure the beans.
 *
 * <p>
 * However, an implementation could simply return Java objects it creates as necessary directly in Java code.
 * <p>
 * 但是, 有一个实现可以直接根据需要直接在Java代码中 返回其创建的Java对象
 * <p>
 * There are no constraints on how the definitions could be stored: LDAP, RDBMS, XML, properties file, etc.
 * 定义的存储方式没有任何限制
 * <p>
 * Implementations are encouraged to support references amongst beans (Dependency Injection).
 * <p>
 * 鼓励实现支持Bean之间应用的功能
 * <p>
 * In contrast to the methods in ListableBeanFactory, all of the
 * operations in this interface will also check parent factories if this is a HierarchicalBeanFactory. If a bean is not found in this factory instance,
 * the immediate parent factory will be asked. Beans in this factory instance are supposed to override beans of the same name in any parent factory.
 * <p>
 * 和 ListableBeanFactory的方法相比, 此接口中所有操作还将检查父工厂(如果实现为HierarchicalBeanFactory)
 * 如果在此工厂实例中未找到bean, 那么将在直接父工厂查找. 该工厂
 * 实例中Bean 支持 覆盖父工厂中同名的Bean
 *
 *
 * <p>Bean factory implementations should support the standard bean lifecycle interfaces as far as possible. The full set of initialization methods and their standard order is:
 * <p>
 * Bean 工厂实现应尽可能支持标准Bean生命周期接口, 全套初始化方法及其标准顺序为:
 *
 * <ol>
 * <li>1 BeanNameAware's {@code setBeanName}
 *
 * <li>2 BeanClassLoaderAware's {@code setBeanClassLoader}
 *
 * <li>3 BeanFactoryAware's {@code setBeanFactory}
 *
 * <li>4 EnvironmentAware's {@code setEnvironment}
 *
 * <li>5 EmbeddedValueResolverAware's {@code setEmbeddedValueResolver}
 *
 * <li>6 ResourceLoaderAware's {@code setResourceLoader}
 * <p>
 * (only applicable when running in an application context)
 *
 * <li>7 ApplicationEventPublisherAware's {@code setApplicationEventPublisher}
 * <p>
 * (only applicable when running in an application context)
 *
 *
 * <li>8 MessageSourceAware's {@code setMessageSource}
 * (only applicable when running in an application context)
 *
 * <li>9 ApplicationContextAware's {@code setApplicationContext}
 * (only applicable when running in an application context)
 *
 * <li>10 ServletContextAware's {@code setServletContext}
 * (only applicable when running in a web application context)
 *
 * <li>11 {@code postProcessBeforeInitialization} methods of BeanPostProcessors
 *
 * <li>12 InitializingBean's {@code afterPropertiesSet}
 *
 * <li>13 a custom init-method definition
 *
 * <li>14 {@code postProcessAfterInitialization} methods of BeanPostProcessors
 *
 * </ol>
 *
 * <p>On shutdown of a bean factory, the following lifecycle methods apply:
 * <p>
 *  请用以下的生命周期方法关闭bean 工厂
 * <ol>
 * <li>{@code postProcessBeforeDestruction} methods of DestructionAwareBeanPostProcessors
 *
 * <li>DisposableBean's {@code destroy}
 *
 * <li>a custom destroy-method definition
 *
 * </ol>
 */
public interface BeanFactory {

	/**
	 * Used to dereference a {@link FactoryBean} instance and distinguish it from
	 * beans <i>created</i> by the FactoryBean. For example, if the bean named
	 * {@code myJndiObject} is a FactoryBean, getting {@code &myJndiObject}
	 * will return the factory, not the instance returned by the factory.
	 * <p>
	 * 用来取消引用FactoryBean实例并将其与FactoryBean创建的bean 区别开来, 例如,
	 * &myJndiObject(其中myJndiObject是一个FactoryBean) 将返回工厂而不是通过工厂返回的实例
	 */
	String FACTORY_BEAN_PREFIX = "&";


	/**
	 * Return an instance, which may be shared or independent, of the specified bean.
	 * <p>This method allows a Spring BeanFactory to be used as a replacement for the
	 * Singleton or Prototype design pattern. Callers may retain references to
	 * returned objects in the case of Singleton beans.
	 * 返回一个实例，该实例可以是指定bean的共享或独立的。 此方法允许使用Spring BeanFactory替代Singleton或Prototype设计模式。
	 * 对于Singleton bean，调用者可以保留对返回对象的引用。
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * 将别名转换回相应的规范bean名称
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 * 如果在工厂实例没有找到将去父工厂查找
	 *
	 * @param name the name of the bean to retrieve
	 * @return an instance of the bean
	 * @throws NoSuchBeanDefinitionException if there is no bean with the specified name
	 * @throws BeansException                if the bean could not be obtained
	 */
	Object getBean(String name) throws BeansException;

	/**
	 * Return an instance, which may be shared or independent, of the specified bean.
	 * <p>Behaves the same as {@link #getBean(String)}, but provides a measure of type
	 * safety by throwing a BeanNotOfRequiredTypeException if the bean is not of the
	 * required type. This means that ClassCastException can't be thrown on casting
	 * the result correctly, as can happen with {@link #getBean(String)}.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 *
	 * @param name         the name of the bean to retrieve
	 * @param requiredType type the bean must match; can be an interface or superclass
	 * @return an instance of the bean
	 * @throws NoSuchBeanDefinitionException  if there is no such bean definition
	 * @throws BeanNotOfRequiredTypeException if the bean is not of the required type
	 * @throws BeansException                 if the bean could not be created
	 */
	<T> T getBean(String name, Class<T> requiredType) throws BeansException;

	/**
	 * Return an instance, which may be shared or independent, of the specified bean.
	 * <p>Allows for specifying explicit constructor arguments / factory method arguments,
	 * overriding the specified default arguments (if any) in the bean definition.
	 *
	 * 允许指定显式构造函数参数/工厂方法参数覆盖bean定义中指定的默认参数(如果有)
	 * @param name the name of the bean to retrieve
	 * @param args arguments to use when creating a bean instance using explicit arguments
	 *             (only applied when creating a new instance as opposed to retrieving an existing one)
	 * @return an instance of the bean
	 * @throws NoSuchBeanDefinitionException if there is no such bean definition
	 * @throws BeanDefinitionStoreException  if arguments have been given but
	 *                                       the affected bean isn't a prototype
	 * @throws BeansException                if the bean could not be created
	 * @since 2.5
	 */
	Object getBean(String name, Object... args) throws BeansException;

	/**
	 * Return the bean instance that uniquely matches the given object type, if any.
	 * <p>This method goes into {@link ListableBeanFactory} by-type lookup territory
	 * but may also be translated into a conventional by-name lookup based on the name
	 * of the given type. For more extensive retrieval operations across sets of beans,
	 * use {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
	 *
	 * @param requiredType type the bean must match; can be an interface or superclass
	 * @return an instance of the single bean matching the required type
	 * @throws NoSuchBeanDefinitionException   if no bean of the given type was found
	 * @throws NoUniqueBeanDefinitionException if more than one bean of the given type was found
	 * @throws BeansException                  if the bean could not be created
	 * @see ListableBeanFactory
	 * @since 3.0
	 */
	<T> T getBean(Class<T> requiredType) throws BeansException;

	/**
	 * Return an instance, which may be shared or independent, of the specified bean.
	 * <p>Allows for specifying explicit constructor arguments / factory method arguments,
	 * overriding the specified default arguments (if any) in the bean definition.
	 * <p>This method goes into {@link ListableBeanFactory} by-type lookup territory
	 * but may also be translated into a conventional by-name lookup based on the name
	 * of the given type. For more extensive retrieval operations across sets of beans,
	 * use {@link ListableBeanFactory} and/or {@link BeanFactoryUtils}.
	 *
	 * @param requiredType type the bean must match; can be an interface or superclass
	 * @param args         arguments to use when creating a bean instance using explicit arguments
	 *                     (only applied when creating a new instance as opposed to retrieving an existing one)
	 * @return an instance of the bean
	 * @throws NoSuchBeanDefinitionException if there is no such bean definition
	 * @throws BeanDefinitionStoreException  if arguments have been given but
	 *                                       the affected bean isn't a prototype
	 * @throws BeansException                if the bean could not be created
	 * @since 4.1
	 */
	<T> T getBean(Class<T> requiredType, Object... args) throws BeansException;

	/**
	 * Return a provider for the specified bean, allowing for lazy on-demand retrieval
	 * of instances, including availability and uniqueness options.
	 *
	 * 返回指定bean的provider, 允许按需的实例延迟检索, 包括可用性和唯一性选项
	 *
	 * @param requiredType type the bean must match; can be an interface or superclass
	 * @return a corresponding provider handle
	 * @see #getBeanProvider(ResolvableType)
	 * @since 5.1
	 */
	<T> ObjectProvider<T> getBeanProvider(Class<T> requiredType);

	/**
	 * Return a provider for the specified bean, allowing for lazy on-demand retrieval
	 * of instances, including availability and uniqueness options.
	 *
	 * @param requiredType type the bean must match; can be a generic type declaration.
	 *                     Note that collection types are not supported here, in contrast to reflective
	 *                     injection points. For programmatically retrieving a list of beans matching a
	 *                     specific type, specify the actual bean type as an argument here and subsequently
	 *                     use {@link ObjectProvider#orderedStream()} or its lazy streaming/iteration options.
	 * @return a corresponding provider handle
	 * @see ObjectProvider#iterator()
	 * @see ObjectProvider#stream()
	 * @see ObjectProvider#orderedStream()
	 * @since 5.1
	 */
	<T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType);

	/**
	 * Does this bean factory contain a bean definition or externally registered singleton
	 * instance with the given name?
	 * <p>If the given name is an alias, it will be translated back to the corresponding
	 * canonical bean name.
	 * <p>If this factory is hierarchical, will ask any parent factory if the bean cannot
	 * be found in this factory instance.
	 * <p>If a bean definition or singleton instance matching the given name is found,
	 * this method will return {@code true} whether the named bean definition is concrete
	 * or abstract, lazy or eager, in scope or not. Therefore, note that a {@code true}
	 * return value from this method does not necessarily indicate that {@link #getBean}
	 * will be able to obtain an instance for the same name.
	 *
	 * @param name the name of the bean to query
	 * @return whether a bean with the given name is present
	 */
	boolean containsBean(String name);

	/**
	 * Is this bean a shared singleton? That is, will {@link #getBean} always
	 * return the same instance?
	 * <p>Note: This method returning {@code false} does not clearly indicate
	 * independent instances. It indicates non-singleton instances, which may correspond
	 * to a scoped bean as well. Use the {@link #isPrototype} operation to explicitly
	 * check for independent instances.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 *
	 * @param name the name of the bean to query
	 * @return whether this bean corresponds to a singleton instance
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * @see #getBean
	 * @see #isPrototype
	 */
	boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

	/**
	 * Is this bean a prototype? That is, will {@link #getBean} always return
	 * independent instances?
	 * <p>Note: This method returning {@code false} does not clearly indicate
	 * a singleton object. It indicates non-independent instances, which may correspond
	 * to a scoped bean as well. Use the {@link #isSingleton} operation to explicitly
	 * check for a shared singleton instance.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 *
	 * @param name the name of the bean to query
	 * @return whether this bean will always deliver independent instances
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * @see #getBean
	 * @see #isSingleton
	 * @since 2.0.3
	 */
	boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

	/**
	 * Check whether the bean with the given name matches the specified type.
	 * More specifically, check whether a {@link #getBean} call for the given name
	 * would return an object that is assignable to the specified target type.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 *
	 * @param name        the name of the bean to query
	 * @param typeToMatch the type to match against (as a {@code ResolvableType})
	 * @return {@code true} if the bean type matches,
	 * {@code false} if it doesn't match or cannot be determined yet
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * @see #getBean
	 * @see #getType
	 * @since 4.2
	 */
	boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException;

	/**
	 * Check whether the bean with the given name matches the specified type.
	 * More specifically, check whether a {@link #getBean} call for the given name
	 * would return an object that is assignable to the specified target type.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 *
	 * @param name        the name of the bean to query
	 * @param typeToMatch the type to match against (as a {@code Class})
	 * @return {@code true} if the bean type matches,
	 * {@code false} if it doesn't match or cannot be determined yet
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * @see #getBean
	 * @see #getType
	 * @since 2.0.1
	 */
	boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException;

	/**
	 * Determine the type of the bean with the given name. More specifically,
	 * determine the type of object that {@link #getBean} would return for the given name.
	 * <p>For a {@link FactoryBean}, return the type of object that the FactoryBean creates,
	 * as exposed by {@link FactoryBean#getObjectType()}. This may lead to the initialization
	 * of a previously uninitialized {@code FactoryBean} (see {@link #getType(String, boolean)}).
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 *
	 * @param name the name of the bean to query
	 * @return the type of the bean, or {@code null} if not determinable
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * @see #getBean
	 * @see #isTypeMatch
	 * @since 1.1.2
	 */
	@Nullable
	Class<?> getType(String name) throws NoSuchBeanDefinitionException;

	/**
	 * Determine the type of the bean with the given name. More specifically,
	 * determine the type of object that {@link #getBean} would return for the given name.
	 * <p>For a {@link FactoryBean}, return the type of object that the FactoryBean creates,
	 * as exposed by {@link FactoryBean#getObjectType()}. Depending on the
	 * {@code allowFactoryBeanInit} flag, this may lead to the initialization of a previously
	 * uninitialized {@code FactoryBean} if no early type information is available.
	 * <p>Translates aliases back to the corresponding canonical bean name.
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 *
	 * @param name                 the name of the bean to query
	 * @param allowFactoryBeanInit whether a {@code FactoryBean} may get initialized
	 *                             just for the purpose of determining its object type
	 * @return the type of the bean, or {@code null} if not determinable
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * @see #getBean
	 * @see #isTypeMatch
	 * @since 5.2
	 */
	@Nullable
	Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException;

	/**
	 * Return the aliases for the given bean name, if any.
	 * <p>All of those aliases point to the same bean when used in a {@link #getBean} call.
	 * <p>If the given name is an alias, the corresponding original bean name
	 * and other aliases (if any) will be returned, with the original bean name
	 * being the first element in the array.
	 * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
	 *
	 * @param name the bean name to check for aliases
	 * @return the aliases, or an empty array if none
	 * @see #getBean
	 */
	String[] getAliases(String name);

}
