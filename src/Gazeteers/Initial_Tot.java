package Gazeteers;

import AuxiliarFiles.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 1. ler a pasta com liricas 2. para cada file separar linha a linha 3. para
 * cada linha, percorrer o file de GazQ#-total.txt (ou outro) e linha a linha
 * deste verificar se a linha da lirica contem as lihas do file GazQ#-total.txt.
 * 4. contar as ocorrências e mandar para ficheiro o relatório
 * 
 * Com esta implem tenho que executar 4x uma por Quadrante
 * 
 * @author rsmal
 * @date
 */

public class Initial_Tot {

	static final String Gazeteer_Q1 = "GazQ1-total.txt";
	static final String Gazeteer_Q2 = "GazQ2-total.txt";
	static final String Gazeteer_Q3 = "GazQ3-total.txt";
	static final String Gazeteer_Q4 = "GazQ4-total.txt";

	static final String sourceFolder = "origem";

	static final String gazeteerFile = Gazeteer_Q4;
	static final String outputFile = "#in" + gazeteerFile + ".txt";
	static final String str = gazeteerFile + " into " + sourceFolder
			+ " - Details";

	public static void main(String[] args) throws ClassNotFoundException,
			IOException {

		// read the names of the files (lyrics) from a folder of lyrics and save
		// them into a
		// String[] (files)
		ReadOperations ro = new ReadOperations();
		String[] files = ro.openDirectory(sourceFolder);
		int numberFiles = ro.filesLenght(files);

		// ArrayList<String> listOfLinesFromTextFile = new ArrayList<String>();
		// listOfLinesFromTextFile = ro.openTxtFile(dicFile); //the lines of dal
		// or anew are in an arraylist
		String[][] matrix = new String[numberFiles][2]; // matrix with 2 columns

		// ArrayList <String> outputDetails = new ArrayList<String>();
		// write details in a specific file
		String outputDetails = "";
		WriteOperations woDetails = new WriteOperations();
		outputDetails = woDetails.writeLinesInList(outputDetails, str);
		outputDetails = woDetails.writeLinesInList(outputDetails, "\n");

		// for each file (lyric)...
		for (int i = 0; i < numberFiles; i++) {

			int contador_file = 0;

			System.out.println("File Number: "+i);
			
			String[] data = files[i].split("\\.");
			matrix[i][0] = data[0];

			// location of the file
			String filename_path = sourceFolder + "/" + files[i];

			// open the file (lyric) for reading
			FileReader fileReader = new FileReader(filename_path);
			BufferedReader in = new BufferedReader(fileReader);
			String thisLine;

			// for each line of the lyric...
			while ((thisLine = in.readLine()) != null) {

				// Ignore empty lines.
				if (thisLine.equals(""))
					continue;

				// remove punctuation marks
				thisLine = thisLine.replaceAll("[^a-zA-Z0-9- ]", "");

				// thisLine = " " + thisLine + " ";
				String[] dataLineLyric = thisLine.split(" ");

				// for each word of the line of the lyric
				for (int k = 0; k < dataLineLyric.length; k++) {

					// open the dict for reading
					FileReader dict = new FileReader(gazeteerFile);
					BufferedReader in2 = new BufferedReader(dict);
					String dictLine;

					while ((dictLine = in2.readLine()) != null) {
						String[] dataDict = dictLine.split("\t");

						// compare each word of the lyric to each word of the
						// dict
						if (dataLineLyric[k].toLowerCase().equals(dataDict[0])) {
							contador_file++;

						}

					}

					in2.close();
				} // end for

			} // end while

			matrix[i][1] = Integer.toString(contador_file);
			in.close();

		} // end for
			// ConvertToTFIDF ct = new ConvertToTFIDF();
			// ct.convertTfidf(matrix);

		WriteOperations wo = new WriteOperations();
		wo.writeMatrixInConsole(matrix);
		wo.writeMatrixInFile(matrix, outputFile);
		wo.writeFile("outputDetails.txt", outputDetails);
	}

}
