package com.tellapart.FileSystem;

import com.tellapart.FileSystem.Exceptions.IllegalFSOperationException;
import com.tellapart.FileSystem.Exceptions.PathExistsException;

public class ZipFile extends FileContainer{
	
	public ZipFile(String name, String path, FileContainer parent) throws IllegalFSOperationException, PathExistsException{
		super(File.FileType.ZIP_FILE, name, path,0,parent);		
	}
	
	@Override
	protected void updateSize(){
		int newSize = 0;
		for(File f: container_content){
			newSize += f.getSize();
		}
		size = (int) Math.ceil(0.5 * newSize);
		parent.updateSize();
	}

}
