package com.BGHK.RCX.CompileAndLink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MakeAllLPCXpresso {

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
					"cmd /b start compilerCommand.bat ");

		} catch (IOException e) {
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
			
			// Create String array with all files to compile.
			String[] fileName = new String[filesToCompile.length];
			
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

	public static void main(String[] args) throws IOException {
		createBatFile();
		compileAndLink();

	}

}
