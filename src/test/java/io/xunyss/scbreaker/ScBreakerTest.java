package io.xunyss.scbreaker;

import java.nio.charset.Charset;
import java.util.List;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import org.junit.Assert;
import org.junit.Test;

import io.xunyss.commons.lang.ArrayUtils;
import io.xunyss.scbreaker.handle.jna.WinHandle;
import io.xunyss.scbreaker.handle.jna.WinProcess;

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
		List<Object[]> hlist = WinHandle.list(0);
		for (Object[] helem : hlist) {
			System.out.println(ArrayUtils.toString(helem));
		}
	}
}
