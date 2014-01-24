/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BGHK.legoGui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johns
 */
public class Programmer {
    String pathToFM;
    String pathToProject;
    String pathToHexFile;
    int portCom=1;
    int baudrate=9600;
    
    public Programmer(int pCom, int br){
        findPaths();
        portCom = pCom;
        baudrate = br;
        try {
            createBatFile();
        } catch (IOException ex) {
            Logger.getLogger(Programmer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
			// Execute command
            System.out.println("running command");
			Process child = Runtime.getRuntime().exec(
					"cmd /c start /b programmerCommand.bat ");

		} catch (IOException e) {
		}
    }
    
    private void findPaths() {
		
		// Get path to java project.
		pathToFM = this.getClass().getClassLoader().getResource(".").getPath();
		
		// Get path to main repo.
		String stringBufferArray[] = pathToFM.split("/sw");               
		
		// Add local path to C project to repo path.
		pathToFM = stringBufferArray[0] + "\\3rd party\\FM\\FM.exe";
                pathToProject = stringBufferArray[0] + "\\sw\\LegoGUI";
                String pathToDebug = stringBufferArray[0] + "\\fw\\legoRCX\\Debug";
		
                // Replace all slashes with escaped backslashes.
		pathToFM = pathToFM.replace("/", "\\");
                pathToProject = pathToProject.replace("/", "\\");
                pathToDebug = pathToDebug.replace("/", "\\");
                
		// Remove first backslash.
		pathToFM = pathToFM.substring(1);
                pathToProject = pathToProject.substring(1);
                pathToDebug = pathToDebug.substring(1);
                
                // Find hex file.
                pathToDebug = stringBufferArray[0] + "\\fw\\legoRCX\\Debug";
                File debug = new File(pathToDebug);
                File [] files = debug.listFiles();
                File hexFile = null;
                for (File f : files){
                    String fileName = f.getName();
                    int extIndex = fileName.lastIndexOf(".");
                    String extension = fileName.substring(extIndex, fileName.length());
                    if(extension.equals(".hex")){
                        hexFile = f;
                        break;
                    }
                }
                pathToHexFile = hexFile.getAbsolutePath();
	}
    
    private void createBatFile() throws IOException {

		// Create new bat file, remove if it existed before.
		File f;
		String wholeIncludes = "";
		f = new File("programmerCommand.bat");
		if (!f.exists()) {
			f.delete();
		}
		f.createNewFile();

		try {
			// Open and write to batch file.
			FileWriter fstream = new FileWriter("programmerCommand.bat");
			BufferedWriter out = new BufferedWriter(fstream);
			
			String buffer = "\""+pathToFM+"\" QUIET("+pathToProject+"\\programmer\\log.txt) COM("+portCom+", "+baudrate+") DEVICE(LPC1114/302,0,0) ERASE(DEVICE, PROTECTISP) HEXFILE("+pathToHexFile+", NOCHECKSUMS, NOFILL, PROTECTISP)";
				out.write(buffer);
                                out.close();
                                fstream.close();
			}            
		
		// Catch exception if any
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
