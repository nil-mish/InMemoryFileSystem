package com.tellapart.FileSystem;

import java.util.ArrayList;
import java.util.Arrays;

import com.tellapart.FileSystem.Exceptions.IllegalFSOperationException;
import com.tellapart.FileSystem.Exceptions.PathExistsException;
import com.tellapart.FileSystem.Exceptions.PathNotFoundException;

public abstract class FileContainer extends File{
	
	/**
	 * Holds the array of 
	 */
	protected ArrayList<File> container_content;
	
	public FileContainer(FileType type, String name, String path, int size, FileContainer parent) throws IllegalFSOperationException, PathExistsException{
		super(type, name, path, size, parent);
		container_content = new ArrayList<File>();
	}
	
	
	protected boolean hasFile(File the_file){
		
		return container_content.contains(the_file);
	
	}
	
	protected boolean hasNamedFile (String name){
		for(File a_file: container_content){
			if(a_file.name.equals(name))
				return true;
		}
		return false;
	}
	
	@Override
	protected void updateSize(){
		int newSize = 0;
		for(File f: container_content){
			newSize += f.getSize();
		}
		size = newSize;
		if(type != File.FileType.DRIVE){
			parent.updateSize();
		}
	}
	
	
	
	private FileContainer getNamedFileContainer(String name){
		for(File a_file: container_content){
			if(a_file.name.equals(name) && (a_file instanceof FileContainer))
				return (FileContainer)a_file;
			else{
				return null;
			}
		}
		return null;
	}
	
	protected File getFile(String[] path){
		if(path.length == 1){ // File should be in this container
			for(File f: container_content){
				if(f.getName().equals(path[0])){
					return f;
				}				
			}
			return null;
		}else{
			FileContainer next_container = getNamedFileContainer(path[0]);
			if(next_container != null){
				return next_container.getFile(Arrays.copyOfRange(path,1,path.length));
			}
		}
		return null;
	}


	public void deleteFile(File file) throws PathNotFoundException{
		if(container_content.contains(file)){			
			container_content.remove(file);
			updateSize();
		}
		else
			throw new PathNotFoundException("No file or folder at "+file.getPath());
	}


	public void addFile(File the_file) throws PathExistsException{
		if(hasNamedFile(the_file.getName())){
			throw new PathExistsException("Destination "+getPath()+" already has an entity with same name");
		}
		container_content.add(the_file);
		updateSize();
		
	}	

}
