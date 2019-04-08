package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import engine.ClusterData;
import engine.IntegrityDataCollector.LayerDataHandler;

public class Logger
{
    public final static Logger log = new Logger();
    
    private static final String ROOT_FOLDER = "Logs";
    private final String ExperimentDataContainer = String.format("Instance_[%s]", getTimestamp());
    private final String snapshotFolderName = "Snapshots";
    private final String snapshotInvariantNamePart = "Snapshot_";
    
    private final String voidsFolderName = "VoidsData";
    private final String voidsInvariantNamePart = "VoidSnapshot_";
    
    private final String integrityLogFileName = "Integrity.txt";
    private final String strengthLogFileName = "Strength.txt";
    private final String tempereratureLogFileName = "Temperature.txt";
    private final String LogFileName = "Log.txt";
    
    private BufferedWriter integrityLogWriter;
    private BufferedWriter strengthLogWriter;
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
    	
    	File voidsFolder = new File(String.format("%s\\%s\\%s\\", ROOT_FOLDER, ExperimentDataContainer, voidsFolderName));
    	voidsFolder.mkdirs();
    	
    	strengthLogWriter  = new BufferedWriter(new FileWriter(new File(String.format("%s\\%s\\%s", ROOT_FOLDER, ExperimentDataContainer, strengthLogFileName)), true));
    	integrityLogWriter = new BufferedWriter(new FileWriter(new File(String.format("%s\\%s\\%s", ROOT_FOLDER, ExperimentDataContainer, integrityLogFileName)), true));
    	temperatureLogWriter = new BufferedWriter(new FileWriter(new File(String.format("%s\\%s\\%s", ROOT_FOLDER, ExperimentDataContainer, tempereratureLogFileName)), true));
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
    
    public void logIntegritySnapshot(Writable loggedOject) {
    	Logger.log.println("Logging integrity snapshot...");
    	try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(new File(String.format("%s\\%s\\%s\\%s%s.txt", 
	    			ROOT_FOLDER, 
	    			ExperimentDataContainer, 
	    			voidsFolderName, 
	    			voidsInvariantNamePart,
	    			loggedOject.getName())), true));
	    	
	    	loggedOject.write(writer);
	    	writer.flush();
	    	writer.close();
    	}
    	catch(Exception e) {
    		System.err.println(e.getMessage());
    	}
    	Logger.log.println("Logging integrity snapshot finished");
    }

    public void logStrength(int step, double temp, ArrayList<ClusterData> strengthData) {
    	StringBuilder builder = new StringBuilder();
    	
    	for(ClusterData data : strengthData){
    		builder.append(String.format("%s;", data.size()));	
    	}
    	
    	try {
    		strengthLogWriter.write(String.format("{%-8d|%-20f| (%s)}\r\n", step, temp, builder.toString()));
    		strengthLogWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	Logger.log.println("StrengthData data writing finished");
    }
    
    public void logIntegrity(int step, double temp, ArrayList<ClusterData> integrityData) {
    	StringBuilder builder = new StringBuilder();
    	
    	for(ClusterData data : integrityData){
    		builder.append(String.format("%s;", data.size()));	
    	}
    	
    	try {
			integrityLogWriter.write(String.format("{%-8d|%-20f| (%s)}\r\n", step, temp, builder.toString()));
			integrityLogWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	Logger.log.println("Integrity data writing finished");
    }
    
    
    public void logTemperature(int step, double value) {    	
    	try {
    		temperatureLogWriter.write(String.format("%s | %s\r\n", step, value));
    		temperatureLogWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	finally {
    		Logger.log.println(String.format("Step: %s; Temperature: %s", step, value));
    	}
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
    		strengthLogWriter.flush();
    		strengthLogWriter.close();
    	}
    	catch(Exception e) {
    		
    	}
    	
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

