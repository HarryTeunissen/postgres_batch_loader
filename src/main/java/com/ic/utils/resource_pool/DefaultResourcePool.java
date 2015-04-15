package com.ic.utils.resource_pool;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ic.utils.transform.Filter;
import com.ic.utils.transform.Functor;

public class DefaultResourcePool<R> implements ResourcePool<R> {
	
	private static Logger log = LoggerFactory.getLogger(DefaultResourcePool.class.getName());


	private Functor<R> functor;
	private List<R> resources = new ArrayList<R>();

	public DefaultResourcePool(Functor<R> functor) {
		super();
		this.functor = functor;
	}

	public synchronized R aquire() {

		if (resources.size() == 0) {
			try {
				return functor.create();
			} catch (Exception e) {
				throw new RuntimeException("Inavlid functor " + e.getMessage()) ;
			}
		}

		return checkoutCachedResource(resources.remove(resources.size() - 1));

	}

	protected R checkoutCachedResource(R resource) {
		return resource;
	}

	protected R checkinCachedResource(R resource) {
		return resource;
	}

	
	public synchronized void release(R resource) {
		resources.add(checkinCachedResource(resource));
	}

	protected synchronized List<R> remove(Filter<R> filter) {

		List<R> removed = new ArrayList<R>(resources.size());
		List<R> retained = new ArrayList<R>(resources.size());

		for (R r : resources) {
			if (filter.isMember(r)) {
				destory(r) ;
				removed.add(r);
			} else {
				retained.add(r);
			}
		}

		return removed;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
	
	protected void destory(R resource) {
		if( resource instanceof AutoCloseable) {
			try {
				((AutoCloseable) resource).close();
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			} 
		}
	}

	public synchronized void dispose() {
		for (R r : resources) {
			destory(r) ;
		}
		this.resources.clear();
	}
}