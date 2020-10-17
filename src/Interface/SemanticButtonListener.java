package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class SemanticButtonListener implements ActionListener {
	MainInterface main_interface;
	
	public SemanticButtonListener(MainInterface main_interface) {
		this.main_interface = main_interface;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		main_interface.dispose();
		SemanticFeatures frame_semantica = null;
		frame_semantica = new SemanticFeatures(main_interface.sourceFolder);
		frame_semantica.setTitle("Selecao de features semanticas");
		frame_semantica.setSize(400,250);
		frame_semantica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_semantica.setVisible(true);	
	}

}
