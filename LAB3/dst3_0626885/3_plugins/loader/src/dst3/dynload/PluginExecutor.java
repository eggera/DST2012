package dst3.dynload;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import net.contentobjects.jnotify.JNotify;
//import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

public class PluginExecutor implements IPluginExecutor {

	// defs
	
	private final static Logger logger = Logger.getLogger("PluginExecutor");

	// state
	
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	private Map<String, ClassLoader> jarsClassLoader = new HashMap<String, ClassLoader>();
	
	private Set<File> directories = new HashSet<File>();
	
	private Map<File, Integer> watchedDirs = new HashMap<File, Integer>();
	
	private final int mask = 	JNotify.FILE_CREATED  | 
								JNotify.FILE_MODIFIED;
	private boolean watchSubTree = false;
	
	private boolean running = false;
	
	
	
	private void init() {
		
//		String libDir = System.getProperty("user.dir") + "/../../lib/jnotify";
//		String libraryPath = System.getProperty("java.library.path") + ":" + libDir;
//		libraryPath = "/home/blackstar/Dropbox/uni/DST/DST2012/LAB3/dst3_0626885/lib/jnotify";
//		System.setProperty("java.library.path", libraryPath);
		
		executorService.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
			
		});
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
			System.err.println("Not a directory: "+dir.getName());
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
	
	public void releaseResources() {
		executorService.shutdown();
	}

	
	private final JNotifyListener listener = new JNotifyListener() {

		@Override
		public void fileCreated(int wd, String rootPath, String name)
	    {
//			logger.debug("JNotifyTest.fileCreated() : wd #" + wd + " root = " + rootPath
//	                + ", " + name + ", extension = "+getFileExtension(name));
			
			logger.debug("file created: "+name);
			
			if(isJarFile(name)) {
		        
		        try {
		        	String path = rootPath + System.getProperty("file.separator") + name;
		        	
					JarFile jarFile = new JarFile(path);
					
					processJarFile(jarFile, path);
					
				} catch (IOException e) {
					logger.debug("fileCreated(): not a valid jar file, "+name);
				}	        
	        
			}
			else
				logger.debug("other file");
				
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

	        if(isJarFile(name)) {
		        
		        try {
		        	String path = rootPath + System.getProperty("file.separator") + name;
		        	
					JarFile jarFile = new JarFile(path);
					
					processJarFile(jarFile, path);
					
				} catch (IOException e) {
//					logger.warn("fileModified(): not a valid jar file, "+name);
				}
	        
			}
			else
				logger.debug("other file");
	        
	    }

		@Override
		public void fileRenamed(int wd, String rootPath, String oldName, String newName)
	    {   
	        System.out.println("JNotifyTest.fileRenamed() : wd #" + wd + " root = " + rootPath + ", " + oldName + " -> " + newName);
	    }   
		
	};
	
	// helper
	
	private boolean isJarFile(String fileName) {
		if(getFileExtension(fileName).equals("jar"))
			return true;
		return false;
	}
	
	private boolean isClassFile(String fileName) {
		if(getFileExtension(fileName).equals("class"))
			return true;
		return false;
	}
	
	private String getBinaryName(String fileName) {
		if(isClassFile(fileName)) {
			String binaryName = fileName.replace(System.getProperty("file.separator").charAt(0), '.');
			return removeFileExtension(binaryName);
		}
		else
			return null;
	}
	
	private String getFileExtension(String fileName) {
		if(fileName.lastIndexOf('.') != -1)
			return fileName.substring(fileName.lastIndexOf('.')+1);
		else
			return "";
	}
	
	private String removeFileExtension(String fileName) {
		return fileName.substring(0,fileName.lastIndexOf('.'));
	}
	
	
	private void processJarFile(JarFile jarFile, String path) throws MalformedURLException {
		
		logger.debug("jar file modified");
		logger.debug("jar file name = "+jarFile.getName());
		logger.debug("jar file size = "+jarFile.size());
		
		File file = new File(jarFile.getName());
		
//		logger.debug("URL from file: "+file.toURI().toURL());
//		logger.debug("URI from file: "+file.toURI());
		
		
		ClassLoader classLoader = null;
		
		if( jarsClassLoader.containsKey(path) )
			classLoader = jarsClassLoader.get(path);
		
		else {
			classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());
			jarsClassLoader.put(path, classLoader);
		}
		
		Enumeration<JarEntry> files = jarFile.entries();
		
		while( files.hasMoreElements() ) {
			JarEntry entry = files.nextElement();

			if(isClassFile(entry.getName())) {
				
				String binaryName = getBinaryName(entry.getName());
			
				try {								
					Class<?> clazz = Class.forName(binaryName,false,classLoader);

					if( IPluginExecutable.class.isAssignableFrom(clazz) ) {
//						logger.debug("INSTANCE of IPluginExecutable: "+binaryName);
						
						try {
							final IPluginExecutable plugin = (IPluginExecutable) clazz.newInstance();
							
							executorService.execute(new Runnable() {
								@Override
								public void run() {
									plugin.execute();
								}
							});
						} catch (InstantiationException e) {
							logger.warn("when creating new instance, "+e.getMessage());
						} catch (IllegalAccessException e) {
							logger.warn("when creating new instance, "+e.getMessage());
						}
		
					}
					else
						logger.debug("Not an instance of IPluginExecutable: "+binaryName);
				} catch (ClassNotFoundException e) {
					logger.warn("No class found, "+e.getMessage());
				}
			}
		}
		
	}
	
}
