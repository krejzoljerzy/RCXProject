package com.BGHK.RCX.CompileAndLink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class CompileAndLink {

	static String compileCommands = "arm-none-eabi-gcc"
			+ " -mcpu=cortex-m0 -mthumb -Wall -ffunction-sections -g -O0 -c -DSTM32F050C6 -DUSE_STDPERIPH_DRIVER -D__ASSEMBLY__ ";
	
	static String includePaths[] = {
			"\\cmsis_core",
			"\\cmsis_boot",
			"\\stm_lib\\inc",
			"\\",
			"\\stm_lib",
			"\\example"};

	static String[] filesToCompile = {
			"\\stm_lib\\src\\stm32f0xx_rcc.c",
			"\\main.c",
			"\\cmsis_boot\\startup\\startup_stm32f0xx.s",
			"\\stm_lib\\src\\stm32f0xx_gpio.c",
			"\\stm_lib\\src\\stm32f0xx_usart.c",
			"\\cmsis_boot\\system_stm32f0xx.c",
			"\\stm_lib\\src\\stm32f0xx_pwr.c",
			"\\cmsis_core\\core_cm0.c",
			"\\example\\IOToggle.c"
			};

	

	  
	  static String linkerCommand = "arm-none-eabi-gcc -mcpu=cortex-m0 -mthumb -nostartfiles -g";
	  static String mapFile = "legoRCXMap.map";
	  static String outPutElfFile= "legoRCX.elf";

	public static void compileAndLink() {

		try {
			// Execute command
			Process child = Runtime.getRuntime().exec(
					"cmd /c start compilerCommand.bat ");

		} catch (IOException e) {
		}

	}

	public static void createBatFile() throws IOException {

		// Create new bat file.
		File f;
		String wholeIncludes="";
		f = new File("compilerCommand.bat");
		if (!f.exists()) {
			f.delete();
		}
		f.createNewFile();

		String actualPath = CompileAndLink.class.getClassLoader()
				.getResource(".").getPath();
		String stringBufferArray[] = actualPath.split("/sw");

		String pathToCProject = stringBufferArray[0] + "\\fw\\legoRCX";
		pathToCProject = pathToCProject.replace("/", "\\");
		pathToCProject = pathToCProject.substring(1);
		System.out.println(pathToCProject);
		
		for(int i=0;i<includePaths.length;i++){
			
			includePaths[i]=pathToCProject+includePaths[i];
			includePaths[i]="-I"+includePaths[i];
			wholeIncludes+=" "+includePaths[i];
			
		}
		
		try {
			// Write to file
			FileWriter fstream = new FileWriter("compilerCommand.bat");
			BufferedWriter out = new BufferedWriter(fstream);
			String[] fileName= new String [filesToCompile.length];
			// Write compilation commands to batch file.
			for (int i = 0; i < filesToCompile.length; i++) {
				String buffer = null;
				buffer = compileCommands;
				buffer+=wholeIncludes;
				if (filesToCompile[i].endsWith(".c")||filesToCompile[i].endsWith(".s")) {
					buffer += " -c";
					buffer += " " + pathToCProject;
					buffer += filesToCompile[i];
					int index = filesToCompile[i].lastIndexOf("\\");
					fileName[i] = filesToCompile[i].substring(index+1);
					fileName[i] = fileName[i].replace(".c",".o");
					fileName[i] = fileName[i].replace(".s",".o");
					
				 
					buffer += " -o";
					buffer += " " + pathToCProject + "\\obj\\";
					buffer += fileName[i];
					buffer += "\r\n";
				}
				out.write(buffer);
			}
			
			// Link objects.
			String buffer = "";
			buffer+=linkerCommand;
			buffer+=" -Wl,-Map="+pathToCProject+"\\obj\\"+mapFile;
			buffer+=" -O0 -Wl,--gc-sections ";
			buffer+="-L"+pathToCProject;
			buffer+=" -Wl,-T"+pathToCProject+"\\arm-gcc-link.ld -g -o ";
			buffer+= pathToCProject+"\\bin\\"+outPutElfFile;
			for (int i=0;i<fileName.length;i++){
				buffer+=" "+ pathToCProject+"\\obj\\"+fileName[i];
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

		createBatFile();
		compileAndLink();

	}

}
