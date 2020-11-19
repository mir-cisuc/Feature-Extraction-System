package StructuralFeatures;

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
