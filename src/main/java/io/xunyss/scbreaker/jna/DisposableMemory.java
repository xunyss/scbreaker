package io.xunyss.scbreaker.jna;

import com.sun.jna.Memory;

/**
 *
 */
public class DisposableMemory extends Memory {
	
	public DisposableMemory() {
	
	}
	
	public DisposableMemory(long size) {
		super(size);
	}
	
	
	@Override
	public synchronized void dispose() {
		super.dispose();
	}
}
