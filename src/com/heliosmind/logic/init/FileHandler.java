package com.heliosmind.logic.init;

import java.io.File;
import java.util.Arrays;

public abstract class FileHandler {

    protected String[] readableExtensions;
    
    public FileHandler(String[] readableExtensions) {
	this.readableExtensions = readableExtensions;
    }
    
    public boolean canRead(String extension) {
	for (String readableExtension : readableExtensions)
	    if (readableExtension.equals(extension))
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
