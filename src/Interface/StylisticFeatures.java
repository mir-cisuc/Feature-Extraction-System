package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import CapitalLetters.CapitalLetters_Initial;
import Gazeteers.Initial_Gazeteers;
import WordsDictionary.WordsDictionary_Initial;
@SuppressWarnings("serial")
public class StylisticFeatures extends JFrame{
	JPanel panel_stylistic;
	JButton allfeatures, featuresSlang, featuresCL,back;
	JLabel label;
	public StylisticFeatures() {
		super();
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
		back.addActionListener(new BackStylisticButtonListener(this));

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
				CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial();
				JOptionPane.showMessageDialog(null, "Features CapitalLetters extra�das", "Mensagem", JOptionPane.PLAIN_MESSAGE);
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
				WordsDictionary_Initial WordsDictionary_Initial = new WordsDictionary_Initial();
				JOptionPane.showMessageDialog(null, "Features Slang extra�das", "Mensagem", JOptionPane.PLAIN_MESSAGE);
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
				CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial();
				WordsDictionary_Initial WordsDictionary_Initial = new WordsDictionary_Initial();
				JOptionPane.showMessageDialog(null, "Todas features estil�sticas extra�das", "Mensagem", JOptionPane.PLAIN_MESSAGE);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	
}
