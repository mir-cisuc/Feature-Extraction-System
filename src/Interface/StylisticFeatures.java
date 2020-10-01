package Interface;

import javax.swing.*;
@SuppressWarnings("serial")
public class StylisticFeatures extends JFrame{
	JPanel panel_stylistic;
	JButton allfeatures, featuresSlang, featuresFCL, featuresACL;
	JLabel label;
	public StylisticFeatures() {
		super();
		panel_stylistic = new JPanel();
		panel_stylistic.setLayout(null);
		
		allfeatures = new JButton("Todas as features");
		featuresSlang = new JButton("Features Slang");
		featuresFCL = new JButton("Features FCL");
		featuresACL = new JButton("Features ACL");

		label = new JLabel("Quais features estilisticas deseja extrair?");
		
		label.setBounds(75,10,300,25);
		allfeatures.setBounds(25,40,160,25);
		featuresSlang.setBounds(200,40,160,25);
		featuresFCL.setBounds(25,80,160,25);
		featuresACL.setBounds(200,80,160,25);
		
		panel_stylistic.add(label);
		panel_stylistic.add(allfeatures);
		panel_stylistic.add(featuresSlang);
		panel_stylistic.add(featuresFCL);
		panel_stylistic.add(featuresACL);

		
		this.add(panel_stylistic);
	}
	
}
