package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import CombinedFeatures.CombinedFeatures;
import StanfordPosTagger.SPT_Initial;
import StructuralFeatures.ChorusDetection;
import StructuralFeatures.countTitle;
import CBF.CBF_Initial;
import Synesketch.mainApp_Synesketch;

@SuppressWarnings("serial")
public class StructuralFeatures extends JFrame{
	JPanel panel_content;
	JButton numberOfTitle, back, chorusDetection;
	JLabel label;
	String sourceFolder;
	boolean hasTitle = false;
	public StructuralFeatures(String sourceFolder) {
		super();
		this.sourceFolder = sourceFolder;
		panel_content = new JPanel();
		panel_content.setLayout(null);
		
		numberOfTitle = new JButton("Número de repetições do título");
		chorusDetection = new JButton("Deteçao do refrao");
		back = new JButton("Back");
		
		label = new JLabel("Quais features de conteudo deseja extrair?");
		
		label.setBounds(75,10,300,25);
		numberOfTitle.setBounds(60,40,250,25);
		chorusDetection.setBounds(60,80,250,25);

		back.setBounds(100,130,160,25);

		
		numberOfTitle.addActionListener(new StructuralFeatures.numberOfTitleButtonListener());
		chorusDetection.addActionListener(new StructuralFeatures.chorusDetectionListener());
		back.addActionListener(new BackButtonListener(this));
 
		panel_content.add(label);
		panel_content.add(numberOfTitle);
		panel_content.add(chorusDetection);
		panel_content.add(back);
		
		
		this.add(panel_content);
		
	}
	
	
	private class numberOfTitleButtonListener implements ActionListener {	
		boolean hasTitle = false;
		boolean hasSourceFile = false;
		String titulo_inserido;
		String sourceFile;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = (String)JOptionPane.showInputDialog(
		               panel_content,
		               "Introduza o titulo", 
		               "Input de titulo",            
		               JOptionPane.PLAIN_MESSAGE,
		               null,            
		               null, 
		               ""
		            );
		            if(result != null && result.length() > 0){
		            	this.titulo_inserido = result;
		            	hasTitle = true;
            }
		    System.out.println(this.titulo_inserido);
		    
		    if (hasTitle) {    
		    	this.sourceFile = chooseFile();
		    }
		    if (this.sourceFile != null) {
		    	hasSourceFile = true;
		    }
	    
		    if (hasTitle && hasSourceFile) {
		    	countTitle titulo = new countTitle(this.titulo_inserido,this.sourceFile,null);
		    	JOptionPane.showMessageDialog(null, "Numero de repetiçoes do titulo calculado", "Mensagem", JOptionPane.PLAIN_MESSAGE);
		    }						
		}	
		
	}
	
	private class chorusDetectionListener implements ActionListener{
		String sourceFile;
		@Override
		public void actionPerformed(ActionEvent e) {
			this.sourceFile = chooseFile();
			if (this.sourceFile != null) {
				ChorusDetection chorusDetection = new ChorusDetection(this.sourceFile);
				JOptionPane.showMessageDialog(null, "Deteçao do refrao feita", "Mensagem", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		
	}
	
	
	public static String chooseFile() {
		boolean flag = true;
		String sourceFile = null;
	
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Escolha o ficheiro: ");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		while(flag) {
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				if (jfc.getSelectedFile().isFile()) {
					//System.out.println("You selected the directory: " + jfc.getSelectedFile());
					String filename = jfc.getSelectedFile().getName();
					String extension = filename.substring(filename.lastIndexOf("."),filename.length());
					if (extension.equals(".txt")){
						sourceFile = jfc.getSelectedFile().getAbsolutePath();
						return sourceFile;
					}
					else {
						JOptionPane.showMessageDialog(null, "Por favor escolha um ficheiro .txt", "Aviso", JOptionPane.PLAIN_MESSAGE);	
					}
				}
			}
			else if (returnValue == JFileChooser.CANCEL_OPTION) {
				flag = false;
			}
		}
		System.out.println(sourceFile);
		return null;
	}
	
	
}
