/**
 * 
 */
package com.heliosmind.logic.init;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

/**
 * Manages the initialization of dynamic modules in a program, as well as their dependencies.
 * 
 * @author Samuel Beausoleil
 */
public class Initializer {

    /**
     * This set contains the name of all the classes linked to initialization that have already been
     * loaded.
     */
    private static Set<AbstractModule> loadedModules = new HashSet<>();

    /**
     * Contains the registered file handlers to read and parse the ModuleMetadata in file form.
     */
    private static Set<FileHandler> fileHandlers = new HashSet<>();

    /**
     * Returns the fileHandlers.
     * 
     * @return the fileHandlers
     */
    public static Set<FileHandler> getFileHandlers() {
	return fileHandlers;
    }

    /**
     * Sets the value of fileHandlers to that of the parameter.
     * 
     * @param fileHandlers
     *            the fileHandlers to set
     */
    public static void setFileHandlers(Set<FileHandler> fileHandlers) {
	Initializer.fileHandlers = fileHandlers;
    }

    /**
     * Registers a FileHandler for usage.
     * 
     * @param handler
     * @return <tt>true</tt> if there was no other FileHandler that already handled that file type
     *         and this was added to the collection.
     */
    public static boolean registerFileHandler(FileHandler handler) {
	return fileHandlers.add(handler);
    }

    /**
     * Returns the loaded modules.
     * 
     * @return the loadedClasses
     */
    public static Set<AbstractModule> getLoadedClasses() {
	return loadedModules;
    }

    /**
     * Sets the value of loadedClasses to that of the parameter.
     * 
     * @param loadedClasses
     *            the loadedClasses to set
     */
    public static void setLoadedClasses(Set<AbstractModule> loadedClasses) {
	Initializer.loadedModules = loadedClasses;
    }

    /**
     * Indicates if a module has already been loaded.
     * 
     * @param moduleName
     * @return <tt>true</tt> if the module has been loaded.
     */
    public static boolean isLoaded(String moduleName) {
	for (AbstractModule module : loadedModules)
	    if (module.getClass().getName().equals(moduleName))
		return true;
	return false;
    }
    
    /**
     * Loads all of the modules in a directory.
     * Note that this is not recursive.
     * @param dir The directory containing all of the modules.
     * @throws DependencyException
     */
    public static void loadModules(File dir) throws ModuleHandlerException {
	LinkedList<File> files = makeFilesList(dir.listFiles((pathName) -> !pathName.isDirectory()));
	ListIterator<File> iterator = files.listIterator();
	File file = null;
	String extension = null;
	while ((file = iterator.next()) != null) {
	    // Get the file's extension
	    extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
	    
	    // Find the correct file handler
	    FileHandler handler = null;
	    Iterator<FileHandler> handlers = fileHandlers.iterator();
	    while ((handler = handlers.next()) != null)
		if (handler.canRead(extension))
		    break;
	    // If there are no handlers, log the exception and continue the loop
	    
	}
    }

    private static LinkedList<File> makeFilesList(File[] listFiles) {
	LinkedList<File> files = new LinkedList<>();
	for (File file : listFiles)
	    files.add(file);
	return files;
    }
}