package com.heliosmind.logic.init;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public abstract class FileHandler {

    protected String[] readableExtensions;
    
    public FileHandler(String[] readableExtensions) {
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
    
    public abstract ModuleMetadata read(File file);
}
