package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import DAL_ANEW.Initial_ANEW;
import GI.Initial_GI;
import Gazeteers.Initial_Gazeteers;
import Synesketch.mainApp_Synesketch;

@SuppressWarnings("serial")
public class SemanticFeatures extends JFrame{
	JPanel panel_semantic;
	JButton allfeatures, features_GI, featuresSynesketch, featuresDAL_ANEW, featuresGazeteers;
	JLabel label;
	public SemanticFeatures() {
		super();
		panel_semantic = new JPanel();
		panel_semantic.setLayout(null);
		
		allfeatures = new JButton("Todas as features");
		features_GI = new JButton("Features GI");
		featuresSynesketch = new JButton("Features Synesktech");
		featuresDAL_ANEW = new JButton("Features DAL_ANEW");
		featuresGazeteers = new JButton("Features Gazeteers");
		
		label = new JLabel("Quais features semanticas deseja extrair?");
		
		label.setBounds(75,10,300,25);
		allfeatures.setBounds(25,40,160,25);
		features_GI.setBounds(200,40,160,25);
		featuresSynesketch.setBounds(25,80,160,25);
		featuresDAL_ANEW.setBounds(200,80,160,25);
		featuresGazeteers.setBounds(200,120,160,25);
		
		featuresGazeteers.addActionListener(new SemanticFeatures.GazeteersButtonListener());
		featuresDAL_ANEW.addActionListener(new SemanticFeatures.DAL_ANEWButtonListener());
		features_GI.addActionListener(new SemanticFeatures.GI_ButtonListener());
		featuresSynesketch.addActionListener(new SemanticFeatures.SynesketchButtonListener());
		
 
		panel_semantic.add(label);
		panel_semantic.add(allfeatures);
		panel_semantic.add(features_GI);
		panel_semantic.add(featuresSynesketch);
		panel_semantic.add(featuresDAL_ANEW);
		panel_semantic.add(featuresGazeteers);
		
		this.add(panel_semantic);
	}
	private class GazeteersButtonListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Initial_Gazeteers initial_AV = new Initial_Gazeteers();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private class DAL_ANEWButtonListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Initial_ANEW initial= new Initial_ANEW();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private class GI_ButtonListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Initial_GI initial= new Initial_GI();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private class SynesketchButtonListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				mainApp_Synesketch ma = new mainApp_Synesketch();
				try {
					ma.init_2();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
			 
				
	}
	
}
