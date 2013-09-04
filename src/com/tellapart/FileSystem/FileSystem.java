package com.tellapart.FileSystem;

import java.util.*;

import com.tellapart.FileSystem.File.FileType;
import com.tellapart.FileSystem.Exceptions.*;

public class FileSystem implements FileOps{
	public ArrayList <Drive>drives;
	
	public FileSystem(){
		drives = new ArrayList<Drive>();
	}	

	/**
	 * Checks if the file system already has a drive with a give name
	 * @param name name of the drive to look for.
	 * @return true if drive with the given name exists else false.
	 */
	private boolean hasDrive(String name) {
		for(Drive each_drive: drives){
			if(each_drive.getName().equals(name))
				return true;
		}
		return false;
	}
	
	private Drive getDrive(String name) {
		for(Drive each_drive: drives){
			if(each_drive.getName().equals(name))
				return each_drive;
		}
		return null;
	}


	@Override
	public File create(FileType type, String name, String parent_path)
			throws PathNotFoundException, PathExistsException,
			IllegalFSOperationException {
		File result = null;
		FileContainer parent = null;
		if(type != File.FileType.DRIVE){
			parent = (FileContainer)getFile(parent_path);		
			if(parent == null)
				throw new PathNotFoundException("No parent exists at parent path: "+parent_path);
			File the_file = null;			
			try {
				the_file = getFile(parent_path+"/"+name);
			} catch (PathNotFoundException e) {
				// Do nothing.
				the_file = null;
			}
			if(the_file != null){
				throw new PathExistsException(the_file.getType()+" with same path("+parent_path+"/"+name+") exists");
			}
		} else{
			if(hasDrive(name))
				throw new PathExistsException(type+" with same path name exists");
		}
		switch(type){
		case DRIVE:
			Drive d = new Drive(name);
			result = (File)d;
			drives.add(d);
			break;
		case FOLDER:
			Folder f = new Folder(name, parent_path+"/"+name, parent);
			result = (File)f;
			break;
		case TEXT_FILE:
			TextFile t = new TextFile(name, parent_path+"/"+name, parent, "");
			result = (File)t;
			break;
		case ZIP_FILE:
			ZipFile z = new ZipFile(name, parent_path +"/"+name, parent);
			result = (File)z;
			break;
		}
		return result;
	}

	@Override
	public void delete(String path) throws PathNotFoundException {
		File f = getFile(path);
		if(f == null)
			throw new PathNotFoundException("No file or folder exists at "+path);
		try {
			f.delete();
		} catch (PathNotFoundException e) {
			throw new PathNotFoundException("Could not delete the file at path "+path);
		}		
	}

	@Override
	public void move(String source_path, String destination_path)
			throws PathNotFoundException, PathExistsException,
			IllegalFSOperationException {
		if(source_path == null || source_path.equals(""))
			throw new IllegalFSOperationException("Empty source path");
		if(destination_path == null || destination_path.equals(""))
			throw new IllegalFSOperationException("Empty destination path");
		File the_file = getFile(source_path);
		if(the_file == null){
			throw new IllegalFSOperationException("Invalid source path");
		}
		if(the_file.getType() == File.FileType.DRIVE){
			throw new IllegalFSOperationException("Drive cannot be moved");
		}
		File the_destination_container = getFile(destination_path);
		if(!(the_destination_container instanceof FileContainer))
			throw new IllegalFSOperationException(String.format("Destination must be of container type(%s,%s,%s)",File.FileType.DRIVE,File.FileType.FOLDER,File.FileType.ZIP_FILE));
		((FileContainer)the_destination_container).addFile(the_file);
		((FileContainer)the_file.getParent()).deleteFile(the_file);
		the_file.setPath(destination_path+"/"+the_file.getName());
	}

	@Override
	public void WriteToFile(String path, String the_content)
			throws PathNotFoundException, NotATextFileException {
		File the_file = getFile(path);
		if (!(the_file instanceof TextFile))
			throw new NotATextFileException(path);		
		((TextFile) the_file).updateContent(the_content);
	}
	
	/**
	 * Returns the File object at the given file path. 
	 * @param path path of the file with each entity separated by "/". 
	 * @return
	 * @throws PathNotFoundException
	 */
	
	private File getFile(String path) throws PathNotFoundException{
		if(path == null)
			return null;
		if(path.equals(""))
			return null;
		String[] pathArray = path.split("/");		
		Drive d = getDrive(pathArray[0]);
		File result = null;
		if(pathArray.length > 1){
			result = d.getFile(Arrays.copyOfRange(pathArray, 1, pathArray.length));
			if(result == null){
				throw new PathNotFoundException(path + " not a valid path");
			}
		}else{
			return (File)d;
		}
		return result;
	}


}
