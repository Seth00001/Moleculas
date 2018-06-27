package engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Logger{
	public static Logger instance = new Logger();
	private String path = "Log.txt";
	private BufferedWriter writer;
	
	
	
	private Logger() {
		try {
			File f = new File(path);
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			writer = new BufferedWriter(fw);
		}
		catch(Exception e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void write(String s) throws IOException {
		writer.write(s);	
		writer.flush();
	}

	@SuppressWarnings("unchecked")
	public void finalize() throws IOException {
		writer.flush();
		writer.close();
	}
	
	
	
	
	
	
	
}
