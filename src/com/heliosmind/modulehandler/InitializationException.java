package com.heliosmind.modulehandler;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * A cumulative exceptions and errors report of all the exceptions and potential errors that may
 * have come up during the module loading process.
 * 
 * @author Samuel Beausoleil
 */
public class InitializationException extends Exception {
    private static final long serialVersionUID = -3798308314798633971L;

    /**
     * Chronological list of the exceptions.
     */
    private LinkedList<Exception> exceptions;

    private HashMap<String, MissingHandlerException> handlerExceptions;

    private LinkedList<DependencyException> dependencyExceptions;

    public InitializationException() {
	exceptions = new LinkedList<Exception>();
	handlerExceptions = new HashMap<String, MissingHandlerException>();
	dependencyExceptions = new LinkedList<DependencyException>();
    }

    /**
     * Returns the map for MissingHandlerExceptions.
     * 
     * @return a map containing all of the MissingHandlerExceptions encountered.
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

    public void registerMissingHandlerException(String extension, File fileName) {
	// Check if there already is an exception for that extension
	boolean createException = true;
	for (Entry<String, MissingHandlerException> entry : handlerExceptions.entrySet()) {
	    if (entry.getKey().equalsIgnoreCase(extension)) {
		createException = false;
		entry.getValue().registerUnloadedFile(fileName);
	    }
	}

	if (createException) {
	    MissingHandlerException e = new MissingHandlerException(extension, fileName);
	    exceptions.add(e);
	    handlerExceptions.put(extension, e);
	}
    }

    public void registerException(Exception e) {
	if (e instanceof DependencyException)
	    dependencyExceptions.add((DependencyException) e);
	exceptions.add(e);
    }

    public boolean hasExceptions() {
	return exceptions.size() > 0;
    }
}
