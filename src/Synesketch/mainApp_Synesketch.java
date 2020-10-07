/**
 * 
 */
package Synesketch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import AuxiliarFiles.ReadLyricsDirectoryToAString;
import Synesketch.Files.Emotion.EmotionalState;
import Synesketch.Files.Emotion.Empathyscope;

/**
 * @author rsmal
 * 
 * Para por a funcionar
 * 1. Mudar a pasta em baixo para ser a pasta onde estão as liricas .txt (no ex. em baixo e "lyrics-1180"...
 * 2. Mudar na classe "ReadLyricsDirectoryToAString" o nome do file que contem os nomes dos ficheiros ordenados 1 por linha
 * Neste ex. temos "lyrics-1180-nomes-ord.txt"
 * 3. O sistema vai guardar os 8 valores Synesketch num ficheiro ESCRITA.txt
 * A ordem e GeneralWeight, getValence, HappinessWeight, SadnessWeight, AngerWeight, FearWeight, DisgustWeight()
 * ,SurpriseWeight 
 * 
 * 
 */
public class mainApp_Synesketch {

	ArrayList<String> ids = new ArrayList();

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		mainApp_Synesketch ma = new mainApp_Synesketch();
		// ma.init_1();
		ma.init_2();
	}

	/**
	 * @throws IOException
	 * @input idSong
	 * @output weights-Synesketch
	 */
	private void init_1() throws IOException {
	//	String text = "I hate you!!!";
		ReadLyricsDirectoryToAString rl = new ReadLyricsDirectoryToAString();
		String idSong = rl.menu();
		String lyric = rl.showLyric(idSong);

		EmotionalState state = Empathyscope.getInstance().feel(lyric);

		System.out.println(state);

		// System.out.println(lyric);

	}

	public void init_2() throws IOException {
		ReadLyricsDirectoryToAString rl = new ReadLyricsDirectoryToAString();
		ids = rl.openFileOfIDs();

		//ids contem os nomes dos files (liricas) sem o .txt
		Iterator it = ids.iterator();
		
		String outputFolder = "src/Output/";

		FileWriter fstream = new FileWriter(outputFolder + "Synesketch_M49.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		
		

		while (it.hasNext()) {
			//idSong e por ex. L001-141
			String idSong = (String) it.next();
			
			//caminho completo para onde esta a lirica ex. lyrics-180/L001-141.txt
			String lyric = rl.ler("src/Origem/"+idSong+".txt");

			EmotionalState state = Empathyscope.getInstance().feel(lyric);
			System.out.println(state);
			
			System.out.println("Song: "+idSong);
			
			String line = idSong + "," + state.getGeneralWeight() + ","
					+ state.getValence() + "," + state.getHappinessWeight()
					+ "," + state.getSadnessWeight() + ","
					+ state.getAngerWeight() + "," + state.getFearWeight()
					+ "," + state.getDisgustWeight() + ","
					+ state.getSurpriseWeight();
			out.write(line);
			out.write("\n");
			
		}

		// Close the output stream
		out.close();

	}

}
