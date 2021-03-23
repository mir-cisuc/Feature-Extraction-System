package StructuralFeatures;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class countTitle {
	public countTitle(String titulo, String inputFile, String outputFile) {
		// TODO Auto-generated constructor stub
		Path path = Paths.get(inputFile);
		String content = null;
		System.out.println(path);
		try {
			content = Files.readString(path, StandardCharsets.UTF_8).toLowerCase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		titulo = titulo.toLowerCase();
						
		int occurences= content.split(titulo, -1).length-1;
		System.out.printf("%s,%s,%s\n",occurences,titulo,inputFile);
		writeCSV(titulo,occurences, outputFile, inputFile);
	}
	
	
	public static void writeCSV(String titulo, int value, String outputFile, String inputFile) {
		String[] data = inputFile.split("\\.");
		if (data[0].contains("/")) {
			String [] nome = data[0].split("/");
			data[0] = nome[nome.length-1];
		}
		
		String outputFolder  = "src/Output/";
		String output = new String();
		if(outputFile != null && !outputFile.isEmpty()) {
			output = outputFile;	
		}
		else {
			output = outputFolder+"Titles.csv";
		}
		FileWriter fileWriter2 = null;
		try {
			fileWriter2 = new FileWriter(output);
			
			fileWriter2.write("filenane,title, count");
			fileWriter2.write("\n");
			fileWriter2.write(data[0]);
			fileWriter2.write(titulo);
			fileWriter2.write(", ");
			fileWriter2.write(String.valueOf(value));			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (fileWriter2 != null) {
					fileWriter2.flush();
					fileWriter2.close();					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}	
	
}
