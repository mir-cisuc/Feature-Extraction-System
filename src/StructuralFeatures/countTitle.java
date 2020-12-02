package StructuralFeatures;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class countTitle {
	public countTitle(String titulo, String sourceFile) {
		// TODO Auto-generated constructor stub
		Path path = Paths.get(sourceFile);
		String content = null;
		try {
			content = Files.readString(path, StandardCharsets.US_ASCII).toLowerCase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		titulo = titulo.toLowerCase();
				
		
		int occurences= content.split(titulo, -1).length-1;
		System.out.println(occurences);
		writeCSV(titulo,occurences);
	}
	
	public static void writeCSV(String titulo, int value) {
		String outputFolder  = "src/Output/";
		FileWriter fileWriter2 = null;
		try {
			fileWriter2 = new FileWriter(outputFolder+"Titles.csv");
			
			fileWriter2.write("title, count");
			fileWriter2.write("\n");
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

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path path = Paths.get("src/Origem/teste.txt");
		String content = Files.readString(path, StandardCharsets.US_ASCII).toLowerCase();
		
		String titulo = "di da dam".toLowerCase();	
				
		
		int occurences= content.split(titulo, -1).length-1;
		System.out.println(occurences);
		writeCSV(titulo,occurences);
			
	}

	
	
}
