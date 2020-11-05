package StructuralFeatures;

public class FraseLetra {
	private String letra;
	private boolean canBeChorus;
	private int ocorrencias;

	public FraseLetra(String letra) {
		// TODO Auto-generated constructor stub
		this.letra = letra;
		this.canBeChorus = true;
		this.ocorrencias = 0;
	}

	@Override
	public String toString() {
		return "FraseLetra [letra=" + letra + ", canBeChorus=" + canBeChorus + ", ocorrencias=" + ocorrencias + "]";
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public boolean getCanBeChorus() {
		return canBeChorus;
	}

	public void setCanBeChorus() {
		this.canBeChorus = false;
	}

	public int getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(int ocorrencias) {
		this.ocorrencias = ocorrencias;
	}


}
