package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class StructuralButtonListener implements ActionListener {
	MainInterface main_interface;
	
	public StructuralButtonListener(MainInterface main_interface) {
		this.main_interface = main_interface;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		main_interface.dispose();
		StructuralFeatures frame_structural = null;
		frame_structural = new StructuralFeatures(main_interface.sourceFolder);
		frame_structural.setTitle("Selecao de features semanticas");
		frame_structural.setSize(400,200);
		frame_structural.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_structural.setVisible(true);	
	}

}
