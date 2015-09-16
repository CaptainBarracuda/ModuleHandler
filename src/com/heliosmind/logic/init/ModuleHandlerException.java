package com.heliosmind.logic.init;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.HashMap;

public class ModuleHandlerException extends Exception {
    private static final long serialVersionUID = -3798308314798633971L;

    /**
     * Chronological list of the exceptions.
     */
    private LinkedList<Exception> exceptions;

    private HashMap<String, MissingHandlerException> handlerExceptions;

    private LinkedList<DependencyException> dependencyExceptions;

    public ModuleHandlerException() {
	exceptions = new LinkedList<Exception>();
	handlerExceptions = new HashMap<String, MissingHandlerException>();
	dependencyExceptions = new LinkedList<DependencyException>();
    }

    /**
     * Returns the handlerExceptions.
     * 
     * @return the handlerExceptions
     */
    public HashMap<String, MissingHandlerException> getHandlerExceptions() {
	return handlerExceptions;
    }

    /**
     * Sets the value of handlerExceptions to that of the parameter.
     * 
     * @param handlerExceptions
     *            the handlerExceptions to set
     */
    public void setHandlerExceptions(HashMap<String, MissingHandlerException> handlerExceptions) {
	this.handlerExceptions = handlerExceptions;
    }

    /**
     * Returns the dependencyExceptions.
     * 
     * @return the dependencyExceptions
     */
    public LinkedList<DependencyException> getDependencyExceptions() {
	return dependencyExceptions;
    }

    /**
     * Sets the value of dependencyExceptions to that of the parameter.
     * 
     * @param dependencyExceptions
     *            the dependencyExceptions to set
     */
    public void setDependencyExceptions(LinkedList<DependencyException> dependencyExceptions) {
	this.dependencyExceptions = dependencyExceptions;
    }

    /**
     * Returns the chronological list of exceptions.
     * 
     * @return the exceptions
     */
    public LinkedList<Exception> getExceptions() {
	return exceptions;
    }

    /**
     * Sets the value of exceptions to that of the parameter.
     * 
     * @param exceptions
     *            the exceptions to set
     */
    public void setExceptions(LinkedList<Exception> exceptions) {
	this.exceptions = exceptions;
    }

    public void registerMissingHandlerException(String extension, String moduleName) {
	// Check if there already is an exception for that extension
	boolean createException = true;
	for (Entry<String, MissingHandlerException> entry : handlerExceptions.entrySet()) {
	    if (entry.getKey().equalsIgnoreCase(extension)) {
		createException = false;
		entry.getValue().registerUnloadedModule(moduleName);
	    }
	}

	if (createException) {
	    MissingHandlerException e = new MissingHandlerException(extension, moduleName);
	    exceptions.add(e);
	    handlerExceptions.put(extension, e);
	}
    }
}
