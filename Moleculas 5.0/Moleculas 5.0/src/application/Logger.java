package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public enum Logger {
    INSTANCE;
    private File root, session, log;
    private String title;
    private String ROOT_FOLDER = "EXPERIMENT_DUMPS";

    {
        title = getFolderTitle();
        initRootFolder();
        initSessionFolder(root);
        initLogFile(session,title+".txt");
    }

    private void initLogFile(File root, String title){
        log = new File(root,title);
        try {
            log.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSessionFolder(File root){
        session = new File(root,title);
        if(!session.exists()||!session.isDirectory()){
            session.mkdir();
        }
    }

    private void initRootFolder(){
        root = new File(ROOT_FOLDER);
        if(!root.exists()||!root.isDirectory()){
            root.mkdirs();
        }
    }

    private String getFolderTitle(){
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        sb.append(cal.get(Calendar.DAY_OF_MONTH)).append("-").append(1+cal.get(Calendar.MONTH)).append("-").append(cal.get(Calendar.YEAR)).append("_");
        sb.append(cal.get(Calendar.HOUR_OF_DAY)).append("-").append(cal.get(Calendar.MINUTE)).append("-").append(cal.get(Calendar.SECOND));
        return sb.toString();
    }

    public void write(String text) {
        System.out.println(text);
        FileWriter fr = null;
        try {
            fr = new FileWriter(log,true);
            fr.write(String.format("%-15s  %s \n",getCurrentTime(),text));
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startLog(){
        write("\t -Started-");
    }

    private String getCurrentTime(){
        Calendar cal = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(cal.get(Calendar.HOUR_OF_DAY)).append(":").append(cal.get(Calendar.MINUTE)).append(":").append(cal.get(Calendar.SECOND)).append(":").append(cal.get(Calendar.MILLISECOND));
        return sb.toString();
    }

    public File getSession() {
        return session;
    }
}
