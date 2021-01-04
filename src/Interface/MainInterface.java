package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import AuxiliarFiles.WriteCSVFinal;
import CBF.CBF_Initial;
import CapitalLetters.CapitalLetters_Initial;
import CombinedFeatures.CombinedFeatures;
import GI.Initial_GI;
import StanfordPosTagger.SPT_Initial;
import StructuralFeatures.countTitle;
import Synesketch.mainApp_Synesketch;

@SuppressWarnings("serial")
public class MainInterface extends JFrame{
	private JButton semantic_button, stylistic_button, directoryButton, content_button, structural_button;
	JPanel panel;
	JLabel warning;
	String sourceFolder;
	
	public MainInterface(String sourceFolder1) {
		super();
		this.sourceFolder = sourceFolder1;
		semantic_button = new JButton("Features Semanticas");
		stylistic_button = new JButton("Features Estilisticas");
		content_button = new JButton("Content-Based Features");
		structural_button = new JButton("Structural Based Features");
		
		directoryButton = new JButton("Escolher pasta");
		
		warning = new JLabel("Se nao for escolhida diretoria, vai ser usada a pasta Origem por omissão.");
		
		semantic_button.setBounds(30,20,200,25);
		stylistic_button.setBounds(240,20,200,25);
		content_button.setBounds(30,60,200,25);
		directoryButton.setBounds(150,110,160,25);
		structural_button.setBounds(240,60,200,25);
		warning.setBounds(25,150,450,25);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.add(semantic_button);
		panel.add(stylistic_button);
		panel.add(content_button);
		panel.add(structural_button);
		panel.add(directoryButton);
		panel.add(warning);
		semantic_button.addActionListener(new SemanticButtonListener(this));
		stylistic_button.addActionListener(new StylisticButtonListener(this));
		content_button.addActionListener(new ContentButtonListener(this));
		structural_button.addActionListener(new StructuralButtonListener(this));
		directoryButton.addActionListener(new DirectoryButtonListener());
		
		this.add(panel);
	}
	
	public static void main(String[] args) {
		if (args.length == 3) {
			String tipoExtracao = args[0];
			String inputFile = args[1];
			String outputFile = args[2];	
			HandleRequest(tipoExtracao,inputFile,outputFile);
		}
		else if (args.length == 4) {
			if (!args[0].equals("features_titulo")) {
				System.out.println("O metodo de extracao com 4 argumentos tem que ser \"features_titulo\"");
				System.out.println("Uso: \"features_titulo\" inputFile outputFile tituloMusica");
			}
			else {
				String inputFile = args[1];
				String outputFile = args[2];
				String titulo = args[3];
				countTitle numero_titulo = new countTitle(titulo,inputFile,outputFile);
			}
		}
		else if (args.length == 5) {
			if (!args[0].equals("features_cbf")) {
				System.out.println("O metodo de extracao com 5 argumentos tem que ser \"features_cbf\"");
				System.out.println("Uso: \"features_cbf\" inputFile arg1 arg2 arg3");
				System.out.println("arg1 pode ser: 'unig', 'big', 'trig'");
				System.out.println("arg2 pode ser: 'nada', 'st', 'sw', 'st+sw'");
				System.out.println("arg3 pode ser: 'freq', 'bool', 'tfidf', 'norm'");
				System.out.println("O output file vai ser a combinacao, por exemplo 'unig_nada_freq.csv'");
			}
			else {
				String inputFolder = args[1];
				ArrayList<String> lista_arg3 = new ArrayList<String> (Arrays.asList("unig","big","trig"));
				ArrayList<String> lista_arg4 = new ArrayList<String> (Arrays.asList("nada","st","sw","st+sw"));
				ArrayList<String> lista_arg5 = new ArrayList<String> (Arrays.asList("freq","bool","tfidf","norm"));

				String arg3 = args[2];
				String arg4 = args[3];
				String arg5 = args[4];
				if (!lista_arg3.contains(arg3) || !lista_arg4.contains(arg4) || !lista_arg5.contains(arg5)) {
					if (!lista_arg3.contains(arg3)) {
						System.out.println("Argumento 1 incorreto.");
						System.out.println("arg1 pode ser: 'unig', 'big', 'trig'");
					}
					if (!lista_arg4.contains(arg4)) {
						System.out.println("Argumento 2 incorreto.");
						System.out.println("arg2 pode ser: 'nada', 'st', 'sw', 'st+sw'");
					}
					if (!lista_arg5.contains(arg5)) {
						System.out.println("Argumento 3 incorreto.");
						System.out.println("arg3 pode ser: 'freq', 'bool', 'tfidf', 'norm'");
					}														
				}				
				else {
					try {
						CBF_Initial initial = new CBF_Initial(inputFolder,arg3,arg4,arg5);				
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}
			}
		}
		else {
			//System.out.println("correr normal!");
			MainInterface main_frame = new MainInterface(null);
			main_frame.setTitle("Janela principal do programa");
			main_frame.setSize(475,225);
			main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			main_frame.setVisible(true);
			main_frame.addWindowListener((WindowListener) new WindowAdapter()  
		    {  
				public void windowClosing(WindowEvent e) {  
					WriteCSVFinal.WriteSemantic();
					WriteCSVFinal.WriteStylistic();
		    	}
		    });
		}
	}
	
	private class DirectoryButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = true;

			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Escolha a pasta com os ficheiros de origem: ");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			while(flag) {
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (jfc.getSelectedFile().isDirectory()) {
						//System.out.println("You selected the directory: " + jfc.getSelectedFile());
						if (jfc.getSelectedFile().list().length > 0){
							sourceFolder = jfc.getSelectedFile().getAbsolutePath();
							flag = false;
						}
						else {
							JOptionPane.showMessageDialog(null, "A pasta escolhida nao tem ficheiros, por favor escolha outra", "Aviso", JOptionPane.PLAIN_MESSAGE);	
						}
					}
				}
				else if (returnValue == JFileChooser.CANCEL_OPTION) {
					flag = false;
				}
			}
			
		}		
	}
	
	public static void HandleRequest(String tipoExtracao, String inputFile, String outputFile) {
		String [] listaOpcoes= {"features_gi","features_synesktech","features_dal_anew","features_gazeteers","features_slang","features_capitalletters","features_standardPOS","features_cbf","features_titulo"};
		File file = new File(inputFile);
			
		switch(tipoExtracao) {
			case "features_gi":
			try {
				Initial_GI initial= new Initial_GI(inputFile,outputFile);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "features_synesktech":
				mainApp_Synesketch ma = new mainApp_Synesketch();
				if (file.exists()){
					if (file.isDirectory()) {
						// System.out.println("Directory");
						try {
							ma.readDirectory(inputFile, outputFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (file.isFile()) {
						//System.out.println("File");
						try {
							ma.readFile(inputFile, outputFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}				
				break;
			case "features_dal_anew":
				if (file.exists()){
					if (file.isDirectory()) {
						// System.out.println("Directory");
						try {
							CombinedFeatures capitalLetters = new CombinedFeatures(false,false,true,false,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (file.isFile()) {
						//System.out.println("File");
						try {
							CombinedFeatures capitalLetters = new CombinedFeatures(true,false,true,false,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}							
				break;
			case "features_gazeteers":
				if (file.exists()){
					if (file.isDirectory()) {
						// System.out.println("Directory");
						try {
							CombinedFeatures capitalLetters = new CombinedFeatures(false,true,false,false,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (file.isFile()) {
						//System.out.println("File");
						try {
							CombinedFeatures capitalLetters = new CombinedFeatures(true,true,false,false,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				break;
			case "features_slang":
				if (file.exists()){
					if (file.isDirectory()) {
						// System.out.println("Directory");
						try {
							CombinedFeatures initial_WD  = new CombinedFeatures(false,false,false,true,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (file.isFile()) {
						//System.out.println("File");
						try {
							CombinedFeatures initial_WD  = new CombinedFeatures(true,false,false,true,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}			
				break;
			case "features_capitalletters":				
				if (file.exists()){
					if (file.isDirectory()) {
						// System.out.println("Directory");
						try {
							CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial(false,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (file.isFile()) {
						//System.out.println("File");
						try {
							CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial(true,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}			
				break;
			case "features_standardPOS":
				if (file.exists()){
					if (file.isDirectory()) {
						// System.out.println("Directory");
						try {
							SPT_Initial spt_initial = new SPT_Initial(false,inputFile,outputFile,1);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (file.isFile()) {
						//System.out.println("File");
						try {
							SPT_Initial spt_initial = new SPT_Initial(true,inputFile,outputFile,1);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				break;
			case "features_cbf":	
				System.out.println("Para usar \"features_cbf\" tem que fazer o seguinte:");
				System.out.println("Uso: \"features_cbf\" inputFile arg1 arg2 arg3");
				System.out.println("arg1 pode ser: 'unig', 'big', 'trig'");
				System.out.println("arg2 pode ser: 'nada', 'st', 'sw', 'st+sw'");
				System.out.println("arg3 pode ser: 'freq', 'bool', 'tfidf', 'norm'");
				System.out.println("O output file vai ser a combinacao, por exemplo 'unig_nada_freq.csv'");
				break;
			case "features_titulo":
				System.out.println("O titulo tem o seguinte uso: \nUso: \"features_titulo\" inputFile outputFile tituloMusica");
				break;
			default:
				System.out.println("Opcao errada! As opcoes sao as seguintes");
				for (String s : listaOpcoes) {
					System.out.println(s);
				}
				break;
		}
	}
	
}
	

	
