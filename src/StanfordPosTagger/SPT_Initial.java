package StanfordPosTagger;

import AuxiliarFiles.ReadOperations;
import AuxiliarFiles.WriteOperations;
import CapitalLetters.CapitalLetters_Initial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * Utilizando o Stanford POSTagger le um conjunto de files .txt e transforma
 * cada file na versao withTags ou onlyTags
 * 
 * Exemplo: onlyTags para o 1º verso da musica L001:
 * JJ TO VB RP WRB DT NN VBZ
 * WRB PRP VBZ DT JJ NN
 * CC NN VBZ RB TO NN
 * NNP VBZ TO NN
 * NNP VBZ TO NN
 * 
 * Exemplo: withTags para o 1º verso da musica L001:
 * Want/JJ to/TO find/VB out/RP where/WRB the/DT moon/NN goes/VBZ 
 * When/WRB it/PRP leaves/VBZ the/DT western/JJ sky/NN 
 * And/CC night/NN dissolves/VBZ again/RB to/TO morning/NN 
 * Azure/NNP turns/VBZ to/TO gold/NN 
 * Azure/NNP turns/VBZ to/TO gold/NN 
 *  
 * @author rsmal
 * @date
 */

public class SPT_Initial {
	static final String locTagger = "src/StanfordTaggers/bidirectional-distsim-wsj-0-18.tagger";
	static String sourceFolder = "src/Origem"; // pasta onde estao as liricas
													// a processar
	static final int withTags = 1;
	static final int onlyTags = 2;
	
	//output folder
	static final String outputFolder  = "src/Output/";
	
	public static void main(String[] args) throws ClassNotFoundException,
	IOException {
		SPT_Initial spt_initial = new SPT_Initial(null);
	}
	
	public SPT_Initial(String inputFile, String outputFile) throws ClassNotFoundException,IOException{
			// TODO Auto-generated method stub
			int option = withTags;
			
			// calcula as postags linha a linha e guarda em tagger
			PosTags postags = new PosTags();
			MaxentTagger tagger = postags.initialize(locTagger);
			
			// abrir o file para leitura
			FileReader fileReader = new FileReader(inputFile);
			BufferedReader in = new BufferedReader(fileReader);

			String thisLine;
			String lyric_text = "";

			// guarda cada linha de file (thisLine) numa string linha por linha
			// (lyric_text)
			while ((thisLine = in.readLine()) != null) {
				lyric_text = lyric_text + "\n" + thisLine;
			}

			String[] lyric_line; // array que guarda em cada posicao uma linha
									// completa
			lyric_line = lyric_text.split("\n");

			// calcula as postags linha a linha e guarda em tagger
			//PosTags postags = new PosTags();
			//MaxentTagger tagger = postags.initialize(locTagger);

			String textTags = "";

			if (option == withTags) {
				// para cada frase em lyric_line calcula a frase com tags
				for (int j = 0; j < lyric_line.length; j++) {
					String tagged = postags.tagString(tagger, lyric_line[j]);
					textTags = textTags + "\n" + tagged;
				}
			} else {
				for (int j = 0; j < lyric_line.length; j++) {
					String tagged = postags.tagString(tagger, lyric_line[j]);
					String taggedOnlyTags = postags.convertToOnlyTags(tagged);
					textTags = textTags + "\n" + taggedOnlyTags;
				}
			}

			// System.out.println(textWithTags);
			String[] filename;
			filename = inputFile.toString().split("\\.");
			// System.out.println(filename[0].toString());

			// guarda em file os fich de texto com postags
			WriteOperations wf = new WriteOperations();
			//if (option == withTags) {
			wf.writeFile(outputFile,textTags);
			//} else {
			//	wf.writeFile(outputFolder "_onlyPosTags.txt", textTags);
			//}
	}
	
	
	
	public SPT_Initial(String sourceFolder1) throws ClassNotFoundException,IOException {
		if(sourceFolder1 != null && !sourceFolder1.isEmpty()) {
			sourceFolder = sourceFolder1;				
		}
		else {
			sourceFolder = "src/Origem/";
		}
		// TODO Auto-generated method stub
		int option = withTags;

		// le os nomes dos ficheiros de uma pasta e guarda-os numa String[]
		// (files)
		ReadOperations rf = new ReadOperations();
		String[] files = rf.openDirectory(sourceFolder);

		// calcula as postags linha a linha e guarda em tagger
		PosTags postags = new PosTags();
		MaxentTagger tagger = postags.initialize(locTagger);
		
		// para cada file...
		for (int i = 0; i < files.length; i++) {
			// localizacao dos files
			String filename_path = sourceFolder + "/" + files[i];
			System.out.println("i = "+i);
			
			// abrir o file para leitura
			FileReader fileReader = new FileReader(filename_path);
			BufferedReader in = new BufferedReader(fileReader);

			String thisLine;
			String lyric_text = "";

			// guarda cada linha de file (thisLine) numa string linha por linha
			// (lyric_text)
			while ((thisLine = in.readLine()) != null) {
				lyric_text = lyric_text + "\n" + thisLine;
			}

			String[] lyric_line; // array que guarda em cada posicao uma linha
									// completa
			lyric_line = lyric_text.split("\n");

			// calcula as postags linha a linha e guarda em tagger
			//PosTags postags = new PosTags();
			//MaxentTagger tagger = postags.initialize(locTagger);

			String textTags = "";

			if (option == withTags) {
				// para cada frase em lyric_line calcula a frase com tags
				for (int j = 0; j < lyric_line.length; j++) {
					String tagged = postags.tagString(tagger, lyric_line[j]);
					textTags = textTags + "\n" + tagged;
				}
			} else {
				for (int j = 0; j < lyric_line.length; j++) {
					String tagged = postags.tagString(tagger, lyric_line[j]);
					String taggedOnlyTags = postags.convertToOnlyTags(tagged);
					textTags = textTags + "\n" + taggedOnlyTags;
				}
			}

			// System.out.println(textWithTags);
			String[] filename;
			filename = files[i].toString().split("\\.");
			// System.out.println(filename[0].toString());

			// guarda em file os fich de texto com postags
			WriteOperations wf = new WriteOperations();
			if (option == withTags) {
				wf.writeFile(outputFolder+ filename[0] + "_WithPosTags.txt", textTags);
			} else {
				wf.writeFile(outputFolder+ filename[0] + "_onlyPosTags.txt", textTags);
			}

		}
	}

}
