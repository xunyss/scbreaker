package io.xunyss.scbreaker;

import java.util.List;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.exec.PumpStreamHandler;
import io.xunyss.commons.io.LineProcessingOutputStream;

/**
 *
 * @author XUNYSS
 */
public class ScBreaker {
	
	// TODO: (OS != Windows) 일 경우 exception 발생시킬 것!
	
	
	static final String HANDLE_EXE = "C:\\Portable Programs\\SysinternalsSuite\\handle.exe";
	
	static final String[] PROCS = {"WINWORD.EXE"};
	
	public List<String> getOpenFiles() {
		
		return null;
	}
	
	public void handle() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(new LineProcessingOutputStream() {
			boolean record = false;
			@Override
			protected void processLine(String line) {
				if (line.equals("------------------------------------------------------------------------------")) {
					record = false;
				}
				else {
					for (int i = 0; i < PROCS.length; i++) {
						if (line.startsWith(PROCS[i])) {
							record = true;
							break;
						}
					}
				}
				
				if (record) {
					System.out.println(line);
				}
			}
		}));
		
		try {
			processExecutor.execute(HANDLE_EXE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
//		File f = new File("C:\\Users\\kbank\\AppData\\Local\\Temp\\~DFBF7B3C98BD1F52F0.TMP");
//		FileUtils.copy(f, new File("/downloads/out.doc"));
	}
}
