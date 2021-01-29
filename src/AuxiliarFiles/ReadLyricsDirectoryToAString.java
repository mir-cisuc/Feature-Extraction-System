/**
 * 
 */
package AuxiliarFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * @author rsmal
 * 
 */
public class ReadLyricsDirectoryToAString {

	
	public String menu() {

		System.out.print("Qual o ID da musica a mostrar:  ");

		BufferedReader buf = new BufferedReader(
				new InputStreamReader(System.in));
		String opcao = null;
		try {
			opcao = buf.readLine();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return opcao;
	}
	

	public String showLyric(String idSong) throws IOException {
		String file = "AllMusics_Final_MIRw.txt";
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);

		String thisLine;
		// percorre linha a linha o file de texto anterior
		while ((thisLine = in.readLine()) != null) {
			String data[] = thisLine.split(" ");
			// System.out.println(idSong);
			// System.out.println(data[0]);
			if (idSong.toUpperCase().equals(data[0])) {
				System.out.println(thisLine.replace("_", " "));
				System.out.println();
				System.out.println(data[1] + " ----- " + data[2]);
			}

		}
/*
		String a = this
				.ler("../../../PhD/MATERIAL TESE ATUAL/Corpus_phd_final_allmusic/lyrics/correct/"
						+ idSong.toLowerCase() + ".txt"); 
						*/
		String a = this
				.ler("lyrics_mir/"
						+ idSong + ".txt");
		// System.out.println(a);

		return a;
	}

	public ArrayList<String> openFileOfIDs() throws IOException {
		ArrayList<String> a = new ArrayList();
		String originFolder = "src/AuxiliarFiles/";
		String file = originFolder + "lyrics-names.txt";
		FileReader fileReader = new FileReader(file);
		BufferedReader in = new BufferedReader(fileReader);

		String thisLine;
		// percorre linha a linha o file de texto anterior
		while ((thisLine = in.readLine()) != null) {
			String data[] = thisLine.split(" ");
			System.out.printf("Data is %s\n", data[0]);
			a.add(data[0]);

		}

		return a;
	}

	/**
	 * metodo que abre um file de texto e escreve-o na consola
	 * 
	 * @param arquivo
	 * @throws IOException
	 */
	public String ler(String arquivo) throws IOException {
		String a = "";
		FileReader reader = new FileReader(arquivo);
		BufferedReader buffReader = new BufferedReader(reader);

		String linha;
		while ((linha = buffReader.readLine()) != null) {

			a = a + linha + "\n";
		}
		reader.close();
		return a;
	}

}
