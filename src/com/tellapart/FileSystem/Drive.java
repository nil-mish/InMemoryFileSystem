package com.tellapart.FileSystem;

import com.tellapart.FileSystem.Exceptions.IllegalFSOperationException;
import com.tellapart.FileSystem.Exceptions.PathExistsException;

public class Drive extends FileContainer{
		
	public Drive(String name) throws IllegalFSOperationException, PathExistsException{
		super(File.FileType.DRIVE, name, null,0,null);		
	}
}
