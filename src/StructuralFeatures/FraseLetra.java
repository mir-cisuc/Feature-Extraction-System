package StructuralFeatures;

import java.util.ArrayList;

public class FraseLetra {
	private String letra;
	private int ocorrencias;
	private int posicaoLetra;
	private double ChorusProbability;

	public FraseLetra(String letra, int posicaoLetra) {
		// TODO Auto-generated constructor stub
		this.letra = letra;
		this.ocorrencias = 0;
		this.posicaoLetra = posicaoLetra;
		this.ChorusProbability = 50;
	}



	@Override
	public String toString() {
		return "FraseLetra [letra=" + letra + ", ocorrencias=" + ocorrencias + ", posicaoLetra=" + posicaoLetra
				+ ", ChorusProbability=" + ChorusProbability + "]";
	}



	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public int getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(int ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public int getPosicaoLetra() {
		return posicaoLetra;
	}

	public void setPosicaoLetra(int posicaoLetra) {
		this.posicaoLetra = posicaoLetra;
	}

	public double getChorusProbability() {
		return ChorusProbability;
	}

	public void setChorusProbability(double chorusProbability) {
		ChorusProbability = chorusProbability;
	}
}

class Bloco{
	private ArrayList<FraseLetra> lista_frases;
	private double mainBlockProbability;
	private double mediaOcorrencias;

	public Bloco() {
		this.lista_frases = new ArrayList <> ();
		this.mainBlockProbability = 0;
		this.mediaOcorrencias = 0;
	}
	
	public void setProbability(double probability) {
		this.mainBlockProbability = probability;
	}
	
	public void setMediaOcorrencias(double media) {
		this.mediaOcorrencias = media;
	}
	
	public double getProbability() {
		return this.mainBlockProbability;
	}

	public double getMediaOcorrencias() {
		return mediaOcorrencias;
	}

	public ArrayList<FraseLetra> getBloco() {
		return lista_frases;
	}
	
	public void adicionarFrase(FraseLetra frase) {
		this.lista_frases.add(frase);
	}
	

	public void setFrasesBloco(ArrayList<FraseLetra> lista_frases) {
		this.lista_frases = lista_frases;
	}
	
	public int getTamanho() {
		return this.lista_frases.size();
	}
	
	public FraseLetra getFraseByIndex(int index) {
		return this.lista_frases.get(index);
	}

	public void printBloco() {
		for (FraseLetra fl: this.lista_frases) {
			System.out.println(fl);
		}
		System.out.println("---------------");
	}
	
	
}




