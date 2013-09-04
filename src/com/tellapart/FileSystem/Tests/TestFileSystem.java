package com.tellapart.FileSystem.Tests;

import com.tellapart.FileSystem.*;
import com.tellapart.FileSystem.Exceptions.IllegalFSOperationException;
import com.tellapart.FileSystem.Exceptions.NotATextFileException;
import com.tellapart.FileSystem.Exceptions.PathExistsException;
import com.tellapart.FileSystem.Exceptions.PathNotFoundException;

public class TestFileSystem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileSystem fs = new FileSystem();
		try {
			String drive_path = "testDrive";
			Drive d = (Drive) fs.create(File.FileType.DRIVE, drive_path, drive_path);
			System.out.println("Drive added");
			TextFile f = (TextFile) fs.create(File.FileType.TEXT_FILE, "test_text_file", drive_path);
			System.out.println("Text file added");
			fs.WriteToFile("testDrive/test_text_file", "This is a Text File");
			System.out.println("Text file read");			
			System.out.println(f.readContent());
			fs.create(File.FileType.FOLDER, "folder1", drive_path);
			fs.create(File.FileType.FOLDER, "folder2", drive_path);
			fs.move(drive_path+"/"+"test_text_file", drive_path+"/"+"folder1");
			System.out.println(f.getPath());
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (PathExistsException e) {
			e.printStackTrace();
		} catch (IllegalFSOperationException e) {
			e.printStackTrace();
		} catch (NotATextFileException e) {
			e.printStackTrace();
		}
	}

}
