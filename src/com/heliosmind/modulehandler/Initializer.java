/**
 * 
 */
package com.heliosmind.modulehandler;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

/**
 * Manages the dynamic initialization of modules in a program, as well as their dependencies.
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
     * Loads all of the modules in a directory. Note that this is not recursive.
     * 
     * @param dir
     *            The directory containing all of the modules.
     * @throws DependencyException
     */
    public static void loadModules(File dir, Object... providedObjects) throws InitializationException {
	InitializationException report = new InitializationException();
	LinkedList<ModuleMetadata> metadatas = getMetadata(dir, report);

	// Load the modules
	ListIterator<ModuleMetadata> iterator;
	ModuleMetadata meta;
	ModuleMetadata metaDependency;

	// Use limited foresight dependency loading algorithm
	int nSuccess = -1;
	while (nSuccess != 0) {
	    nSuccess = 0;
	    iterator = metadatas.listIterator();
	    while ((meta = iterator.next()) != null) { // TODO Set a label to here
		if (meta.hasDependencies()) {
		    for (String dependency : meta.getDependencies()) {
			if (isLoaded(dependency))
			    continue;

			metaDependency = find(dependency, metadatas);

			// If that dependency cannot be found, log a missing dependency exception
			if (metaDependency == null) {
			    report.registerException(new MissingDependencyException(dependency,
				    checkMissingDependencies(meta, metadatas)));
			    // TODO Replace by a continue to the label
			    break;
			}

			if (!canLoad(metaDependency)) {
			    // TODO Replace by a continue to the label
			    break;
			} else {
			    try {
				loadModule(metaDependency, providedObjects);
			    } catch (Exception e) {
				report.registerException(
					new MissingDependencyException(meta.getClassName(), dependency, e));
			    }
			    metadatas.remove(metaDependency);
			}
		    }
		}

		try {
		    loadModule(meta, providedObjects);
		} catch (Exception e) {
		    report.registerException(e);
		}
		iterator.remove();
		nSuccess++;
	    }
	}

	// Check if things have not been loaded
	if (!metadatas.isEmpty()) {
	    // If we are here, the only possible cause may be a dependency loop
// TODO
	}
    }

    /**
     * Lists all of the missing dependencies of module.
     * 
     * @param meta
     *            the module
     * @param metadatas
     *            the list of all other available modules
     * @return an array with the name of all the dependencies that could not be found. Returns an
     *         empty array if no dependencies are missing.
     */
    private static String[] checkMissingDependencies(ModuleMetadata meta, LinkedList<ModuleMetadata> metadatas) {
	// Checks all of the dependencies for missing ones
	LinkedList<String> missing = new LinkedList<>();
	for (String dependency : meta.getDependencies())
	    if (find(dependency, metadatas) == null && !isLoaded(dependency))
		missing.add(dependency);
	return missing.toArray(new String[missing.size()]);
    }

    /**
     * Checks if a module is ready to be loaded. Checks if all of the dependencies of the module are
     * already loaded.
     * 
     * @param meta
     *            the module
     * @return <tt>true</tt> if all of the dependencies of that module are loaded.
     */
    private static boolean canLoad(ModuleMetadata meta) {
	for (String dependency : meta.getDependencies())
	    if (!isLoaded(dependency))
		return false;
	return true;
    }

    /**
     * Loads a module.
     * 
     * @param meta
     *            the metadata of that module
     * @param providedObjects
     *            the objects to provide to the module for it's initialization
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void loadModule(ModuleMetadata meta, Object... providedObjects)
	    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	AbstractModule module = (AbstractModule) ClassLoader.getSystemClassLoader().loadClass(meta.getClassName())
		.newInstance();
	module.init(providedObjects);
	loadedModules.add(module);
    }

    /**
     * Finds a module's metadata from it's name inside of a list of modules' metadatas.
     * 
     * @param moduleName
     *            the name of the module
     * @param modules
     *            the list of modules' metadas
     * @return the metadata appropriate to that module.
     */
    private static ModuleMetadata find(String moduleName, LinkedList<ModuleMetadata> modules) {
	for (ModuleMetadata module : modules)
	    if (module.getClassName().equals(moduleName))
		return module;
	return null;
    }

    /**
     * Gets all of the metadas in a directory.
     * 
     * @param dir
     *            the directory containing the metadata
     * @param report
     *            the exceptions report into witch to store the possible exceptions.
     * @return a list of ModuleMetadata.
     */
    private static LinkedList<ModuleMetadata> getMetadata(File dir, final InitializationException report) {
	File[] files = dir.listFiles((pathName) -> !pathName.isDirectory());
	LinkedList<ModuleMetadata> meta = new LinkedList<>();
	String extension = null;
	FileHandler handler;
	Iterator<FileHandler> handlers;

	for (File file : files) {
	    // Get the file's extension
	    extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
	    // Find the correct file handler
	    handler = null;
	    handlers = fileHandlers.iterator();
	    while ((handler = handlers.next()) != null)
		if (handler.canRead(extension))
		    break;
	    // If there are no handlers, log the exception and continue the loop
	    if (handler == null) {
		report.registerMissingHandlerException(extension, file);
		continue;
	    }
	    // Read the file and get the metadata
	    meta.add(handler.read(file));
	}

	return meta;
    }
}