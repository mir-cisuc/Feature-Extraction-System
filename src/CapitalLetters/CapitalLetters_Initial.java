package CapitalLetters;

import AuxiliarFiles.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * 1. ler a pasta com liricas (origem) 
 * 2.1. faz contagem de palavras começadas por maiuscula, execpto a 1ª palavra de cada linha
 * 2.2. faz contagem de palavras todas em maiusculas
 * 3. guarda o output em ficheiro output (M45-capitalLetters.txt)
 * 
 * @author rsmal
 * @date
 */
public class CapitalLetters_Initial {

	static final String sourceFolder = "src/Origem/"; // pasta onde estao as
													// liricas

	static final String outputFolder = "src/Output/";
	static final String outputFile = outputFolder + "CapitalLetters-M45";
	
	public static void main(String[] args) throws ClassNotFoundException,
	IOException {
		CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial();
	}
	
	public CapitalLetters_Initial() throws ClassNotFoundException, IOException{	

		// read the names of the files from a folder and save them into a
		// String[] (files)
		ReadOperations ro = new ReadOperations();
		String[] files = ro.openDirectory(sourceFolder);
		int numberFiles = ro.filesLenght(files);

		String[][] matrix = new String[numberFiles][3];

		// for each file...
		for (int i = 0; i < numberFiles; i++) {
			String[] data = files[i].split("\\.");
			matrix[i][0] = data[0];

			System.out.println("Ficheiro - "+i);
			
			
			// location of the file
			String filename_path = sourceFolder + "/" + files[i];

			boolean fcl = false;
			boolean acl = false;
			int cfcl = 0;
			int cacl = 0;

			// open the file for reading
			FileReader fileReader = new FileReader(filename_path);
			BufferedReader in = new BufferedReader(fileReader);
			String thisLine;

			//CapitalLetters_Initial ini = new CapitalLetters_Initial();
			while ((thisLine = in.readLine()) != null) {
				// Ignore empty lines.
				if (thisLine.equals(""))
					continue;

				thisLine = thisLine.replace("\'", "");
				// remover punctuation marks
				thisLine = thisLine.replaceAll("[^a-zA-Z0-9-]", " ");
				
				//System.out.println("AAAA --> "+thisLine);

				String[] words = thisLine.split(" ");

				int numberWordsPerLine = words.length;

				for (int j = 0; j < numberWordsPerLine; j++) {

					if (words[j].equals("")) {
						continue;
					}
					fcl = this.firstCapitalLetter(words[j]);
					acl = this.allCapitalLetters(words[j]);

					if (fcl) {
						cfcl++;
					}

					if (acl) {
						cacl++;
					}

					//System.out.println("AAAA --> " + words[j]);
					//System.out.println("BBBB --> " + acl);

				}

			} // end while
			matrix[i][1] = Integer.toString(cfcl);
			matrix[i][2] = Integer.toString(cacl);
			in.close();
		} // end for
		WriteOperations wo = new WriteOperations();
		wo.writeMatrixInConsole(matrix);
		wo.writeMatrixInFile(matrix, outputFile);

	}


	public boolean firstCapitalLetter(String word) {
		if (Character.isLowerCase(word.charAt(0))
				|| (Character.isDigit(word.charAt(0))) || (word.length() == 1)) {
			return false;
		} else {
			for (int i = 1; i < word.length(); i++) {
				if (Character.isUpperCase(word.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean allCapitalLetters(String word) {
		int cons = 0;
		for (int i = 0; i < word.length(); i++) {
			if (Character.isLowerCase(word.charAt(i)) || (word.length() == 1)) {
				return false;
			} else if (!Character.isDigit(word.charAt(i))) {
				cons++;
			}
		}
		if (cons != 0) {
			return true;
		} else
			return false;

	}

}
