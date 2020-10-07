package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class StylisticButtonListener implements ActionListener {
	MainInterface main_interface;
	public StylisticButtonListener(MainInterface main_interface) {
		this.main_interface = main_interface;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		main_interface.dispose();
		StylisticFeatures frame_stylistic = null;
		frame_stylistic = new StylisticFeatures();
		frame_stylistic.setTitle("Selecao de features estilisticas");
		frame_stylistic.setSize(400,220);
		frame_stylistic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_stylistic.setVisible(true);	
	}

}
