package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import Gazeteers.Initial_AV;
@SuppressWarnings("serial")
public class SemanticFeatures extends JFrame{
	JPanel panel_semantic;
	JButton allfeatures, features_Gl, featuresSynesketch, featuresDAL, featuresANEW, featuresGazeteers;
	JLabel label;
	public SemanticFeatures() {
		super();
		panel_semantic = new JPanel();
		panel_semantic.setLayout(null);
		
		allfeatures = new JButton("Todas as features");
		features_Gl = new JButton("Features GI");
		featuresSynesketch = new JButton("Features Synesktech");
		featuresDAL = new JButton("Features DAL");
		featuresANEW = new JButton("Features ANEW");
		featuresGazeteers = new JButton("Features Gazeteers");
		
		label = new JLabel("Quais features sem�nticas deseja extrair?");
		
		label.setBounds(75,10,300,25);
		allfeatures.setBounds(25,40,160,25);
		features_Gl.setBounds(200,40,160,25);
		featuresSynesketch.setBounds(25,80,160,25);
		featuresDAL.setBounds(200,80,160,25);
		featuresANEW.setBounds(25,120,160,25);
		featuresGazeteers.setBounds(200,120,160,25);
		
		featuresGazeteers.addActionListener(new SemanticFeatures.GazeteersButtonListener());
		
		panel_semantic.add(label);
		panel_semantic.add(allfeatures);
		panel_semantic.add(features_Gl);
		panel_semantic.add(featuresSynesketch);
		panel_semantic.add(featuresDAL);
		panel_semantic.add(featuresANEW);
		panel_semantic.add(featuresGazeteers);
		
		this.add(panel_semantic);
	}
	private class GazeteersButtonListener implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Initial_AV initial_AV = new Initial_AV();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
