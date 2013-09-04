package com.tellapart.FileSystem;

import com.tellapart.FileSystem.Exceptions.IllegalFSOperationException;
import com.tellapart.FileSystem.Exceptions.PathExistsException;
import com.tellapart.FileSystem.Exceptions.PathNotFoundException;

public abstract class File {
	
	public enum FileType{
		DRIVE, FOLDER, TEXT_FILE, ZIP_FILE
				
	}

	protected FileType type;
	protected String name;
	protected String path;
	protected int size;
	protected FileContainer parent;
	
	
	public File(FileType type, String name, String path, int size, FileContainer parent) throws IllegalFSOperationException, PathExistsException{
		this.type = type;
		this.name = name;
		this.path = path;
		this.size = size;
		/*
		 * Should be handled by the FileSystem?
		 */
		if (!(this.type == File.FileType.DRIVE)){
			if(parent == null){
				throw new IllegalFSOperationException(this.type+" file entity needs a parent");
			}
			this.parent = parent;
			parent.addFile(this);
		}
	}
	
	public FileType getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	protected void setName(String newName){
		name = newName;
	}
	
	protected abstract void updateSize();

	
	public void setSize(int newSize){
		size = newSize;
	}
	
	public String getPath(){
		return path;
	}
	
	protected void setPath(String newPath){
		path = newPath;
	}
	
	public int getSize(){
		return size;
	}
	
	protected File getParent(){
		return parent;
	}
	
	void delete() throws PathNotFoundException{
		parent.deleteFile(this);
	}
	
	protected void setParent(FileContainer newParent){
		parent = newParent;
	}
	
	
}
