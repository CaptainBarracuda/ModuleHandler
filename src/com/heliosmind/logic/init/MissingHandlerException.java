package com.heliosmind.logic.init;

import java.util.LinkedList;

public class MissingHandlerException extends Exception {
    private static final long serialVersionUID = 6634496329838454376L;

    private String missingExtension;
    private LinkedList<String> unloadedModules;
    
    public MissingHandlerException(String missingExtension, String... unloadedModules) {
	this.missingExtension = missingExtension;
	this.unloadedModules = new LinkedList<String>();
	for (String unloadedModule : unloadedModules)
	    this.unloadedModules.add(unloadedModule);
    }

    /**
     * Returns the missingExtension.
     * @return the missingExtension
     */
    public String getMissingExtension() {
        return missingExtension;
    }

    /**
     * Sets the value of missingExtension to that of the parameter.
     * @param missingExtension the missingExtension to set
     */
    public void setMissingExtension(String missingExtension) {
        this.missingExtension = missingExtension;
    }

    /**
     * Returns the unloadedModules.
     * @return the unloadedModules
     */
    public LinkedList<String> getUnloadedModules() {
        return unloadedModules;
    }

    /**
     * Sets the value of unloadedModules to that of the parameter.
     * @param unloadedModules the unloadedModules to set
     */
    public void setUnloadedModules(LinkedList<String> unloadedModules) {
        this.unloadedModules = unloadedModules;
    }

    public void registerUnloadedModule(String unloadedModule) {
	unloadedModules.add(unloadedModule);
    }
}
