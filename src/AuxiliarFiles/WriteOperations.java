/**
 * 
 */
package AuxiliarFiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author rsmal
 * 
 */
public class WriteOperations {

	public void writeFileArray(String filename, ArrayList<String> filecontent)
			throws IOException {
		File file = new File("filename");

		boolean isFile = file.exists();

		// se o ficheiro nao existir cria um novo, caso contrario
		// escreve por cima
		if (!isFile) {
			file.createNewFile();
		}

		// abre o ficheiro anterior para escrita
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < filecontent.size(); i++) {
			out.write(filecontent.get(i));
			out.newLine();
		}
		out.close();
	}

	public void writeFile(String filename, String filecontent)
			throws IOException {
		File file = new File(filename);

		// System.out.println("filename## " + filename);

		boolean isFile = file.exists();

		// se o ficheiro nao existir cria um novo, caso contrario
		// escreve por cima
		if (!isFile) {
			file.createNewFile();
		}

		// abre o ficheiro anterior para escrita
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		String[] data;
		data = filecontent.split("\n");

		for (int i = 0; i < data.length; i++) {
			out.write(data[i]);
			out.newLine();
		}
		out.close();

	}

	/**
	 * escreve a matriz criada no ecra
	 */
	public void writeMatrixInConsole(String[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * escreve a matriz num file .txt
	 * 
	 * @throws IOException
	 */
	public void writeMatrixInFile(String[][] matrix, String filename)
			throws IOException {

		File file = new File(filename + ".txt");
		System.out.println(file.getAbsolutePath());
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
	
	public ArrayList<String> writeLinesInList(ArrayList<String> al, String str) {		
		al.add(str);
		al.add("\n");
		return al;
	}
	
	public String writeLinesInList(String st, String str) {		
		st = st + str;
		st = st + "\n";
		return st;
	}
	
}
