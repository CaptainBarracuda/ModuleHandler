/**
 * 
 */
package com.heliosmind.logic.init;

import java.util.Arrays;

/**
 * This exception is thrown when an initialization class is missing one or more dependency.
 * @author Samuel Beausoleil
 *
 */
public class MissingDependencyException extends Exception {
    private static final long serialVersionUID = -956613802067775897L;

    public MissingDependencyException(String className, String[] missingDependencies) {
	super("The class " + className + " is missing the following dependencies: " + Arrays.toString(missingDependencies));
    }
}
