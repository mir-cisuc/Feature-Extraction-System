package Gazeteers;

import AuxiliarFiles.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



//import edu.stanford.nlp.ling.CoreAnnotations.DictAnnotation;

/**
 * 1. ler a pasta com liricas 2. para cada file separar linha a linha 3. para
 * cada linha, percorrer o file de GazQ#-total.txt (ou outro) e linha a linha
 * deste verificar se a linha da lirica contem as lihas do file GazQ#-total.txt.
 * 4. contar as ocorrências e mandar para ficheiro o relatório
 * 
 * @author rsmal
 * @date 21/04/2015
 */

public class Initial_AV {

	static final String Gazeteer_Q1Q2Q3Q4_dal = "GazQ1Q2Q3Q4-dal.txt";
	static final String Gazeteer_Q2_dal = "GazQ2-dal.txt";
	static final String Gazeteer_Q3_dal = "GazQ3-dal.txt";
	static final String Gazeteer_Q4_dal = "GazQ4-dal.txt";

	static final String sourceFolder = "src/Gazeteers/GazeteersFiles/Origem";
	static final String gazeteerFolder = "src/Gazeteers/GazeteersFiles/";

	static final String gazeteerFile = gazeteerFolder + Gazeteer_Q1Q2Q3Q4_dal;
	static final String outputFile = gazeteerFolder  + "AVin" + Gazeteer_Q1Q2Q3Q4_dal + ".txt";
	static final String str = gazeteerFile + " into " + sourceFolder + " - Details";
			

	public static void main(String[] args) throws ClassNotFoundException, IOException  {
		Initial_AV initial  = new Initial_AV();
	}
	
	public Initial_AV() throws ClassNotFoundException, IOException{	
		// read the names of the files (lyrics) from a folder of lyrics and save
		// them into a String[] (files)
		System.out.println(sourceFolder);
		ReadOperations ro = new ReadOperations();
		String[] files = ro.openDirectory(sourceFolder);
		int numberFiles = ro.filesLenght(files);
		

		// ArrayList<String> listOfLinesFromTextFile = new ArrayList<String>();
		// listOfLinesFromTextFile = ro.openTxtFile(dicFile); //the lines of dal
		// or anew are in an arraylist
		String[][] matrix = new String[numberFiles][3]; // matrix with 4 columns

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
			double averageValenceFileValue = 0;
			double averageArousalFileValue = 0;
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
				double averageValenceLineValue = 0;
				double averageArousalLineValue = 0;
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
					FileReader dict = new FileReader(gazeteerFile);
					BufferedReader in2 = new BufferedReader(dict);
					String dictLine;

					while ((dictLine = in2.readLine()) != null) {
						String[] dataDict = dictLine.split("\t");

						// compare each word of the lyric to each word of the
						// dict
						if (dataLineLyric[k].toLowerCase().equals(dataDict[0])) {
							valenceFileValue = valenceFileValue
									+ Double.parseDouble(dataDict[1]);
							arousalFileValue = arousalFileValue
									+ Double.parseDouble(dataDict[2]);
							valenceLineValue = valenceLineValue
									+ Double.parseDouble(dataDict[1]);
							arousalLineValue = arousalLineValue
									+ Double.parseDouble(dataDict[2]);
							countFileValue++;
							countLineValue++;
							outputDetails = woDetails.writeLinesInList(
									outputDetails, dataDict[0] + " "
											+ dataDict[1] + " " + dataDict[2]);
						}

					}

					in2.close();
				} // end for
				averageValenceLineValue = valenceLineValue / countLineValue;
				averageArousalLineValue = arousalLineValue / countLineValue;

				outputDetails = woDetails.writeLinesInList(outputDetails, "\n");
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"\n Valence Line " + averageValenceLineValue);
				outputDetails = woDetails.writeLinesInList(outputDetails,
						"Arousal Line " + averageArousalLineValue);

			} // end while
			averageValenceFileValue = valenceFileValue / countFileValue;
			averageArousalFileValue = arousalFileValue / countFileValue;

			outputDetails = woDetails.writeLinesInList(outputDetails, "\n\n");
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"Valence File " + averageValenceFileValue);
			outputDetails = woDetails.writeLinesInList(outputDetails,
					"Arousal File " + averageArousalFileValue);

			matrix[i][1] = Double.toString(averageValenceFileValue);
			matrix[i][2] = Double.toString(averageArousalFileValue);

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


