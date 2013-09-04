package com.tellapart.FileSystem;


import com.tellapart.FileSystem.Exceptions.IllegalFSOperationException;
import com.tellapart.FileSystem.Exceptions.NotATextFileException;
import com.tellapart.FileSystem.Exceptions.PathExistsException;
import com.tellapart.FileSystem.Exceptions.PathNotFoundException;

public interface FileOps {
	/**
	 * 
	 * @param type
	 * @param name
	 * @param parent_path
	 * @return
	 * @throws PathNotFoundException
	 * @throws PathExistsException
	 * @throws IllegalFSOperationException
	 */
	public File create(File.FileType type, String name, String parent_path) throws PathNotFoundException, PathExistsException, IllegalFSOperationException;
	
	/**
	 * 
	 * @param path
	 * @throws PathNotFoundException
	 */
	public void delete(String path) throws PathNotFoundException;
	
	/**
	 * 
	 * @param source_path
	 * @param destination_path
	 * @throws PathNotFoundException
	 * @throws PathExistsException
	 * @throws IllegalFSOperationException
	 */
	public void move(String source_path, String destination_path) throws PathNotFoundException, PathExistsException, IllegalFSOperationException;
	
	/**
	 * 
	 * @param path
	 * @param the_content
	 * @throws PathNotFoundException
	 * @throws NotATextFileException
	 */
	public void WriteToFile(String path, String the_content) throws PathNotFoundException, NotATextFileException;
}
