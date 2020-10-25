package AuxiliarFiles;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class WriteCSVFinal {
	
	

	public static void main(String[] args) throws IOException {
		WriteSemantic();
		WriteStylistic();
		
	}
	public static void WriteSemantic() {
		ArrayList<String> inputSemantic = new ArrayList<String>();
		ArrayList<String> songsSemantic = new ArrayList<String>();
		inputSemantic.add("src/Output/Gazeteers.csv");
		inputSemantic.add("src/Output/DAL_ANEW.csv");
		inputSemantic.add("src/Output/Synesketch_M49.csv");
		inputSemantic.add("src/Output/GI_Features-1180.csv");
		
		int firstline;
		String headerSemantic="Id,";
		boolean firstFile=true;
		
		//ler todos os ficheiros com os valores das features semanticas
		for(int i = 0; i < inputSemantic.size(); i++)
		{
			String line = "";
			// use comma as separator
		    String cvsSplitBy = ", ";
		    
		    File f = new File(inputSemantic.get(i));
		    if(f.exists() && !f.isDirectory()) { 
		    	
		    	 try (BufferedReader br = new BufferedReader(new FileReader(inputSemantic.get(i)))) {
				    	firstline=0;
				        while ((line = br.readLine()) != null) {
				
				        	//no ficheiro  do synesketch os valores em vez de estar separado por virgula e um espaço, estao separados apenas virgula 
				        	if(i==2 && firstline!=0) {
						    	 cvsSplitBy = ",";
						    }
				            String[] values = line.split(cvsSplitBy);
				          
				            if(firstline==0) {
				            	//pegar no header das features e adicionar o nome antes
				            	for(int colunas = 1; colunas < values.length; colunas++) {
				            		if(i==0)
				            			headerSemantic+="Gazeteers_"+values[colunas]+",";
				            		else
				            			if (i==1)
				            				headerSemantic+="DAL_ANEW_"+values[colunas]+",";
			            			else
				            			if (i==2)
				            				headerSemantic+="Synesktech_"+values[colunas]+",";
			            			else
				            			if (i==3)
				            				headerSemantic+="GI_"+values[colunas]+",";
				            		
				            	}
				            	 
				            }else {
				            	
				            	 if(firstFile) {
				            		 //no primeiro ficheiro que lemos podemos pegar logo no id e valores e adiconar no lugar do array que corresponde a esta musica
				            		 String song="";
				            		 
				            		 for(int colunas = 0; colunas < values.length; colunas++) {
				            			 song+=values[colunas]+",";  			          		 
				            		 }
				            		
				            		 songsSemantic.add(song);
				            		
				            		 
				            		 
				            	 }else {
				            		 //a partir do segundo ficheiro, temos de procurar onde esta a musica no array e juntar os novos valores que lemos
				            		 String addValuesToSong="";
				            		 
				            		 if(!songsSemantic.isEmpty()) {
				            			
				            			 addValuesToSong=songsSemantic.get(firstline-1);
				            			 for(int colunas = 1; colunas < values.length; colunas++) {
					            			 addValuesToSong+=values[colunas]+",";	          		 
					            		 }
				            			 songsSemantic.set(firstline-1,addValuesToSong);
				            			 
				            		 }else {
				            			 for(int colunas = 0; colunas < values.length; colunas++) {
				            				 addValuesToSong+=values[colunas]+",";  			          		 
					            		 }
				            			 songsSemantic.add(addValuesToSong);
					            		 
				            		 }
				            		
				            		 
				            	 }
				            }
				           
				            firstline+=1;
				        }
				        br.close();
				
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
		    	
		    	
		    	
		    	 firstFile=false;
		    }
		
		   
		}
		//escrever para um ficheiro todos os valores das features semanticas das songs analisadas
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("src/Output/Semantic.csv");
			
			fileWriter.write(headerSemantic);
			fileWriter.write("\n");
			
			for(int i = 0; i < songsSemantic.size(); i++)
			{
				fileWriter.write(songsSemantic.get(i));
				fileWriter.write("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (fileWriter != null) {
					fileWriter.flush();
					fileWriter.close();					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void WriteStylistic() {
		ArrayList<String> inputStylistic = new ArrayList<String>();
		ArrayList<String> songsStylistic  = new ArrayList<String>();
		inputStylistic.add("src/Output/WordsDictionary.csv");
		inputStylistic.add("src/Output/CapitalLetters_M45.csv");
		
		int firstline;
		String headerStylistic ="Id,";
		boolean firstFile=true;
		//ler todos os ficheiros com os valores das features estilisticas
		for(int i = 0; i < inputStylistic.size(); i++)
		{
			String line = "";
		    String cvsSplitBy = ", ";
		    
		    File f = new File(inputStylistic.get(i));
		    if(f.exists() && !f.isDirectory()) { 
		
			    try (BufferedReader br = new BufferedReader(new FileReader(inputStylistic.get(i)))) {
			    	firstline=0;
			        while ((line = br.readLine()) != null) {
			
			            String[] values = line.split(cvsSplitBy);
			           
			            if(firstline==0) {
			            	//pegar no header das features e adicionar o nome antes
			            	for(int colunas = 1; colunas < values.length; colunas++) {
			            		if(i==0)
			            			headerStylistic+="WordsDictionary_"+values[colunas]+",";
			            		else
			            			headerStylistic+="CapitalLetters_"+values[colunas]+",";           		
			            	}
			            	 
			            }else {
			            	 if(firstFile) {
			            		 //no primeiro ficheiro que lemos podemos pegar logo no id e valores e adiconar no lugar do array que corresponde a esta musica
			            		 String song="";
			            		 
			            		 for(int colunas = 0; colunas < values.length; colunas++) {
			            			 song+=values[colunas]+",";  			          		 
			            		 }
			            		 
			            		 songsStylistic.add(song);
			            	 }else {
			            		//a partir do segundo ficheiro, temos de procurar onde esta a musica no array e juntar os novos valores que lemos 
			            		 
			            		 String addValuesToSong="";
			            		 
			            		 if(!songsStylistic.isEmpty()) {
			            			
			            			 addValuesToSong=songsStylistic.get(firstline-1);
			            			 for(int colunas = 1; colunas < values.length; colunas++) {
				            			 addValuesToSong+=values[colunas]+",";	          		 
				            		 }
			            			 songsStylistic.set(firstline-1,addValuesToSong);
			            			 
			            		 }else {
			            			 for(int colunas = 0; colunas < values.length; colunas++) {
			            				 addValuesToSong+=values[colunas]+",";  			          		 
				            		 }
			            			 songsStylistic.add(addValuesToSong);
				            		 
			            		 }
			            	 }
			            }
			           
			            firstline+=1;
			        }
			        br.close();
			
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			    firstFile=false;
		    }
		    
		    
		}
	
		//escrever para um ficheiro todos os valores das features estilisticas das songs analisadas
		FileWriter fileWriter2 = null;
		try {
			fileWriter2 = new FileWriter("src/Output/Stylistic.csv");
			
			fileWriter2.write(headerStylistic);
			fileWriter2.write("\n");
			
			for(int i = 0; i < songsStylistic.size(); i++)
			{
				fileWriter2.write(songsStylistic.get(i));
				fileWriter2.write("\n");
			}
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
	
}
