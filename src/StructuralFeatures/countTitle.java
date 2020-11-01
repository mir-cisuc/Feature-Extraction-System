package StructuralFeatures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class countTitle {

	public countTitle() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path path = Paths.get("src/Origem/bp.txt");
		String content = Files.readString(path, StandardCharsets.US_ASCII).toLowerCase();
		//String content = "we are the lovesick girls";
		String titulo = "wish you were here".toLowerCase();	
				
		//content = content.replace(" "," ");
		//content = content.replace("?"," ");
		//content = content.replace(","," ");
		//content = content.replace("!"," "); 
		//content = content.replace("&"," ");
		//content = content.replaceAll("\\r\\n|\\r|\\n", " ");

		System.out.println(content.split(titulo, -1).length-1);
		
		/*String [] list = content.split(" ");
		
		int contador = 0;

		for (int j = 0; j < list.length; j++) {
			System.out.println(list[j]);
			if (list[j].toLowerCase().equals(lista_titulo[0].toLowerCase())) {
				if (check(lista_titulo,list,j+1)) {
					//System.out.println(lista_titulo[0].toLowerCase() + " " + list[j].toLowerCase());
					contador++;
				}
			}
		}
		System.out.println(contador);*/
	}

	/*public static boolean check (String [] titulo, String [] conteudo, int indice) {
		for (int i = 1; i< titulo.length; i++) {
			if (!titulo[i].toLowerCase().equals(conteudo[indice].toLowerCase())) {
				return false;
			}
			indice++;
		}
		//System.out.println(titulo[i].toLowerCase() + " " + conteudo[indice].toLowerCase());
		return true;
	}
	
	public static void printList(String [] lista) {
		for(String lista1 : lista) {
			System.out.println(lista1);
		}
	}*/
	
}
