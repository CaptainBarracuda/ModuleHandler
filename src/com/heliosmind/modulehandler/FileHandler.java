package com.heliosmind.modulehandler;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * An adapter between the ModuleHandler library and the exterior code.
 * The contract of this class is to provide means to identify which files it can read and load them.
 * 
 * @author Samuel Beausoleil
 *
 */
public abstract class FileHandler {

    /**
     * A list of all readable extensions.
     * Extensions are formated to remove the period that may be at their beginning when received.
     */
    private String[] readableExtensions;
    
    public FileHandler(String[] readableExtensions) {
	this.readableExtensions = formatExtensions(readableExtensions);
    }
    
    /**
     * Returns the readableExtensions.
     * @return the readableExtensions
     */
    public String[] getReadableExtensions() {
        return readableExtensions;
    }

    /**
     * Sets the value of readableExtensions to that of the parameter.
     * @param readableExtensions the readableExtensions to set
     */
    public void setReadableExtensions(String[] readableExtensions) {
        this.readableExtensions = formatExtensions(readableExtensions);
    }

    
    /**
     * Removes the period that may precede the extension itself.
     * @param readableExtensionse
     * @return
     */
    public static String[] formatExtensions(String[] extensions) {
	LinkedList<String> formatedExtensions = new LinkedList<>();
	for (String extension : extensions) {
	    if (extension.startsWith("."))
		extension = extension.substring(1);
	    formatedExtensions.add(extension);
	}
	return formatedExtensions.toArray(new String[extensions.length]);
    }

    /**
     * Checks if this FileHandler can read this file type.
     * @param extension the extension of the file type.
     * @return <tt>true</tt> if it can read files with the specified extension.
     */
    public boolean canRead(String extension) {
	for (String readableExtension : readableExtensions)
	    if (readableExtension.equalsIgnoreCase(extension))
		return true;
	return false;
    }
    
    public boolean equals(FileHandler f) {
	return Arrays.equals(readableExtensions, f.readableExtensions);
    }
    
    public int hashCode() {
	int hash = 0;
	
	/* IMPLEMENTATION NOTE
	 * 31 is used as a prime number, as recommended in Effective Java.
	 */
	for (String ext : readableExtensions)
	    hash = hash * 31 + ext.hashCode();
	
	return hash;
    }
    
    /**
     * Reads the file and makes a ModuleMetadata out of it.
     * @param file the file to read
     * @return the ModuleMetada that was contained within the file.
     */
    public abstract ModuleMetadata read(File file);
}
