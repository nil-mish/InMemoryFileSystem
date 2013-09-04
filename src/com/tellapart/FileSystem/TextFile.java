package com.tellapart.FileSystem;

import com.tellapart.FileSystem.Exceptions.IllegalFSOperationException;
import com.tellapart.FileSystem.Exceptions.PathExistsException;

public class TextFile extends File{
	
	private String content;
	
	public TextFile(String name, String path, FileContainer parent, String content) throws IllegalFSOperationException, PathExistsException{
		super(File.FileType.TEXT_FILE, name, path,0,parent);
		this.content = content;
		updateSize();
	}
	
	@Override
	protected void updateSize(){
		if(content != null)
			this.setSize(content.length());
		else{
			this.setSize(0);
		}
		parent.updateSize();
	}
	
	/**
	 * Concatenates the new content to the original content of the file.
	 * @param str
	 */
	
	public void updateContent(String new_content){
		if(content == null){
			content = "";
		}
		content = content.concat(new_content);
		updateSize();		
	}
	
	public String readContent(){
		return ""+content;
	}

}
