package com.ic.utils.resource_pool;

public interface ResourcePool<R> {
	
	public R aquire();

	public void release(R resource);

	public void dispose();

}
