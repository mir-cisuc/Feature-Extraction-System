package GI;

import AuxiliarFiles.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



/**
 * 1.copiar o file "gi_dataset_prog_CSV.csv" para uma matriz (gi_matrix) 11788 *
 * 183; 
 * 2.criar uma matriz das features (feature_matrix) com 181 * 183 
 * 3.ler liricas 1 a 1 e para cada ler frase a frase a frase; 
 * 4.verificar se cada palavra das frases está no GI (matriz anterior - 1ª coluna); 
 * 5.para cada palavra copiar as colunas preenchidas de gi_matrix para feature_matrix
 * (incrementando o num de ocorrencias dessa feature; 
 * 6.escrever a matriz (feature_matrix) em ficheiro;
 * 
 * o gi tem varias palavras com diferentes sentidos (as que tem #);
 * 
 * @author rsmal
 * @date 29/05/2015 v1
 */

public class Initial_GI {

	// pasta das liricas
	static String sourceFolder = "src/Origem";
	
	// pasta do gi
	static final String giFolder = "src/GI/GIFiles/";
	// file gi
	static String gi = giFolder + "gi-11788.csv";
	
	//output folder
	static final String outputFolder  = "src/Output/";

	// file output das features
	static String outputFile = outputFolder + "GI_Features-1180";
	
	public static void main(String[] args) throws ClassNotFoundException, IOException  {
		Initial_GI initial_anew  = new Initial_GI(null);
	}
	
	public Initial_GI(String inputFile, String outputFile) throws ClassNotFoundException, IOException {
		gi = inputFile;
		this.outputFile = outputFile;
		mainCode();
	}
	
	
	public Initial_GI(String sourceFolder1) throws ClassNotFoundException, IOException {	
		if(sourceFolder1 != null && !sourceFolder1.isEmpty()) {
			sourceFolder = sourceFolder1;				
		}
		else {
			sourceFolder = "src/Origem/";
		}
		mainCode();
	}

	public void mainCode() throws ClassNotFoundException, IOException{
		/**
		 * 1)copiar o file "gi_dataset_prog_CSV.csv" para uma matriz (gi_matrix)
		 * 11788 * 183;
		 */
		String[][] gi_matrix = new String[11788][183];
		
		// open the gi_file for reading
		FileReader file = new FileReader(gi);
		
		BufferedReader in = new BufferedReader(file);
		String line;
		int lin = 0;
		int tt = -1;

		while ((line = in.readLine()) != null) {
			tt++;
			String[] cellsLine = line.split("\\,");
			for (int i = 0; i < gi_matrix[0].length; i++) {
				gi_matrix[lin][i] = cellsLine[i].toLowerCase();
			}
			lin++;
		}
		in.close();
		
		/**
		 * 2)criar uma matriz das features (feature_matrix) com 181 * 183
		 */

		/**
		 * 3)ler liricas 1 a 1 e para cada ler frase a frase; 4)verificar se
		 * cada palavra das frases está no GI (matriz anterior - 1ª coluna);
		 * 5)para cada palavra copiar as colunas preenchidas de gi_matrix para
		 * feature_matrix (incrementando o num de ocorrencias dessa feature;
		 */

		// read the names of the files (lyrics) from a folder of lyrics and save
		// them into a String[] (files)
		ReadOperations ro = new ReadOperations();
		String[] files = ro.openDirectory(sourceFolder);
		int numberFiles = ro.filesLenght(files);

		/**
		 * 2)
		 */
		String[][] feature_matrix = new String[numberFiles + 1][183];
		int length_feature_matrix = feature_matrix[0].length; // 183
		for (int i=0; i < gi_matrix[0].length; i++) {
			feature_matrix[0][i] = gi_matrix[0][i];
		}
		
		// for each file (lyric)...
		for (int i = 0; i < numberFiles; i++) {
			String[] cont_vector = new String[183];
			for (int j = 0; j < cont_vector.length; j++) {
				cont_vector[j] = "0";
			}
			
			System.out.println("File num : "+i);
			// guarda na feature_matrix o nome da lirica na 1ª coluna
			String[] data = files[i].split("\\.");
			feature_matrix[i+1][0] = data[0];

			// location of the file
			String filename_path = sourceFolder + "/" + files[i];

			// open the file (lyric) for reading
			FileReader fileReader = new FileReader(filename_path);
			BufferedReader in2 = new BufferedReader(fileReader);
			String thisLine; // linha corrente da lirica atual

			// for each line of the lyric...
			while ((thisLine = in2.readLine()) != null) {

				// Ignore empty lines.
				if (thisLine.equals(""))
					continue;

				// remove punctuation marks
				thisLine = thisLine.replaceAll("[^a-zA-Z0-9- ]", "");

				// string[] com cada palavra da linha da lyric
				String[] dataLineLyric = thisLine.split(" ");

				// for each word of the line of the lyric
				for (int k = 0; k < dataLineLyric.length; k++) {
					//verificar se a palavra da lirica existe no gi
					for (int l = 1; l < gi_matrix.length; l++) {
						String[] word = gi_matrix[l][0].split("\\#");
						if (dataLineLyric[k].toLowerCase().equals(
								word[0])) {
							// a palavra dataLineLyric[k] existe no gi na linha
							// l
							// percorrer a linha do gi_matrix que contem a
							// palavra e copiar e incrementar as features com
							// calores diferentes de zero para a feature_matrix
							for (int z = 1; z < gi_matrix[l].length; z++) {
								if (!gi_matrix[l][z].equals("0")) {
									int cont = Integer
											.parseInt(cont_vector[z]);
									cont++;
									cont_vector[z] = String.valueOf(cont);
								}
							}
						} // end if
					} // end for
				} // end for
			} // end while
			in2.close();
			for (int r = 1; r < cont_vector.length; r++) {
				feature_matrix[i + 1][r] = cont_vector[r];
			}
		} // end for

		/**
		 * 6) escrever a matriz (feature_matrix) em ficheiro;
		 */

		WriteOperations wo = new WriteOperations();
		wo.writeMatrixInConsole(feature_matrix);
		wo.writeMatrixInFile(feature_matrix, outputFile,1); //enviar option para imprimir o header do GI = 1

	}

}
