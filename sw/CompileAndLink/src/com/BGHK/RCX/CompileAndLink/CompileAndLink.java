package com.BGHK.RCX.CompileAndLink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
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

		String pathToCProject = findPathToCProject();
		List<String> allPaths = new ArrayList<>();
		List<String> crucialPaths = new ArrayList<>();
		List<String> sourceFiles = new ArrayList<>();	
		int indexToNotCheckedPath = 1;
		//
		File projectPath = new File(pathToCProject);
		String[] list = projectPath.list();
		String parent = "";
		allPaths.add("\\");
		for (int k = 0; k < allPaths.size(); k++) {
			for (int i = 0; i < list.length; i++) {
				// If listed item is a directory.
				if (!list[i].contains(".")) {
					allPaths.add(parent + "\\" + list[i]);
				}
			}
			if(indexToNotCheckedPath<allPaths.size()-1){
				indexToNotCheckedPath++;
			}
			parent = allPaths.get(indexToNotCheckedPath);
			list=new File(projectPath+"\\"+parent).list();
		}
		
		// Now we have all paths form project. Let's take only those that hold source files.
		for (int i=0;i<allPaths.size();i++){
			File bufferFile = new File(pathToCProject+allPaths.get(i));
			String[] bufferPaths = bufferFile.list();
			for (int k=0; k<bufferPaths.length;k++){
				if(bufferPaths[k].contains(".s")||bufferPaths[k].contains(".c")||bufferPaths[k].contains(".h")){
					crucialPaths.add(allPaths.get(i));
					k=bufferPaths.length;
				}
			}
		}
		
		// Now we've got all crucial paths so let's them rewrite to final String[] and find all files to compile.
		for(int i=0; i<crucialPaths.size();i++){
			
			
			File bufferFile= new File(pathToCProject+crucialPaths.get(i));
			String[] listBuffer = bufferFile.list();
			for(int k=0;k<listBuffer.length;k++){
				if((listBuffer[k].contains(".c")||listBuffer[k].contains(".s"))&& !listBuffer[k].contains(".coproj")){
					sourceFiles.add(crucialPaths.get(i)+"\\"+listBuffer[k]);
				}
			}
		}
		includePaths = crucialPaths.toArray(new String[crucialPaths.size()]);
		filesToCompile = sourceFiles.toArray(new String[sourceFiles.size()]);

	}

	private static String findPathToCProject() {
		String actualPath = CompileAndLink.class.getClassLoader()
				.getResource(".").getPath();
		String stringBufferArray[] = actualPath.split("/sw");

		String pathToCProject = stringBufferArray[0] + "\\fw\\legoRCX";
		pathToCProject = pathToCProject.replace("/", "\\");
		pathToCProject = pathToCProject.substring(1);
		return pathToCProject;
	}

	public static void createBatFile() throws IOException {

		// Create new bat file.
		File f;
		String wholeIncludes = "";
		f = new File("compilerCommand.bat");
		if (!f.exists()) {
			f.delete();
		}
		f.createNewFile();

		String pathToCProject = findPathToCProject();
		System.out.println(pathToCProject);

		for (int i = 0; i < includePaths.length; i++) {

			includePaths[i] = pathToCProject + includePaths[i];
			includePaths[i] = "-I" + includePaths[i];
			wholeIncludes += " " + includePaths[i];

		}

		try {
			// Write to file
			FileWriter fstream = new FileWriter("compilerCommand.bat");
			BufferedWriter out = new BufferedWriter(fstream);
			String[] fileName = new String[filesToCompile.length];
			// Write compilation commands to batch file.
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

			// Link objects.
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
			out.write(buffer);

			out.write("\r\nexit");
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public static void main(String[] args) throws IOException {
		listAllFiles();
		createBatFile();
		compileAndLink();

	}

}
