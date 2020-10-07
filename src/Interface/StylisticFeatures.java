package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import CapitalLetters.CapitalLetters_Initial;
import Gazeteers.Initial_Gazeteers;
@SuppressWarnings("serial")
public class StylisticFeatures extends JFrame{
	JPanel panel_stylistic;
	JButton allfeatures, featuresSlang, featuresCL;
	JLabel label;
	public StylisticFeatures() {
		super();
		panel_stylistic = new JPanel();
		panel_stylistic.setLayout(null);
		
		allfeatures = new JButton("Todas as features");
		featuresSlang = new JButton("Features Slang");
		featuresCL = new JButton("Features CapitalLetters");

		label = new JLabel("Quais features estilisticas deseja extrair?");
		
		label.setBounds(75,10,300,25);
		allfeatures.setBounds(25,40,160,25);
		featuresSlang.setBounds(200,40,160,25);
		featuresCL.setBounds(90,80,200,25);
		
		featuresCL.addActionListener(new StylisticFeatures.CL_ButtonListener());
		
		panel_stylistic.add(label);
		panel_stylistic.add(allfeatures);
		panel_stylistic.add(featuresSlang);
		panel_stylistic.add(featuresCL);

		
		this.add(panel_stylistic);
	}
	private class CL_ButtonListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				CapitalLetters_Initial capitalLetters = new CapitalLetters_Initial();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
