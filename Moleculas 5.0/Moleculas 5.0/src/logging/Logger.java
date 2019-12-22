package logging;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public static final Logger log = new Logger();
	
	
	private static final String ROOT_FOLDER = "Logs";
    private final String ExperimentDataContainer = String.format("Experiment_[%s]", getTimestamp());
    private final String snapshotFolderName = "Snapshots";
    private final String snapshotInvariantNamePart = "Snapshot_";
    private final String LogFileName = "Log.txt";
    
    
    private BufferedWriter integrityLogWriter;
    private BufferedWriter temperatureLogWriter;
    private BufferedWriter logWriter;
    
    
    protected Logger() 
    {
    	try {
			initializeLoggingSession();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void initializeLoggingSession() throws IOException {
    	File snapshotsFolder = new File(String.format("%s\\%s\\%s\\", ROOT_FOLDER, ExperimentDataContainer, snapshotFolderName));
    	snapshotsFolder.mkdirs(); 	
    	
//    	integrityLogWriter = new BufferedWriter(new FileWriter(new File(String.format("%s\\%s\\%s", ROOT_FOLDER, ExperimentDataContainer, integrityLogFileName)), true));
//    	temperatureLogWriter = new BufferedWriter(new FileWriter(new File(String.format("%s\\%s\\%s", ROOT_FOLDER, ExperimentDataContainer, tempereratureLogFileName)), true));
    	logWriter = new BufferedWriter(new FileWriter(new File(String.format("%s\\%s\\%s", ROOT_FOLDER, ExperimentDataContainer, LogFileName)), true));
    }
    
    public void println(String s) {
    	String message = String.format("Time: %s <nanos: %s>|  %s", getTimestamp(), System.nanoTime(),  s);
    	System.out.println(message);
    	try {
			logWriter.write(message);
			logWriter.newLine();
			logWriter.flush();
		} catch (Exception e) {
			System.err.println(String.format("Failed to append stream: <logWriter> \r\n	%s", (Object)e.getStackTrace() ));
		}
    }
    
    public static String getTimestamp() { 
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy MM dd (HH_mm_ss)");
	    Date now = new Date();
	    return sdfDate.format(now);    	
    }
    
    public void logSnapshot(Writable loggedOject) {
    	Logger.log.println("Logging snapshot...");
    	try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(new File(String.format("%s\\%s\\%s\\%s%s", 
	    			ROOT_FOLDER, 
	    			ExperimentDataContainer, 
	    			snapshotFolderName, 
	    			snapshotInvariantNamePart,
	    			loggedOject.getName())), true));
	    	
	    	loggedOject.write(writer);
	    	writer.flush();
	    	writer.close();
    	}
    	catch(Exception e) {
    		System.err.println(e.getMessage());
    	}
    	Logger.log.println("Logging snapshot finished");
    }
    
    public void logDebugSnapshot(Writable loggedOject) {
    	Logger.log.println("Logging debug snapshot...");
    	try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(new File(loggedOject.getName()), true));
	    	
	    	loggedOject.write(writer);
	    	
			writer.flush();
	    	writer.close();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	finally {
    		Logger.log.println("Debug snapshot is logged");
    	}
    }
    
    @Override
    public void finalize() {
    	
    	try {
    		integrityLogWriter.flush();
    		integrityLogWriter.close();
    	}
    	catch(Exception e) {
    		
    	}
    	
		try {
			temperatureLogWriter.flush();
			temperatureLogWriter.close();
    	}
    	catch(Exception e) {
    		
    	}

		try {
			logWriter.flush();
			logWriter.close();
		}
		catch(Exception e) {
			
		}
		
    }

}