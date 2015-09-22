package com.heliosmind.modulehandler;

public abstract class AbstractModule {

    private ModuleMetadata metadata;

    
    public AbstractModule() {
	this.metadata = null;
    }
    
    /**
     * Constructs an AbstractModule with this metadata.
     * 
     * @param metadata
     */
    public AbstractModule(ModuleMetadata metadata) {
	this.setMetadata(metadata);
    }

    /**
     * Returns the metadata.
     * 
     * @return the metadata
     */
    public ModuleMetadata getMetadata() {
	return metadata;
    }

    /**
     * Sets the value of metadata to that of the parameter.
     * 
     * @param metadata
     *            the metadata to set
     */
    public void setMetadata(ModuleMetadata metadata) {
	this.metadata = metadata;
    }

    /**
     * Initializes the module. At this stage, only the <tt>dependencies</tt>, as defined in this
     * module's metadata have been initialized.
     * 
     * @param providedObjects
     *            An ensemble of objects that may be useful during the initialization method.
     *            Usually project dependent.
     */
    public abstract void init(Object... providedObjects);

    /**
     * Adapts the module with it's optional dependencies. At the stage of initialization, the
     * optional dependencies, as defined in this module's metadata have been initialized.
     * 
     * @param optionalDependencies
     *            An array of all the optional dependencies that were found and initialized.
     * @param providedObjects
     *            An ensemble of objects that may be useful during the initialization method.
     *            Usually project dependent.
     */
    public abstract void postInit(String[] optionalDependencies, Object... providedObjects);
}
