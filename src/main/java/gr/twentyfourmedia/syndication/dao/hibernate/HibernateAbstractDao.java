package gr.twentyfourmedia.syndication.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.ReflectionUtils;

import gr.twentyfourmedia.syndication.dao.AbstractDao;

/**
 * Generic Class's DAO Implementation
 * @param <T> Generic Class
 */
public abstract class HibernateAbstractDao<T extends Object> implements AbstractDao<T> {

	@Inject private SessionFactory sessionFactory;
	private Class<T> domainClass;
	
	/**
	 * Protected Method Allowing Subclasses To Perform Persistent Operations Against The Hibernate Session
	 * @return Hibernate Session
	 */
	protected Session getSession() {
		
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Reflection To Get Actual Domain Class
	 * @return Domain Class
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
		
		if(domainClass==null) {
			
			ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
			this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		
		return domainClass;
	}
	
	/**
	 * Reflection To Get Actual Domain Class Name
	 * @return Domain Class Name
	 */
	private String getDomainClassName() {
		
		return getDomainClass().getName();
	}
	
	/**
	 * Persist Generic Class Object. The Method Sets applicationDateUpdated Property If It Exists
	 * @param t Object To Be Persisted
	 */
	@Override
	public void persist(T t) {
		
		Method method = ReflectionUtils.findMethod(getDomainClass(), "setApplicationDateUpdated", new Class[] { Calendar.class });
		
		if(method!=null) {
			
			try {
				
				method.invoke(t, Calendar.getInstance());
			}
			catch(Exception exception) { /*Do Nothing*/
			
			}
		}
		
		getSession().persist(t);
		getSession().flush(); //Flush To Get Id Of Object Persisted
	}
	
	/**
	 * Merge Generic Class Object. The Method Sets applicationDateUpdated Property If It Exists
	 * @param t Object To Be Merged
	 */
	@Override
	public void merge(T t) {

		Method method = ReflectionUtils.findMethod(getDomainClass(), "setApplicationDateUpdated", new Class[] { Calendar.class });
		
		if(method!=null) {
			
			try {
				
				method.invoke(t, Calendar.getInstance());
			}
			catch(Exception exception) { /*Do Nothing*/
			
			}
		}		
		
		getSession().merge(t);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		
		return (T) getSession().get(getDomainClass(), id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		
		return (T) getSession().load(getDomainClass(), id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		
		return (List<T>) getSession().createQuery("FROM " + getDomainClassName()).list();
	}
		
	@Override
	public void delete(T t) {
		
		getSession().delete(t);
	}
	
	@Override
	public void deleteById(Serializable id) {
		
		delete(load(id));
	}
	
	@Override
	public Long count() {
	
		return (Long) getSession().createQuery("SELECT COUNT(*) FROM " + getDomainClassName()).uniqueResult();
	}
}