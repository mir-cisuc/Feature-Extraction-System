package StructuralFeatures;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class countTitle {

	public countTitle() {
		// TODO Auto-generated constructor stub
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
			fileWriter2.write(value);
			
			
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
		Path path = Paths.get("src/Origem/bp.txt");
		String content = Files.readString(path, StandardCharsets.US_ASCII).toLowerCase();
		//String content = "we are the lovesick girls";
		String titulo = "wish you were here".toLowerCase();	
				
		
		int occurences=0;
		occurences=content.split(titulo, -1).length-1;
		System.out.println(content.split(titulo, -1).length-1);
		writeCSV(titulo,occurences);
		
		
	}

	
	
}
