package com.tellapart.FileSystem;

import com.tellapart.FileSystem.Exceptions.IllegalFSOperationException;
import com.tellapart.FileSystem.Exceptions.PathExistsException;

public class Folder extends FileContainer{
	
	public Folder(String name, String path, FileContainer parent) throws IllegalFSOperationException, PathExistsException{
		super(File.FileType.FOLDER, name, path,0,parent);		
	}
	
}
