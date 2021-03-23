package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import AuxiliarFiles.WriteCSVFinal;
import CapitalLetters.CapitalLetters_Initial;
import CombinedFeatures.CombinedFeatures;


@SuppressWarnings("serial")
public class StylisticFeatures extends JFrame{
	JPanel panel_stylistic;
	JButton allfeatures, featuresSlang, featuresCL,back;
	JLabel label;
	String sourceFolder;
	public StylisticFeatures(String sourceFolder) {
		super();
		this.sourceFolder = sourceFolder;
		panel_stylistic = new JPanel();
		panel_stylistic.setLayout(null);
		
		allfeatures = new JButton("Todas as features");
		featuresSlang = new JButton("Features Slang");
		featuresCL = new JButton("Features CapitalLetters");
		back = new JButton("Back");

		label = new JLabel("Quais features estilisticas deseja extrair?");
		
		label.setBounds(75,10,300,25);
		allfeatures.setBounds(25,40,160,25);
		featuresSlang.setBounds(200,40,160,25);
		featuresCL.setBounds(90,80,200,25);
		back.setBounds(200,130,160,25);
		
		featuresCL.addActionListener(new StylisticFeatures.CL_ButtonListener());
		allfeatures.addActionListener(new StylisticFeatures.AllFeaturesButtonListener());
		featuresSlang.addActionListener(new StylisticFeatures.SlangButtonListener());
		back.addActionListener(new BackButtonListener(this));

		panel_stylistic.add(label);
		panel_stylistic.add(allfeatures);
		panel_stylistic.add(featuresSlang);
		panel_stylistic.add(featuresCL);
		panel_stylistic.add(back);
		
		this.add(panel_stylistic);
	}
	private class CL_ButtonListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial(false,sourceFolder,null);
				JOptionPane.showMessageDialog(null, "Features CapitalLetters extraidas", "Mensagem", JOptionPane.PLAIN_MESSAGE);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class SlangButtonListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				CombinedFeatures initial_WD  = new CombinedFeatures(false,false,false,true,sourceFolder,null);
				JOptionPane.showMessageDialog(null, "Features Slang extraidas", "Mensagem", JOptionPane.PLAIN_MESSAGE);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class AllFeaturesButtonListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial(false,sourceFolder,null);
				CombinedFeatures initial_WD  = new CombinedFeatures(false,false,false,true,sourceFolder,null);
				WriteCSVFinal writecsv = new WriteCSVFinal();
				writecsv.WriteStylistic(null,null,null);
				JOptionPane.showMessageDialog(null, "Todas features estilisticas extraidas", "Mensagem", JOptionPane.PLAIN_MESSAGE);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	
}
