package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class MainInterface extends JFrame{
	private JButton semantic_button, stylistic_button, directoryButton;
	JPanel panel;
	JLabel warning;
	String sourceFolder;
	
	public MainInterface(String sourceFolder1) {
		super();
		this.sourceFolder = sourceFolder1;
		semantic_button = new JButton("Features Semanticas");
		stylistic_button = new JButton("Features Estilisticas");
		directoryButton = new JButton("Escolher pasta");
		
		warning = new JLabel("Se nao for escolhida diretoria, vai ser usada a pasta Origem por omissão.");
		
		semantic_button.setBounds(50,20,160,25);
		stylistic_button.setBounds(225,20,160,25);
		directoryButton.setBounds(150,60,160,25);
		warning.setBounds(25,100,450,25);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.add(semantic_button);
		panel.add(stylistic_button);
		panel.add(directoryButton);
		panel.add(warning);
		semantic_button.addActionListener(new SemanticButtonListener(this));
		stylistic_button.addActionListener(new StylisticButtonListener(this));
		directoryButton.addActionListener(new DirectoryButtonListener());
		
		this.add(panel);
	}
	
	public static void main(String[] args) {
		MainInterface main_frame = new MainInterface(null);
		main_frame.setTitle("Janela principal do programa");
		main_frame.setSize(475,175);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setVisible(true);	

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
	

	

}
