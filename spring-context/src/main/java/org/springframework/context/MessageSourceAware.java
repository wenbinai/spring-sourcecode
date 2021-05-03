package org.springframework.context;

import org.springframework.beans.factory.Aware;

/**
 * Interface to be implemented by any object that wishes to be notified
 * of the MessageSource (typically the ApplicationContext) that it runs in.
 *
 * <p>Note that the MessageSource can usually also be passed on as bean
 * reference (to arbitrary bean properties or constructor arguments), because
 * it is defined as bean with name "messageSource" in the application context.
 * <p>
 * 注意，MessageSource通常也可以作为bean引用（传递给任意bean属性或构造函数参数）传递，因为
 * 在应用程序上下文中，它被定义为名称为“ messageSource”的bean
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @see ApplicationContextAware
 * @since 1.1.1
 */
public interface MessageSourceAware extends Aware {

	/**
	 * Set the MessageSource that this object runs in.
	 * <p>Invoked after population of normal bean properties but before an init
	 * callback like InitializingBean's afterPropertiesSet or a custom init-method.
	 * Invoked before ApplicationContextAware's setApplicationContext.
	 *
	 * @param messageSource message source to be used by this object
	 */
	void setMessageSource(MessageSource messageSource);

}
