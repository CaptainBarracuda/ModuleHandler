package com.heliosmind.modulehandler;

import java.io.File;
import java.util.LinkedList;

public class MissingHandlerException extends Exception {
    private static final long serialVersionUID = 6634496329838454376L;

    private String missingExtension;
    private LinkedList<File> unloadedFiles;
    
    public MissingHandlerException(String missingExtension, File... unloadedFiles) {
	this.missingExtension = missingExtension;
	this.unloadedFiles = new LinkedList<File>();
	for (File unloadedModule : unloadedFiles)
	    this.unloadedFiles.add(unloadedModule);
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
    public LinkedList<File> getUnloadedFiles() {
        return unloadedFiles;
    }

    /**
     * Sets the value of unloadedModules to that of the parameter.
     * @param unloadedFiles the unloadedModules to set
     */
    public void setUnloadedFiles(LinkedList<File> unloadedFiles) {
        this.unloadedFiles = unloadedFiles;
    }

    public void registerUnloadedFile(File unloadedFile) {
	unloadedFiles.add(unloadedFile);
    }
}
