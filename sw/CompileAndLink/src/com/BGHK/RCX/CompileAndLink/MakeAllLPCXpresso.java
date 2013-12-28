package com.BGHK.RCX.CompileAndLink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompileAndLink {

	static String compileCommands = "arm-none-eabi-gcc"
			+ " -mcpu=cortex-m0 -mthumb -Wall -ffunction-sections -g -O0 -c -DSTM32F050C6 -DUSE_STDPERIPH_DRIVER -D__ASSEMBLY__ ";

	static String includePaths[] = {};

	static String[] filesToCompile = {};

	static String linkerCommand = "arm-none-eabi-gcc -mcpu=cortex-m0 -mthumb -nostartfiles -g";
	static String mapFile = "legoRCXMap.map";
	static String outPutElfFile = "legoRCX.elf";

	public static void compileAndLink() {

		try {
			// Execute command
			Process child = Runtime.getRuntime().exec(
					"cmd /c start compilerCommand.bat ");

		} catch (IOException e) {
		}

	}

	public static void listAllFiles() {

		// Obtain and store path to C project.
		String pathToCProject = findPathToCProject();
		File binFolder = new File (pathToCProject+"\\bin");
		File objFolder = new File (pathToCProject+"\\obj");
		
		// Create bin and obj directories if they don't exist.
		if (!binFolder.exists()){
			System.out.println("Creating \\bin folder");
			binFolder.mkdir();
		}
		if (!objFolder.exists()){
			System.out.println("Creating \\obj folder");
			objFolder.mkdir();
		}
		
		// Prepare dynamic tables to store all directories, and files in C project.
		List<String> allPaths = new ArrayList<>();
		List<String> crucialPaths = new ArrayList<>();
		List<String> sourceFiles = new ArrayList<>();	
		
		// Helper index used for path checking.
		int indexToNotCheckedPath = 1;
		
		// Start searching from root directory of C project.
		File projectPath = new File(pathToCProject);
		
		// List all objects in this directory.
		String[] list = projectPath.list();
		
		// Set parent to none.
		String parent = "";
		
		// Add root path to path list.
		allPaths.add("\\");
		
		// Now search for any subfolder in paths stored in allPaths list.
		for (int k = 0; k < allPaths.size(); k++) {
			for (int i = 0; i < list.length; i++) {
				
				// If listed item is a directory.
				if (!list[i].contains(".")) {
					allPaths.add(parent + "\\" + list[i]);
				}
			}
			// Check if we are not going out of the list area.
			if(indexToNotCheckedPath<allPaths.size()-1){
				indexToNotCheckedPath++;
			}
			// Save next path from list as a parent and list all objects in it.
			parent = allPaths.get(indexToNotCheckedPath);
			list=new File(projectPath+"\\"+parent).list();
		}
		
		// Now we have all paths form project. Let's take only those that hold source files.
		for (int i=0;i<allPaths.size();i++){
			File bufferFile = new File(pathToCProject+allPaths.get(i));
			String[] bufferPaths = bufferFile.list();
			
			// Check if any object in this directory has a source, header or thumb list file.
			for (int k=0; k<bufferPaths.length;k++){
				if(bufferPaths[k].contains(".s")||bufferPaths[k].contains(".c")||bufferPaths[k].contains(".h")){
					
					// if so, add it to crucialPaths list.
					crucialPaths.add(allPaths.get(i));
					
					// We can now stop looking for other files. Stop loop.
					k=bufferPaths.length;
				}
			}
		}
		
		// Now we've got all crucial paths so let's them rewrite to final String[] and find all files to compile.
		// In the same time look for any source file in crucial directories.
		for(int i=0; i<crucialPaths.size();i++){
			
			
			File bufferFile= new File(pathToCProject+crucialPaths.get(i));
			String[] listBuffer = bufferFile.list();
			
			// Look for any source file in this path except *.coproj files and store it in the list.
			for(int k=0;k<listBuffer.length;k++){
				if((listBuffer[k].contains(".c")||listBuffer[k].contains(".s"))&& !listBuffer[k].contains(".coproj")){
					sourceFiles.add(crucialPaths.get(i)+"\\"+listBuffer[k]);
				}
			}
		}
		// Save crucial paths and source files lists in global String arrays.
		includePaths = crucialPaths.toArray(new String[crucialPaths.size()]);
		filesToCompile = sourceFiles.toArray(new String[sourceFiles.size()]);

	}

	private static String findPathToCProject() {
		
		// Get path to java project.
		String actualPath = CompileAndLink.class.getClassLoader()
				.getResource(".").getPath();
		
		// Get path to main repo.
		String stringBufferArray[] = actualPath.split("/sw");
		
		// Add local path to C project to repo path.
		String pathToCProject = stringBufferArray[0] + "\\fw\\legoRCX";
		
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

		// Make all paths global, add include command and store them in one String.
		for (int i = 0; i < includePaths.length; i++) {

			includePaths[i] = pathToCProject + includePaths[i];
			includePaths[i] = "-I" + includePaths[i];
			wholeIncludes += " " + includePaths[i];

		}

		try {
			// Open and write to batch file.
			FileWriter fstream = new FileWriter("compilerCommand.bat");
			BufferedWriter out = new BufferedWriter(fstream);
			
			// Create String array with all files to compile.
			String[] fileName = new String[filesToCompile.length];
			
			// Write compilation commands to batch file (source and output).
			for (int i = 0; i < filesToCompile.length; i++) {
				String buffer = null;
				buffer = compileCommands;
				buffer += wholeIncludes;
				if (filesToCompile[i].endsWith(".c")
						|| filesToCompile[i].endsWith(".s")) {
					buffer += " -c";
					buffer += " " + pathToCProject;
					buffer += filesToCompile[i];
					int index = filesToCompile[i].lastIndexOf("\\");
					fileName[i] = filesToCompile[i].substring(index + 1);
					fileName[i] = fileName[i].replace(".c", ".o");
					fileName[i] = fileName[i].replace(".s", ".o");

					buffer += " -o";
					buffer += " " + pathToCProject + "\\obj\\";
					buffer += fileName[i];
					buffer += "\r\n";
				}
				out.write(buffer);
			}

			// Create linker script with all object files objects.
			String buffer = "";
			buffer += linkerCommand;
			buffer += " -Wl,-Map=" + pathToCProject + "\\obj\\" + mapFile;
			buffer += " -O0 -Wl,--gc-sections ";
			buffer += "-L" + pathToCProject;
			buffer += " -Wl,-T" + pathToCProject + "\\arm-gcc-link.ld -g -o ";
			buffer += pathToCProject + "\\bin\\" + outPutElfFile;
			for (int i = 0; i < fileName.length; i++) {
				buffer += " " + pathToCProject + "\\obj\\" + fileName[i];
			}
			
			// Write to batch file
			out.write(buffer);
			
			// Write closing command to file
			out.write("\r\nexit");
			
			// Close the output stream
			out.close();
			
		}
		
		// Catch exception if any
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	public static void main(String[] args) throws IOException {
		listAllFiles();
		createBatFile();
		compileAndLink();

	}

}
