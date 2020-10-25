package AuxiliarFiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import cc.mallet.types.InstanceList;

public class CpSaveInstancesInFile {

	public static final String FREQ = "freq";
	public static final String BOOL = "bool";
	public static final String TFIDF = "tfidf";
	public static final String NORM = "norm";
	/*
	 * getSizeAlphabet() get lenght do directorio origem buildMatrix com dim dos
	 * 2 valores anteriores guardar num file o Alphabet fillMatrix - percorrer
	 * as instancias 1 a 1
	 */
	InstanceList instances;
	String[][] matrix_freq;
	String[][] matrix_bool;
	String[][] matrix_norm;
	String[][] matrix_tfidf;
	int col, lin;

	public CpSaveInstancesInFile(InstanceList instances) {

		this.instances = instances;
		col = getSizeAlphabet() + 1;
		lin = getNumberOfInstances();
		matrix_freq = new String[lin+1][col];
		matrix_bool = new String[lin+1][col];
		matrix_norm = new String[lin+1][col];
		matrix_tfidf = new String[lin+1][col];
	}

	public void execute_freq(String[] args) throws IOException {

		int i = 0;
		this.initializeMatrix(matrix_freq);
		while (i < lin) {
			String name = getNameOfInstance(instances.get(i).getName()
					.toString());
			// System.out.println(name);
			ArrayList<String> listOfWords = new ArrayList<String>();
			listOfWords = buildListOfWords(i);

			// talvez aqui

			// System.out.println(instances.get(0).getData());

			this.writeMatrix(listOfWords, name, i, matrix_freq);

			i++;

		}
		System.out.println(instances.getAlphabet());

		// aqui ter um if para mandar escrever no ecra uma unica matriz
		// this.writeMatrixInConsole(matrix_bool);

		/*
		 * Guardar em ficheiro as features da freq e a matriz com as freq de
		 * todas as liricas
		 */
		this.writeMatrixInConsole(matrix_freq);
		String nameFile1 = args[1] + "_" + args[2] + "_" + FREQ;
		File file1 = new File(nameFile1 + "F.txt");
		this.writeNamesOfFeaturesNames(file1);
		File file2 = new File(nameFile1 + ".txt");
		this.writeMatrixInFile(matrix_freq, file2);

		// System.out.println(instances.get(0).getName());
		// System.out.println(instances.get(0).getData());
		// String test[] = instances.get(0).getData().toString().split("\n");
		// System.out.println(test[1]);
		// System.out.println(test[20]);

	}

	public void execute_bool(String[] args) throws IOException {

		int i = 0;
		this.initializeMatrix(matrix_bool);
		while (i < lin) {
			String name = getNameOfInstance(instances.get(i).getName()
					.toString());
			ArrayList<String> listOfWords = new ArrayList<String>();
			listOfWords = buildListOfWords(i);

			// talvez aqui

			this.writeMatrix(listOfWords, name, i, matrix_bool);

			i++;

		}
		System.out.println(instances.getAlphabet());

		// aqui ter um if para mandar escrever no ecra uma unica matriz
		// this.writeMatrixInConsole(matrix_bool);

		/*
		 * Guardar em ficheiro as features da freq e a matriz com as freq de
		 * todas as liricas
		 */
		this.calculateBoolValues();
		this.writeMatrixInConsole(matrix_bool);
		String nameFile2 = args[1] + "_" + args[2] + "_" + BOOL;
		File file3 = new File(nameFile2 + "F.txt");
		this.writeNamesOfFeaturesNames(file3);
		File file4 = new File(nameFile2 + ".txt");
		this.writeMatrixInFile(matrix_bool, file4);

		// System.out.println(instances.get(0).getName());
		// System.out.println(instances.get(0).getData());
		// String test[] = instances.get(0).getData().toString().split("\n");
		// System.out.println(test[1]);
		// System.out.println(test[20]);

	}

	public void execute_norm(String[] args) throws IOException {
		int i = 0;
		this.initializeMatrix(matrix_norm);
		while (i < lin) {
			String name = getNameOfInstance(instances.get(i).getName()
					.toString());
			ArrayList<String> listOfWords = new ArrayList<String>();
			listOfWords = buildListOfWords(i);

			this.writeMatrix(listOfWords, name, i, matrix_norm);

			i++;

		}
		System.out.println(instances.getAlphabet());

		// aqui ter um if para mandar escrever no ecra uma unica matriz
		// this.writeMatrixInConsole(matrix_bool);

		/*
		 * Guardar em ficheiro as features da freq e a matriz com as freq de
		 * todas as liricas
		 */
		this.calculatenormValues();
		this.writeMatrixInConsole(matrix_norm);
		String nameFile2 = args[1] + "_" + args[2] + "_" + NORM;
		File file3 = new File(nameFile2 + "F.txt");
		this.writeNamesOfFeaturesNames(file3);
		File file4 = new File(nameFile2 + ".txt");
		this.writeMatrixInFile(matrix_norm, file4);

	}

	private void calculatenormValues() {

		double[][] featmax = new double[1][this.getSizeAlphabet()];
		//double[][] featmin = new double[1][this.getSizeAlphabet()];

		for (int j = 1; j < this.getSizeAlphabet() + 1; j++) {
			double max = 0;
			double min = 0;
			for (int i = 0; i < this.getNumberOfInstances(); i++) {
				if (Double.parseDouble(matrix_norm[i][j]) > max) {
					max = Double.parseDouble(matrix_norm[i][j]);
				}
				//if (Double.parseDouble(matrix_norm[i][j]) < min) {
					//min = Double.parseDouble(matrix_norm[i][j]);
				//}
			}
			featmax[0][j - 1] = max;
			//featmin[0][j - 1] = min;
		}

		//double[][] matrix_aux = new double[lin][col];
		for (int j = 1; j < this.getSizeAlphabet() + 1; j++) {
			for (int i = 0; i < this.getNumberOfInstances(); i++) {
//				matrix_aux[i][j] = (Double.parseDouble(matrix_norm[i][j]) - featmin[0][j - 1])
//						/ (featmax[0][j - 1] - featmin[0][j - 1]);
				matrix_norm[i][j] = Double.toString((Double.parseDouble(matrix_norm[i][j]) / (featmax[0][j - 1])));
			}
		}

		// copiar uma matriz para outra
//		DecimalFormat aux2 = new DecimalFormat("0.##");
//		for (int i = 0; i < lin; i++) {
//			for (int j = 1; j < col; j++) {
//				String aux3 = aux2.format(matrix_aux[i][j]);
//				matrix_norm[i][j] = aux3;
//			}
//
//		}

	}

	
//	private void calculatenormValues() {
//
//		double[][] featmax = new double[1][this.getSizeAlphabet()];
//		double[][] featmin = new double[1][this.getSizeAlphabet()];
//
//		for (int j = 1; j < this.getSizeAlphabet() + 1; j++) {
//			double max = 0;
//			double min = 0;
//			for (int i = 0; i < this.getNumberOfInstances(); i++) {
//				if (Double.parseDouble(matrix_norm[i][j]) > max) {
//					max = Double.parseDouble(matrix_norm[i][j]);
//				}
//				if (Double.parseDouble(matrix_norm[i][j]) < min) {
//					min = Double.parseDouble(matrix_norm[i][j]);
//				}
//			}
//			featmax[0][j - 1] = max;
//			featmin[0][j - 1] = min;
//		}
//
//		double[][] matrix_aux = new double[lin][col];
//		for (int j = 1; j < this.getSizeAlphabet() + 1; j++) {
//			for (int i = 0; i < this.getNumberOfInstances(); i++) {
//				matrix_aux[i][j] = (Double.parseDouble(matrix_norm[i][j]) - featmin[0][j - 1])
//						/ (featmax[0][j - 1] - featmin[0][j - 1]);
//			}
//		}
//
//		// copiar uma matriz para outra
//		DecimalFormat aux2 = new DecimalFormat("0.##");
//		for (int i = 0; i < lin; i++) {
//			for (int j = 1; j < col; j++) {
//				String aux3 = aux2.format(matrix_aux[i][j]);
//				matrix_norm[i][j] = aux3;
//			}
//
//		}
//
//	}
	
	
	public void execute_tfidf(String[] args) throws IOException {

		int i = 0;
		this.initializeMatrix(matrix_tfidf);
		while (i < lin) {
			String name = getNameOfInstance(instances.get(i).getName()
					.toString());
			ArrayList<String> listOfWords = new ArrayList<String>();
			listOfWords = buildListOfWords(i);

			this.writeMatrix(listOfWords, name, i, matrix_tfidf);

			i++;

		}
		System.out.println(instances.getAlphabet());

		// aqui ter um if para mandar escrever no ecra uma unica matriz
		// this.writeMatrixInConsole(matrix_bool);

		/*
		 * Guardar em ficheiro as features da freq e a matriz com as freq de
		 * todas as liricas
		 */
		this.calculatetfidfValues();
		this.writeMatrixInConsole(matrix_tfidf);
		String nameFile2 = args[1] + "_" + args[2] + "_" + TFIDF;
		File file3 = new File(nameFile2 + "F.txt");
		this.writeNamesOfFeaturesNames(file3);
		File file4 = new File(nameFile2 + ".txt");
		this.writeMatrixInFile(matrix_tfidf, file4);

	}

	private void calculatetfidfValues() {

		// calcular o maxtfij
		double[][] maxfij = new double[this.getNumberOfInstances()][1];
		for (int i = 0; i < this.getNumberOfInstances(); i++) {
			double max = 0;
			for (int j = 1; j < this.getSizeAlphabet(); j++) {
				if (Double.parseDouble(matrix_tfidf[i][j]) > max) {
					max = Double.parseDouble(matrix_tfidf[i][j]);
				}
			}
			// ponto 2)
			maxfij[i][0] = max;

		}

		// idfi = log2(N/ni)
		int N = this.getNumberOfInstances();
		double[][] ni = new double[1][this.getSizeAlphabet()];

		for (int j = 1; j < this.getSizeAlphabet() + 1; j++) {
			double max = 0;
			for (int i = 0; i < this.getNumberOfInstances(); i++) {
				if (Double.parseDouble(matrix_tfidf[i][j]) > max) {
					max = Double.parseDouble(matrix_tfidf[i][j]);
				}
			}
			// ponto 4)
			ni[0][j - 1] = max;
		}

		double[][] idfi = new double[1][this.getSizeAlphabet()];
		for (int i = 0; i < idfi[0].length; i++) {
			double aux = N / ni[0][i];
			// ponto 5
			idfi[0][i] = this.log(aux, 2);
		}

		System.gc();
		// calcular tfij = fij / maxtfij
		for (int i = 0; i < this.getNumberOfInstances(); i++) {
			for (int j = 1; j < this.getSizeAlphabet() + 1; j++) {

				double aux = (Double.parseDouble(matrix_tfidf[i][j]))
						/ (maxfij[i][0]);
				// ponto 3
				matrix_tfidf[i][j] = Double.toString(aux);

			}

		}

		// wij = tfi * idfi
		DecimalFormat aux3 = new DecimalFormat("0.##");
		for (int i = 0; i < this.getNumberOfInstances(); i++) {
			for (int j = 1; j < this.getSizeAlphabet()+1; j++) {
				double aux2 = (Double.parseDouble(matrix_tfidf[i][j]))
						* (idfi[0][j - 1]);
				String aux4 = aux3.format(aux2);

				matrix_tfidf[i][j] = aux4;

			}

		}

	}

	// private void calculatetfidfValues() {
	//
	// // calcular o maxtfij
	// double[][] maxfij = new double[this.getNumberOfInstances()][1];
	// for (int i = 0; i < this.getNumberOfInstances(); i++) {
	// double max = 0;
	// for (int j = 1; j < this.getSizeAlphabet(); j++) {
	// if (Double.parseDouble(matrix_tfidf[i][j]) > max) {
	// max = Double.parseDouble(matrix_tfidf[i][j]);
	// }
	// }
	// // ponto 2)
	// maxfij[i][0] = max;
	//
	// }
	//
	// // copiar uma matriz para outra
	// String[][] maxtfidf = new String[lin][col];
	// for (int i = 0; i < lin; i++) {
	// for (int j = 0; j < col; j++) {
	// maxtfidf[i][j] = matrix_tfidf[i][j];
	// }
	//
	// }
	//
	// // calcular tfij = fij / maxtfij
	// for (int i = 0; i < this.getNumberOfInstances(); i++) {
	// for (int j = 1; j < this.getSizeAlphabet() + 1; j++) {
	//
	// double aux = (Double.parseDouble(matrix_tfidf[i][j]))
	// / (maxfij[i][0]);
	// // ponto 3
	// maxtfidf[i][j] = Double.toString(aux);
	//
	// }
	//
	// }
	//
	// // idfi = log2(N/ni)
	// int N = this.getNumberOfInstances();
	// double[][] ni = new double[1][this.getSizeAlphabet()];
	//
	// for (int j = 1; j < this.getSizeAlphabet() + 1; j++) {
	// double max = 0;
	// for (int i = 0; i < this.getNumberOfInstances(); i++) {
	// if (Double.parseDouble(matrix_tfidf[i][j]) > max) {
	// max = Double.parseDouble(matrix_tfidf[i][j]);
	// }
	// }
	// // ponto 4)
	// ni[0][j - 1] = max;
	// }
	//
	// double[][] idfi = new double[1][this.getSizeAlphabet()];
	// for (int i = 0; i < idfi[0].length; i++) {
	// double aux = N / ni[0][i];
	// // ponto 5
	// idfi[0][i] = this.log(aux, 2);
	// }
	//
	// // wij = tfi * idfi
	// DecimalFormat aux3 = new DecimalFormat("0.##");
	// for (int i = 0; i < this.getNumberOfInstances(); i++) {
	// for (int j = 1; j < this.getSizeAlphabet(); j++) {
	// double aux2 = (Double.parseDouble(maxtfidf[i][j]))
	// * (idfi[0][j - 1]);
	// String aux4 = aux3.format(aux2);
	//
	// matrix_tfidf[i][j] = aux4;
	//
	// }
	//
	// }
	//
	// }

	static double log(double x, int base) {
		return (double) (Math.log(x) / Math.log(base));
	}

	/**
	 * constroi a matriz_bool com 1 caso a feature exista para a lirica ou 0
	 * caso contrário i-linha; j-coluna
	 * 
	 */
	private void calculateBoolValues() {
		for (int i = 0; i < matrix_bool.length; i++) {
			for (int j = 1; j < matrix_bool[i].length; j++) {
				String s = matrix_bool[i][j];
				if (s != "0")
					matrix_bool[i][j] = "1";

			}
		}

	}

	private void writeNamesOfFeaturesNames(File file) throws IOException {

		boolean isFile = file.exists();

		// se o ficheiro nao existir cria um novo, caso contrario
		// escreve por cima
		if (!isFile) {
			file.createNewFile();
		}

		// abre o ficheiro anterior para escrita
		BufferedWriter out = new BufferedWriter(new FileWriter(file));

		out.write(instances.getAlphabet().toString());

		out.close();

	}

	/**
	 * escreve a matriz num file .txt
	 * 
	 * @throws IOException
	 */
	private void writeMatrixInFile(String[][] matrix, File file)
			throws IOException {

		boolean isFile = file.exists();

		// se o ficheiro nao existir cria um novo, caso contrario
		// escreve por cima
		if (!isFile) {
			file.createNewFile();
		}

		// abre o ficheiro anterior para escrita
		BufferedWriter out = new BufferedWriter(new FileWriter(file));

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				out.write(matrix[i][j] + " ");
			}
			out.newLine();
		}
		out.close();
	}

	/**
	 * inicializa uma matriz a zeros
	 */
	private void initializeMatrix(String[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				String s = matrix[i][j];
				if (s == null)
					matrix[i][j] = "0";

			}
		}
	}

	/**
	 * escreve a matriz criada no ecra
	 */
	private void writeMatrixInConsole(String[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * escreve numa matriz com nºlinhas igual ao nº de instancias e nºcolunas
	 * igual ao nº de features+1
	 * 
	 * @param listOfWords
	 * @param name
	 *            (identifica o nome da instancia atual)
	 * @param i
	 *            (identifica a instancia atual)
	 */
	private void writeMatrix(ArrayList<String> listOfWords, String name, int i,
			String[][] matrix) {
		Iterator<String> it = listOfWords.iterator();
		while (it.hasNext()) {
			String line = it.next();
			 //System.out.println(line);
			int ind1 = getIndex(line, "(") + 1;
			int ind2 = getIndex(line, ")");
			int ind3 = getIndex(line, "=") + 1;
			int ind4 = getIndex(line, ".");
			String pos = line.substring(ind1, ind2);
			String freq = line.substring(ind3, ind4);
			// System.out.println(pos + " ------->>> " + freq);

			// tirar o .txt
			int ind5 = getIndex(name, ".");
						
			matrix[i][0] = name.substring(0, ind5);

			matrix[i][Integer.parseInt(pos) + 1] = freq;
		}
	}

	/**
	 * devolve o indice de um dado caracter numa string
	 * 
	 * @param line
	 * @param ch
	 * @return line.indexOf(ch)
	 */
	private int getIndex(String line, String ch) {
		return line.indexOf(ch);
	}

	/**
	 * os dados de cada instancia i entao armazenados na forma
	 * "defense(68)=1.0 \n son(69)=1.0 \n ..." na mesma variavel. este metodo
	 * pretende colocar dentro de um arraylist em que cada uma das suas entradas
	 * corresponde a uma palavra e a sua frequencia ex: defense(68)=1.0
	 * 
	 * @param int i (instancia i)
	 * @return ArrayList<String> lw
	 */
	private ArrayList<String> buildListOfWords(int i) {
		// TODO Auto-generated method stub
		String list[] = instances.get(i).getData().toString().split("\n");
		int len = list.length;
		ArrayList<String> lw = new ArrayList<String>();
		for (int j = 0; j < len; j++) {
			lw.add(list[j]);
		}
		return lw;
	}

	/**
	 * devolve o nome do file da lyric que corresponde ao id da musica. as
	 * lyrics tem obrigatoriamente de estar armazenadas num directorio origem
	 * 
	 * @param name
	 * @return String data[1]
	 */
	private String getNameOfInstance(String name) {
		// atencao a esta instrucao que so funciona se o file estiver na pasta
		// origem/
		String data[] = name.split("q4/");
		return data[1];
	}

	/**
	 * devolve o nº de palavras de todos os documentos exceptuando as stopwords.
	 * estas palavras serao as features associadas a cada instancia
	 * 
	 * @return int instances.getAlphabet().size();
	 */
	private int getSizeAlphabet() {
		return instances.getAlphabet().size();
	}

	/**
	 * devolve o nº de letras no directorio origem
	 * 
	 * @return int instances.size()
	 */
	private int getNumberOfInstances() {
		return instances.size();
	}

}
