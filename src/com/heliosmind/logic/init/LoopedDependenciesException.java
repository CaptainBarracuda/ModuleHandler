package com.heliosmind.logic.init;

/**
 * Exception thrown when a module part of a dependency chain requires as a dependency a module that is already part of the dependency chain.
 * 
 * @author Samuel Beausoleil
 *
 */
public class LoopedDependenciesException extends Exception {
    private static final long serialVersionUID = -5900448488669782027L;

    public LoopedDependenciesException() {} // TODO
}
