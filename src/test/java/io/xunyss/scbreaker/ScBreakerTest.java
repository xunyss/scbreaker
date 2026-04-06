package io.xunyss.scbreaker;

import com.sun.jna.Platform;
import io.xunyss.commons.lang.ArrayUtils;
import io.xunyss.scbreaker.handle.jna.WinHandle;
import io.xunyss.scbreaker.handle.jna.WinProcess;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.List;

/**
 *
 * @author XUNYSS
 */
public class ScBreakerTest {
	
	@Test
	public void isWindow() {
		Assert.assertTrue(Platform.isWindows());
	}
	
	@Test
	public void charset() {
		System.out.println(Charset.defaultCharset());
	}
	
	@Test
	public void listProcess() {
		List<Object[]> plist = WinProcess.list();
		for (Object[] pelem : plist) {
			System.out.println(ArrayUtils.toString(pelem));
		}
	}
	
	@Test
	public void listHandle() {
		List<Object[]> hlist = WinHandle.list();
		for (Object[] helem : hlist) {
			System.out.println(ArrayUtils.toString(helem));
		}
	}
	
	@Test
	public void handle() {
		ScBreaker scBreaker = new ScBreaker();
		scBreaker.availableList();
	}
}
