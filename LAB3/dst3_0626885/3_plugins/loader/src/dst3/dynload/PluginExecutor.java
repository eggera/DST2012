package dst3.dynload;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

import net.contentobjects.jnotify.JNotify;
//import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

public class PluginExecutor implements IPluginExecutor {

	// defs
	
	private final static Logger logger = Logger.getLogger("PluginExecutor");

	// state
	
	private Set<File> directories = new HashSet<File>();
	
	private Map<File, Integer> watchedDirs = new HashMap<File, Integer>();
	
	private final int mask = 	JNotify.FILE_CREATED  | 
								JNotify.FILE_MODIFIED;
	private boolean watchSubTree = true;
	
	private boolean running = false;
	
	
	public PluginExecutor() {
		init();
	}
	
	
	private void init() {
		
//		String libDir = System.getProperty("user.dir") + "/../../lib/jnotify";
//		String libraryPath = System.getProperty("java.library.path") + ":" + libDir;
//		libraryPath = "/home/blackstar/Dropbox/uni/DST/DST2012/LAB3/dst3_0626885/lib/jnotify";
//		System.setProperty("java.library.path", libraryPath);
	}
    
    
    /**
     * Adds a directory to monitor.
     * May be called before and also after start has been called.
     * @param dir the directory to monitor.
     */
	@Override
	public void monitor(File dir) {
		
		if( dir.isDirectory() ) {

			if(running) {
				try {
					int watchId = JNotify.addWatch(dir.getPath(), mask, watchSubTree, listener);
					watchedDirs.put(dir, watchId);
					directories.add(dir);
				} catch (Exception e) {
					System.err.println("Could not add directory "+dir+" to monitor");
				}
			}
			else {
				directories.add(dir);
			}
		}
		else
			System.err.println("No directory");
	}

	
	/**
     * Stops monitoring the specified directory.
     * May be called before and also after start has been called.
     * @param dir the directory which should not be monitored anymore.
     */
	@Override
	public void stopMonitoring(File dir) {
		
		if( dir.isDirectory() && watchedDirs.containsKey(dir) ) {
			
			int watchId = watchedDirs.get(dir);
			try {
				JNotify.removeWatch(watchId);
				watchedDirs.remove(dir);
				directories.remove(dir);
			} catch (Exception e) {
				System.err.println("Could not remove directory "+dir+" from monitor");
			}
		}
	}

	
	/**
     * Starts the plugin executor.
     * All added directories will be monitored and any .jar file processed.
     * If there are any implementations of IPluginExecutor they are executed
     * within own threads.
     */
	@Override
	public void start() {
		
		for( File dir : directories ) {
			if( !watchedDirs.containsKey(dir) ) {
				try {
					int watchId = JNotify.addWatch(dir.getPath(), mask, watchSubTree, listener);
					watchedDirs.put(dir, watchId);
					
				} catch (Exception e) {
					System.err.println("Could not add directory "+dir+" to monitor");
				}
			}
		}
		running = true;
	}
	
	
	/**
     * Stops the plugin executor.
     * The monitoring of directories and the execution
     * of the plugins should stop as soon as possible.
     */
	@Override
	public void stop() {
		
		for( File dir : watchedDirs.keySet() ) {
			try {
				JNotify.removeWatch(watchedDirs.get(dir));
			} catch (Exception e) {
				System.err.println("Could not remove directory "+dir+" from monitor");
			}
		}
		running = false;
	}

	
	private final JNotifyListener listener = new JNotifyListener() {

		@Override
		public void fileCreated(int wd, String rootPath, String name)
	    {
			System.out.println("JNotifyTest.fileCreated() : wd #" + wd + " root = " + rootPath
	                + ", " + name + ", extension = "+getFileExtension(name));
				
	    }


		@Override
		public void fileDeleted(int wd, String rootPath, String name)
	    {   
	        System.out.println("JNotifyTest.fileDeleted() : wd #" + wd + " root = " + rootPath
	                + ", " + name);
	        
	    }   

		@Override
		public void fileModified(int wd, String rootPath, String name)
	    {   
	        System.out.println("JNotifyTest.fileModified() : wd #" + wd + " root = " + rootPath + ", " + name);
	        
	        if(isJarFile(name)) {
		        
		        
//		        File file = new File(name);
		        
		        try {
					JarFile jarFile = new JarFile(name);
					System.out.println("jar file size = "+jarFile.size());
					Enumeration<JarEntry> files = jarFile.entries();
					
					while( files.hasMoreElements() ) {
						JarEntry entry = files.nextElement();
						System.out.println("entry: "+entry.getName());
					}
					
				} catch (IOException e) {
					System.err.println("while creating jar file, "+e.getMessage());
					e.printStackTrace();
				}
		        
//		        System.out.println("filename = "+file.getName());
		        
		        // no directory!
//		        System.out.println("nr of files: "+file.list().length);
	        
	        
			}
			else
				System.out.println("other file");
	        
	    }   

		@Override
		public void fileRenamed(int wd, String rootPath, String oldName, String newName)
	    {   
	        System.out.println("JNotifyTest.fileRenamed() : wd #" + wd + " root = " + rootPath + ", " + oldName + " -> " + newName);
	    }   
		
	};
	
	// helper
	
	private boolean isJarFile(String fileName) {
		if(getFileExtension(fileName) != null) {
			if(getFileExtension(fileName).equals("jar"))
				return true;
		}
		return false;
	}
	
	private String getFileExtension(String fileName) {
		if(fileName.lastIndexOf('.') != -1)
			return fileName.substring(fileName.lastIndexOf('.')+1);
		else
			return null;
	}
	
	
}
