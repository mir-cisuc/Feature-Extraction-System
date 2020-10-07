package DAL_ANEW;

import AuxiliarFiles.*;
import Gazeteers.Initial_Gazeteers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//import edu.stanford.nlp.ling.CoreAnnotations.DictAnnotation;

/**
 DAL
 comment w=word, ee=pleasantness, aa=activation, ii=imagery
 comment scores for pleasantness range from 1 (unpleasant) to 3 (pleasant)
 scores for activation range from 1 (passive) to 3 (active)
 scores for imagery range from 1 (difficult to form a meantal
 picture of this word) to 3 (easy to form a mental picture)
 comment pleasantness mean=1.84, sd=.44
 activation mean=1.85, sd=.39
 imagery (does word give you a clear mental picture) mean=1.94, sd=.63
 comment these values have been tested on 348,000 words of natural language
 the dictionary has a 90% matching rate for this corpus
 mean ee is 1.85, with an sd of .36
 mean aa is 1.67, with an sd of .36
 mean ii is 1.52, with an sd of .63
 */

/**
 * 1. ler a pasta com liricas <pasta origem>
 *  2. para cada file da pasta origem separar linha a linha 
 *  3. para cada linha, percorrer o file de anew-rsmal.txt ou dal-rsmal.txt (ou outro que insira) e linha a linha destem verificar se 
 *  a linha da lirica contem as linhas do file selecionado (ex: anew-rsmal.txt) 
 *  4. contar as ocorrencias e mandar para ficheiro o relatorio. 
 *  Esse ficheiro será anew-rsmal.txtInorigem.txt ou dal-rsmal.txtInorigem.txt conforme o caso.
 * O sistema cria ainda um relatório detalhado que armazena no ficheiro outputDetails.txt
 * 
 * @author rsmal
 * @date
 */

public class Initial_ANEW {

	static final String dicFile1 = "dal-rsmal.txt";
	static final String dicFile2 = "anew-rsmal.txt";
	static final String sourceFolder = "src/Origem";
	static final String dalAnewFolder = "src/DAL_ANEW/DAL_ANEWFiles/";
	
	static final String outputFolder = "src/Output/";
	
	static final String dicFile = dalAnewFolder + dicFile1;
	static final String outputFile = outputFolder + "DAL_ANEW_Indal-rsmal";
	static final String str = dicFile + " into " + sourceFolder + " - Details";

	public static void main(String[] args) throws ClassNotFoundException, IOException  {
		Initial_ANEW initial_anew  = new Initial_ANEW();
	}
	public Initial_ANEW() throws ClassNotFoundException, IOException{	

		// read the names of the files (lyrics) from a folder of lyrics and save
		// them into a
		// String[] (files)
		ReadOperations ro = new ReadOperations();
		String[] files = ro.openDirectory(sourceFolder);
		int numberFiles = ro.filesLenght(files);

		// ArrayList<String> listOfLinesFromTextFile = new ArrayList<String>();
		// listOfLinesFromTextFile = ro.openTxtFile(dicFile); //the lines of dal
		// or anew are in an arraylist
		String[][] matrix = new String[numberFiles][4]; // matrix with 4 columns

		// ArrayList <String> outputDetails = new ArrayList<String>();
		// write details in a specific file
		String outputDetails = "";
		WriteOperations woDetails = new WriteOperations();
		outputDetails = woDetails.writeLinesInList(outputDetails, str);
		outputDetails = woDetails.writeLinesInList(outputDetails, "\n");

		// for each file (lyric)...
		for (int i = 0; i < numberFiles; i++) {

			double valenceFileValue = 0;
			double arousalFileValue = 0;
			double dominanceFileValue = 0;
			double averageValenceFileValue = 0;
			double averageArousalFileValue = 0;
			double averageDominanceFileValue = 0;
			int countFileValue = 0;

			System.out.println("File Number: "+i);
			
			
			String[] data = files[i].split("\\.");
			matrix[i][0] = data[0];

			// location of the file
			String filename_path = sourceFolder + "/" + files[i];

			outputDetails = woDetails.writeLinesInList(outputDetails, "\n");
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"###############################################");
			outputDetails = woDetails.writeLinesInList(outputDetails,
					filename_path);
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"###############################################");

			// open the file (lyric) for reading
			FileReader fileReader = new FileReader(filename_path);
			BufferedReader in = new BufferedReader(fileReader);
			String thisLine;

			// for each line of the lyric...
			while ((thisLine = in.readLine()) != null) {

				double valenceLineValue = 0;
				double arousalLineValue = 0;
				double dominanceLineValue = 0;
				double averageValenceLineValue = 0;
				double averageArousalLineValue = 0;
				double averageDominanceLineValue = 0;
				int countLineValue = 0;

				// Ignore empty lines.
				if (thisLine.equals(""))
					continue;

				// remove punctuation marks
				thisLine = thisLine.replaceAll("[^a-zA-Z0-9- ]", "");

				outputDetails = woDetails.writeLinesInList(outputDetails, "\n");
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"--------------------------------------------");
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"Linha -> " + thisLine);
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"--------------------------------------------");

				// thisLine = " " + thisLine + " ";
				String[] dataLineLyric = thisLine.split(" ");

				// for each word of the line of the lyric
				for (int k = 0; k < dataLineLyric.length; k++) {

					// open the dict for reading
					FileReader dict = new FileReader(dicFile);
					BufferedReader in2 = new BufferedReader(dict);
					String dictLine;

					while ((dictLine = in2.readLine()) != null) {
						String[] dataDict = dictLine.split("\t");

						// compare each word of the lyric to each word of the
						// dict
						if (dataLineLyric[k].equals(dataDict[0])) {
							valenceFileValue = valenceFileValue
									+ Double.parseDouble(dataDict[1]);
							arousalFileValue = arousalFileValue
									+ Double.parseDouble(dataDict[2]);
							dominanceFileValue = dominanceFileValue
									+ Double.parseDouble(dataDict[3]);
							valenceLineValue = valenceLineValue
									+ Double.parseDouble(dataDict[1]);
							arousalLineValue = arousalLineValue
									+ Double.parseDouble(dataDict[2]);
							dominanceLineValue = dominanceLineValue
									+ Double.parseDouble(dataDict[3]);
							countFileValue++;
							countLineValue++;
							outputDetails = woDetails.writeLinesInList(
									outputDetails, dataDict[0] + " "
											+ dataDict[1] + " " + dataDict[2]
											+ " " + dataDict[3]);
						}

					}

					in2.close();
				} // end for
				averageValenceLineValue = valenceLineValue / countLineValue;
				averageArousalLineValue = arousalLineValue / countLineValue;
				averageDominanceLineValue = dominanceLineValue / countLineValue;
				
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"\n");
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"\n Valence Line " + averageValenceLineValue);
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"Arousal Line " + averageArousalLineValue);
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"Dominance Line " + averageDominanceLineValue);

			} // end while
			averageValenceFileValue = valenceFileValue / countFileValue;
			averageArousalFileValue = arousalFileValue / countFileValue;
			averageDominanceFileValue = dominanceFileValue / countFileValue;
			
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"\n\n");
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"Valence File " + averageValenceFileValue);
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"Arousal File " + averageArousalFileValue);
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"Dominance File " + averageDominanceFileValue);
			
			matrix[i][1] = Double.toString(averageValenceFileValue);
			matrix[i][2] = Double.toString(averageArousalFileValue);
			matrix[i][3] = Double.toString(averageDominanceFileValue);
			in.close();

		} // end for
			// ConvertToTFIDF ct = new ConvertToTFIDF();
			// ct.convertTfidf(matrix);

		WriteOperations wo = new WriteOperations();
		wo.writeMatrixInConsole(matrix);
		wo.writeMatrixInFile(matrix, outputFile);
		wo.writeFile(outputFolder + "DAL_ANEW_outputDetails.txt", outputDetails);
	}

}
