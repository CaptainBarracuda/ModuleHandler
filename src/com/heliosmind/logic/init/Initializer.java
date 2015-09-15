/**
 * 
 */
package com.heliosmind.logic.init;

import java.util.HashSet;
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


    public static boolean isLoaded(String moduleName) {
	for (AbstractModule module : loadedModules)
	    if (module.getClass().getName().equals(moduleName))
		return true;
	return false;
    }
}