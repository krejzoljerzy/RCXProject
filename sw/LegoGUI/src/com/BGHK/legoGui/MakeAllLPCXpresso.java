package com.BGHK.legoGui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MakeAllLPCXpresso {    
    
	public static void compileAndLink() {
            try {
                // Execute command
                Process child = Runtime.getRuntime().exec(
                                "cmd /c start compilerCommand.bat ");
            } catch (IOException ex) {
                Logger.getLogger(MakeAllLPCXpresso.class.getName()).log(Level.SEVERE, null, ex);
            }

	

	}


	private static String findPathToCProject() {
		
		// Get path to java project.
		String actualPath = MakeAllLPCXpresso.class.getClassLoader()
				.getResource(".").getPath();
		
		// Get path to main repo.
		String stringBufferArray[] = actualPath.split("/sw");
		
		// Add local path to C project to repo path.
		String pathToCProject = stringBufferArray[0] + "\\fw\\legoRCX\\Debug";
		
		// Replace all slashes with backslashes.
		pathToCProject = pathToCProject.replace("/", "\\");
		
		// Remove first backslash.
		pathToCProject = pathToCProject.substring(1);
		return pathToCProject;
	}

	public static void createBatFile() throws IOException {

		// Create new bat file, remove if it existed before.
		File f;
		String wholeIncludes = "";
		f = new File("compilerCommand.bat");
		if (!f.exists()) {
			f.delete();
		}
		f.createNewFile();

		// Get path to C project.
		String pathToCProject = findPathToCProject();
                System.out.println(pathToCProject);
		

		try {
			// Open and write to batch file.
			FileWriter fstream = new FileWriter("compilerCommand.bat");
			BufferedWriter out = new BufferedWriter(fstream);
			
			String buffer = "make clean \r\n make all";
				out.write(buffer);
                                out.close();
                                fstream.close();
			}
                

		
		// Catch exception if any
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	public MakeAllLPCXpresso (){
            try {
                createBatFile();
            } catch (IOException ex) {
                Logger.getLogger(MakeAllLPCXpresso.class.getName()).log(Level.SEVERE, null, ex);
            }
		compileAndLink();

	}

}
