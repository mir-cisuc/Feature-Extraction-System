package StructuralFeatures;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class ChorusDetection {
	ArrayList<FraseLetra> letra = new ArrayList<>();
	public ChorusDetection(String sourceFile) {
		// TODO Auto-generated constructor stub
		Path path = Paths.get(sourceFile);
		String content = null;
		try {
			content = Files.readString(path, StandardCharsets.US_ASCII).toLowerCase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String [] lista_string = content.split("\n");
				
		System.out.println("----------------------------------------");
		

		for (String s : lista_string) {
			letra.add(new FraseLetra(s));
		}
		
		
		for (FraseLetra fl : this.letra) {
			if (!fl.getLetra().equals("")){
				fl.setOcorrencias(countOcorrencesofString(fl.getLetra()));
			}
			else {
				fl.setCanBeChorus();
			}
		}
		
		int indice = 0;
		
		for (FraseLetra fl: this.letra) { // isto aqui permite-nos livrarmo-nos de alguns versos
			if (fl.getOcorrencias() == 1 && fl.getCanBeChorus()) { 
				/* se for igual a 1, vamos ver se todas a frases que estao contidas neste bloco sao 1,
				 * se forem, nao e refrao, se nao forem, podera ser*/
				checkBlock(fl.getLetra(),indice);
			}
			indice++;
		}
		int maximo_ocorrencias = getMax();

		for (int i = 0; i < letra.size(); i++) {
			if (letra.get(i).getCanBeChorus() && letra.get(i).getOcorrencias() < maximo_ocorrencias) {
				boolean iBG = isBlockGood(i,maximo_ocorrencias);
				if (!iBG) {
					updateBlock(getStartingBlockIndex(i),getFinalBlockIndex(i));
				}
			}
		}
		
		
		for (int i = 0; i < letra.size(); i++) {
			if (letra.get(i).getCanBeChorus()) {
				System.out.println("i "  + i  + " com frase - " + letra.get(i).getLetra());
			}
		}
		
		printLetra();
	}
	
	public int getMax() {
		int max = 0;
		for (FraseLetra fl: letra) {
			if (fl.getOcorrencias() > max) {
				max = fl.getOcorrencias();
			}				
		}
		return max;
	}
	
	public boolean isBlockGood(int indice, int maximo) {
		int indice_inicio = getStartingBlockIndex(indice);
		int indice_fim = getFinalBlockIndex(indice);
		
		System.out.printf("ISBG %d %d\n", indice_inicio, indice_fim);
		for (int i = indice_inicio; i < indice_fim; i++) {
			if (letra.get(i).getOcorrencias() == maximo) {
				return true;
			}
		}
		return false;
		
	}
	
	public int getFinalBlockIndex(int indice) {
		for (int i = indice; i < letra.size(); i++) {
			if (this.letra.get(i).getOcorrencias() == 0) {
				return i;
			}
		}
		return letra.size();		
	}
	
	public int getStartingBlockIndex(int indice) {
		for (int i = indice; i > 0; i--) {
			if (this.letra.get(i).getOcorrencias() == 0) {
				return i;
			}
		}
		return 0;		
	}
	
	
	public void checkBlock(String s, int indice) {
		int indice_inicio = getStartingBlockIndex(indice);
		int indice_fim = getFinalBlockIndex(indice);
	
		boolean canBlockBeChorus = false;
		for (int i = indice_inicio; i != indice_fim; i++) {
			if (letra.get(i).getOcorrencias() > 1) {
				canBlockBeChorus = true;
				break;
			}
		}
		if (!canBlockBeChorus) {
			updateBlock(indice_inicio,indice_fim);
		}
	}
	
	public void updateBlock(int indice_inicio, int indice_fim) {
		System.out.printf("Vou dar update a %d, %d\n",indice_inicio, indice_fim);
		if (indice_inicio == 0) {
			letra.get(0).setCanBeChorus();
		}
		for (int i = indice_inicio+1; i < indice_fim; i++) {
			letra.get(i).setCanBeChorus();
		}
	}
	
	
	public void printLetra() {
		for (FraseLetra fl : this.letra) {
			System.out.println(fl);
		}
	}
	
	public int countOcorrencesofString(String s) {
		int ocorrencias = 0;
		for (FraseLetra frase_letra : this.letra) {
			if (frase_letra.getLetra().equals(s)) {
				ocorrencias++;
			}
		}
		return ocorrencias;
	}
	
	
	public static void writeCSV(String titulo, int value) {
		String outputFolder  = "src/Output/";
		FileWriter fileWriter2 = null;
		try {
			fileWriter2 = new FileWriter(outputFolder+"Titles.csv");
			
			fileWriter2.write("title, count");
			fileWriter2.write("\n");
			fileWriter2.write(titulo);
			fileWriter2.write(", ");
			fileWriter2.write(String.valueOf(value));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (fileWriter2 != null) {
					fileWriter2.flush();
					fileWriter2.close();					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String folder = "src/Origem/teste.txt";
		ChorusDetection chorusDetection = new ChorusDetection(folder);	
	}
	
	
	
}
