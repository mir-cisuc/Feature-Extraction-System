package WordsDictionary;

import AuxiliarFiles.*;
import CapitalLetters.CapitalLetters_Initial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 1. ler a pasta com liricas 
 * 2. para cada file separar linha a linha 
 * 3. para cada linha, percorrer o file de slangs.txt (ou outro) e linha a linha deste verificar se a 
 * linha da lirica contem as lihas do file slangs 
 * 4. contar as ocorrencias e mandar para os 2 ficheiros de relatorio
 * 
 * @author rsmal
 * @date
 */
public class WordsDictionary_Initial {

	static final String sourceFolder = "src/Origem"; // pasta onde estao as
													// liricas
	static final String dicFile = "src/AuxiliarFiles/slang.txt";
	
	static final String outputFolder = "src/Output/";

	static final String outputFile =  outputFolder + "WordsDictionary_Output";
	
	
	public static void main(String[] args) throws ClassNotFoundException,
	IOException {
		WordsDictionary_Initial WordsDictionary_Initial = new WordsDictionary_Initial();
	}

	public WordsDictionary_Initial() throws ClassNotFoundException, IOException{	

		// read the names of the files from a folder and save them into a
		// String[] (files)
		ReadOperations ro = new ReadOperations();
		String[] files = ro.openDirectory(sourceFolder);
		int numberFiles = ro.filesLenght(files);
		
		System.out.println("numfiles "+numberFiles);

		ArrayList<String> listOfLinesFromTextFile = new ArrayList<String>();
		listOfLinesFromTextFile = ro.openTxtFile(dicFile);
		String[][] matrix = new String[numberFiles][2];

		// ArrayList <String> outputDetails = new ArrayList<String>();
		String outputDetails = "";
		WriteOperations woDetails = new WriteOperations();
		String str = dicFile + " into " + sourceFolder + " - Details";
		outputDetails = woDetails.writeLinesInList(outputDetails, str);
		outputDetails = woDetails.writeLinesInList(outputDetails, "\n");

		// for each file...
		for (int i = 0; i < numberFiles; i++) {
			String[] data = files[i].split("\\.");
			matrix[i][0] = data[0];

			System.out.println("File number "+i);
			
			// location of the file
			String filename_path = sourceFolder + "/" + files[i];

			outputDetails = woDetails.writeLinesInList(outputDetails, "\n");
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"###############################################");
			outputDetails = woDetails.writeLinesInList(outputDetails,
					filename_path);
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"###############################################");

			int count = 0;

			// open the file for reading
			FileReader fileReader = new FileReader(filename_path);
			BufferedReader in = new BufferedReader(fileReader);
			String thisLine;

			while ((thisLine = in.readLine()) != null) {
				// Ignore empty lines.
				if (thisLine.equals(""))
					continue;

				// remover punctuation marks
				thisLine = thisLine.replaceAll("[^a-zA-Z0-9- ]", "");
				
				outputDetails = woDetails.writeLinesInList(outputDetails, "\n");
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"--------------------------------------------");
				outputDetails = woDetails.writeLinesInList(outputDetails,"Linha -> "+thisLine);
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"--------------------------------------------");

				thisLine = " " + thisLine + " ";
				// open the dict for reading
				FileReader dict = new FileReader(dicFile);
				BufferedReader in2 = new BufferedReader(dict);
				String dictLine;

				while ((dictLine = in2.readLine()) != null) {
					// if (dictLine.equals(""))
					// continue;

					dictLine = " " + dictLine + " ";
					int index = thisLine.indexOf(dictLine);

					while (index >= 0) {
						index++;
						count++;
						String str2 = dictLine;
						outputDetails = woDetails.writeLinesInList(
								outputDetails, str2);
						index = thisLine.indexOf(dictLine, index);

					}

				}
				in2.close();

			} // end while
			matrix[i][1] = Integer.toString(count);
			in.close();
		} // end for
		//ConvertToTFIDF ct = new ConvertToTFIDF();
		//ct.convertTfidf(matrix);
		
		WriteOperations wo = new WriteOperations();
		wo.writeMatrixInConsole(matrix);
		wo.writeMatrixInFile(matrix, outputFile);
		wo.writeFile(outputFolder + "WordsDictionary_outputDetails.txt", outputDetails);
	}

}
