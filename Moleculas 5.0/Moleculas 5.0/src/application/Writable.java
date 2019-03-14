package application;

import java.io.BufferedWriter;

public interface Writable {
	
	public void write(BufferedWriter writer) throws Exception;
	public String getName();
	public void setName(String name);
}
