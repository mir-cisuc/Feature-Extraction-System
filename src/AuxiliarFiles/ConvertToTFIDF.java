/**
 * 
 */
package AuxiliarFiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * import convertFromFreq.*;
 * ConvertToTFIDF ct = new ConvertToTFIDF();
 *		String matrix[][] = { { "Nome", "AAA", "BBB", "CCC", "Classe" },
 *				{ "D1", "3", "1", "0", "NA" }, { "D2", "2", "0", "1", "NA" },
 *				{ "D3", "2", "0", "0", "NA" }, { "D4", "0", "2", "0", "NA" } };
 *		ct.writeMatrixInConsole(matrix);
 *		ct.execute_tfidf(matrix);
 * 
 * 
 * @author rsmal
 * 
 */
public class ConvertToTFIDF {

	public static final String TFIDF = "tfidf";

	public void convertTfidf(String[][] matrix)
			throws IOException {

		/*
		 * Guardar em ficheiro as features da freq e a matriz com as freq de
		 * todas as liricas
		 */
		this.calcTfidfValues(matrix);
		this.writeMatrixConsole(matrix);
		String nameFile2 = "Teste_" + TFIDF;
		File file4 = new File(nameFile2 + ".csv");
		this.writeMatrixFile(matrix, file4);

	}

	/**
	 * escreve a matriz num file .txt
	 * 
	 * @throws IOException
	 */
	public void writeMatrixFile(String[][] matrix, File file)
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
				if (j == matrix[i].length - 1) {
					out.write(matrix[i][j]);
				} else
					out.write(matrix[i][j] + ",");
				// out.write(matrix[i][j] + " ");
			}
			out.newLine();
		}
		out.close();
	}

	/**
	 * escreve a matriz criada no ecra
	 */
	public void writeMatrixConsole(String[][] matrix) {
		// TODO Auto-generated method stub
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

	}

	private void calcTfidfValues(String[][] matrix) {

		// calcular o maxtfij 
		double[][] maxfij = new double[this.getNumberDocuments(matrix)][1];
		for (int i = 1; i <= this.getNumberDocuments(matrix); i++) {
			double max = 0;
			for (int j = 1; j <= this.getNumberFeatures(matrix); j++) {
				if (Double.parseDouble(matrix[i][j]) > max) {
					max = Double.parseDouble(matrix[i][j]);
				}
			}
			// ponto 2)
			maxfij[i - 1][0] = max;

		}

		// idfi = log2(N/ni)
		int N = this.getNumberDocuments(matrix);
		double[][] ni = new double[1][this.getNumberFeatures(matrix)];

		for (int j = 1; j <= this.getNumberFeatures(matrix); j++) {
			double max = 0;
			for (int i = 1; i <= this.getNumberDocuments(matrix); i++) {
				if (Double.parseDouble(matrix[i][j]) > max) {
					max = Double.parseDouble(matrix[i][j]);
				}
			}
			// ponto 4)
			ni[0][j - 1] = max;
		}

		double[][] idfi = new double[1][this.getNumberFeatures(matrix)];
		for (int i = 0; i < idfi[0].length; i++) {
			double aux = N / ni[0][i];
			// ponto 5
			idfi[0][i] = this.log(aux, 2);
		}

		// System.gc();
		// calcular tfij = fij / maxtfij
		for (int i = 1; i <= this.getNumberDocuments(matrix); i++) {
			for (int j = 1; j < this.getNumberFeatures(matrix) + 1; j++) {
				double aux = (Double.parseDouble(matrix[i][j]))
						/ (maxfij[i - 1][0]);
				// ponto 3
				matrix[i][j] = Double.toString(aux);
			}

		}

		// wij = tfi * idfi
		DecimalFormat aux3 = new DecimalFormat("0.##");
		for (int i = 1; i < this.getNumberDocuments(matrix); i++) {
			for (int j = 1; j < this.getNumberFeatures(matrix) + 1; j++) {
				double aux2 = (Double.parseDouble(matrix[i][j]))
						* (idfi[0][j - 1]);
				String aux4 = aux3.format(aux2).replace(",", ".");

				matrix[i][j] = aux4;

			}

		}

	}

	static double log(double x, int base) {
		// TODO Auto-generated method stub
		return (double) (Math.log(x) / Math.log(base));
	}

	/**
	 * devolve o nº de palavras de todos os documentos exceptuando as stopwords.
	 * estas palavras serao as features associadas a cada instancia
	 * 
	 * @return int instances.getAlphabet().size();
	 */
	private int getNumberFeatures(String[][] matrix) {
		// TODO Auto-generated method stub
		return matrix[0].length - 2;

	}

	/**
	 * devolve o nº de letras no directorio origem
	 * 
	 * @return int instances.size()
	 */
	private int getNumberDocuments(String[][] matrix) {
		// TODO Auto-generated method stub
		return matrix.length-1;

	}

}
