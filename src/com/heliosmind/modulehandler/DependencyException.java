package com.heliosmind.modulehandler;

/**
 * Superclass of all dependency related exceptions.
 * 
 * @author Samuel Beausoleil
 *
 */
public class DependencyException extends Exception {
    private static final long serialVersionUID = -971918380611469870L;

    public DependencyException() {
	super();
    }

    public DependencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public DependencyException(String message, Throwable cause) {
	super(message, cause);
    }

    public DependencyException(String message) {
	super(message);
    }

    public DependencyException(Throwable cause) {
	super(cause);
    }

    
}
