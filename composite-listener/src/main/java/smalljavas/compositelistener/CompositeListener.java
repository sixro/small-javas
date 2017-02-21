package smalljavas.compositelistener;

import java.lang.reflect.*;
import java.util.*;

/**
 * Represents a composite listener able to act as the listener itself allowing
 * to "propagate" a call to the listeners contained.
 * 
 * <p>
 * PAY ATTENTION: it is a responsibility of the listener implementations to handle all
 * sort of exceptions. This object prevents that those error stop the notification of the
 * call to all the others, simply catching the errors and logging them on std err.
 */
public class CompositeListener<T> {
	
	private final Class<T> itemsType;
	private final List<T> listeners;

	private CompositeListener(Class<T> itemsType) {
		super();
		this.itemsType = itemsType;
		this.listeners = new LinkedList<>();
	}

	public static <T> CompositeListener<T> of(Class<T> itemsType) {
		return new CompositeListener<T>(itemsType);
	}
	
	public void add(T listener) {
		this.listeners.add(listener);
	}
	
	public void remove(T listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Returns a proxy of the specified listener allowing any call to it
	 * to be propagated to all the contained listeners.
	 * 
	 * @return the proxy acting like the desired listener
	 */
	public T proxy() {
		Object proxy = Proxy.newProxyInstance(
			Thread.currentThread().getContextClassLoader(), 
			new Class<?>[]{ itemsType }, 
			new CompositeListener._InvocationHandler(this.listeners)
		);
		return itemsType.cast(proxy);
	}
	
	public static class _InvocationHandler implements InvocationHandler {

		private final List<?> listeners;

		public _InvocationHandler(List<?> listeners) {
			this.listeners = listeners;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			listeners
				.parallelStream()
				.forEach(o -> {
					try {
						method.invoke(o, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						System.err.println("Unable to call method '" + method.getName() + "' on object " + o);
						e.printStackTrace(System.err);
					}
				});
			return null;
		}

	}

}
