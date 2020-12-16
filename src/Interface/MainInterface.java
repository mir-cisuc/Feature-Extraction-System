package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import AuxiliarFiles.WriteCSVFinal;
import CapitalLetters.CapitalLetters_Initial;
import CombinedFeatures.CombinedFeatures;
import GI.Initial_GI;
import StanfordPosTagger.SPT_Initial;
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
		else {
			System.out.println("correr normal!");
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
		String [] listaOpcoes= {"features_gi","features_synesktech","features_dal_anew","features_gazeteers","features_slang","features_capitalleters","features_standardPOS","features_CBF","titulo"};
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
				File file = new File(inputFile);
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
				/*try {
					CombinedFeatures initial  = new CombinedFeatures(false, true,false,sourceFolder); // false true vai ser gazeteers
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	*/			
				break;
			case "features_gazeteers":
				break;
			case "features_slang":
				break;
			case "features_capitalleters":				
				File file1 = new File(inputFile);
				if (file1.exists()){
					if (file1.isDirectory()) {
						// System.out.println("Directory");
						try {
							CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial(false,inputFile,outputFile);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (file1.isFile()) {
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
				try {
					SPT_Initial initial = new SPT_Initial(inputFile,outputFile);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "features_CBF":
				
				break;
			case "titulo":
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
