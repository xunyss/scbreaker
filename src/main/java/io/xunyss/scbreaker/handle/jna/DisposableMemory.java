package io.xunyss.scbreaker.handle.jna;

import com.sun.jna.Memory;

/**
 *
 * @author XUNYSS
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
