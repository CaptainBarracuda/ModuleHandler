package com.heliosmind.modulehandler;

import java.io.Serializable;

/**
 * Information regarding one of the classes to be loaded during the program's initialization phase.
 * 
 * @author Samuel Beausoleil
 */
public class ModuleMetadata implements Serializable {
    private static final long serialVersionUID = -3867228299678431682L;

    /**
     * The full name of the master class of the module to be loaded.
     * <p>
     * The full name is the package name followed by the name of the class. For example, the full
     * name of the String class is: <tt>java.lang.String</tt>.
     */
    private String className;

    /**
     * An array of all the dependencies of this class. These modules must be initialized before
     * initialization of this class.
     * <p>
     * These modules must be identified with the full name of their master class. For example, the
     * full name of the String class is: <tt>java.lang.String</tt>.
     */
    private String[] dependencies;

    /**
     * An array of all the optional dependencies of this class.
     * <p>
     * These modules may or may not be already initialized at the initialization moment of this
     * module. Therefore, nothing should be done at the first initialization of this module.
     * Instead, wait for the call of the method <tt>postInit()</tt> to be made to apply changes
     * following the optional dependencies that are present (or not present).
     * <p>
     * These modules must be identified with the full name of their master class. For example, the
     * full name of the String class is: <tt>java.lang.String</tt>.
     */
    private String[] optionalDependencies;

    /**
     * Constructs a ModuleMetadata.
     * 
     * @param className
     * @param dependencies
     * @param optionalDependencies
     */
    public ModuleMetadata(String className, String[] dependencies, String[] optionalDependencies) {
	this.className = className;
	this.dependencies = dependencies;
	this.optionalDependencies = optionalDependencies;
    }

    /**
     * Returns the name of the class to be loaded.
     * 
     * @return the name of the class to be loaded
     */
    public String getClassName() {
	return className;
    }

    /**
     * Sets the name of the class to be loaded.
     * 
     * @param className
     *            the className to set
     */
    public void setClassName(String className) {
	this.className = className;
    }

    /**
     * Returns the dependencies.
     * 
     * @return the dependencies
     */
    public String[] getDependencies() {
	return dependencies;
    }

    /**
     * Sets the value of dependencies to that of the parameter.
     * 
     * @param dependencies
     *            the dependencies to set
     */
    public void setDependencies(String[] dependencies) {
	this.dependencies = dependencies;
    }

    /**
     * Returns the optionalDependencies.
     * 
     * @return the optionalDependencies
     */
    public String[] getOptionalDependencies() {
	return optionalDependencies;
    }

    /**
     * Sets the value of optionalDependencies to that of the parameter.
     * 
     * @param optionalDependencies
     *            the optionalDependencies to set
     */
    public void setOptionalDependencies(String[] optionalDependencies) {
	this.optionalDependencies = optionalDependencies;
    }

    /**
     * Indicates whether or not the module has dependencies.
     * 
     * @return <tt>true</true> if there is one or more dependencies (without counting
     *         optionalDependencies)
     */
    public boolean hasDependencies() {
	return dependencies != null && dependencies.length > 0;
    }

    /**
     * Indicates whether or not the module has optional dependencies.
     * 
     * @return <tt>true</true> if there is one or more optional dependencies
     */
    public boolean hasOptionalDependencies() {
	return optionalDependencies != null && optionalDependencies.length > 0;
    }
}
