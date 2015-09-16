package com.heliosmind.logic.init;

/**
 * Exception thrown when a module part of a dependency chain requires as a dependency a module that is already part of the dependency chain.
 * 
 * @author Samuel Beausoleil
 *
 */
public class LoopedDependenciesException extends DependencyException {
    private static final long serialVersionUID = -5900448488669782027L;

    public LoopedDependenciesException(ModuleMetadata[] loop) {
	super(makeMessage(loop));
    }

    public static String makeMessage(ModuleMetadata[] loop) {
	StringBuilder message = new StringBuilder("Dependency loop formed by the following modules: ");
	for (int i = 0; i < loop.length; i++) {
	    message.append(loop[i].getClassName());
	    if (i + 1 < loop.length)
		message.append(" -> ");
	}
	return message.toString();
    }
}
