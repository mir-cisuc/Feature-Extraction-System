package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import CombinedFeatures.CombinedFeatures;
import CBF.CBF_Initial;
import Synesketch.mainApp_Synesketch;

@SuppressWarnings("serial")
public class ContentFeatures extends JFrame{
	JPanel panel_content;
	JButton allfeatures, features_CBF, back;
	JLabel label;
	String sourceFolder;
	public ContentFeatures(String sourceFolder) {
		super();
		this.sourceFolder = sourceFolder;
		panel_content = new JPanel();
		panel_content.setLayout(null);
		
		allfeatures = new JButton("Todas as features");
		features_CBF = new JButton("CBF Features");

		back = new JButton("Back");
		
		label = new JLabel("Quais features de conteudo deseja extrair?");
		
		label.setBounds(75,10,300,25);
		allfeatures.setBounds(25,40,160,25);
		features_CBF.setBounds(200,40,160,25);

		back.setBounds(200,180,160,25);

		features_CBF.addActionListener(new ContentFeatures.CBF_ButtonListener(this));
	//	allfeatures.addActionListener(new ContentFeatures.AllFeaturesListener());
		back.addActionListener(new BackContentButtonListener(this));
 
		panel_content.add(label);
		panel_content.add(allfeatures);
		panel_content.add(features_CBF);
		panel_content.add(back);
		
		this.add(panel_content);
	}
	
	private class CBF_ButtonListener implements ActionListener {	
		ContentFeatures content_features;

		public CBF_ButtonListener(ContentFeatures content) {
			this.content_features = content;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ContentSelector content_selector = null;
			content_selector = new ContentSelector(sourceFolder,this.content_features);
			content_selector.setTitle("Selecao de opçoes de conteudo");
			content_selector.setSize(400,220);
			content_selector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			content_selector.setVisible(true);			
		}
	}

	/*private class AllFeaturesListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JOptionPane.showMessageDialog(null, "Todas as features de conteudo extraidas", "Mensagem", JOptionPane.PLAIN_MESSAGE);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		}
	}
	*/
	
	
}
