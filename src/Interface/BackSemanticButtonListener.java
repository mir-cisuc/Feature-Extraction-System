package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class BackSemanticButtonListener implements ActionListener {
    MainInterface main_interface;
    SemanticFeatures semantic_panel;
	
	public BackSemanticButtonListener(SemanticFeatures semantic_panel) {
		this.semantic_panel = semantic_panel;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        semantic_panel.dispose();
		MainInterface main_frame = new MainInterface();
		main_frame.setTitle("Janela principal do programa");
		main_frame.setSize(400,100);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setVisible(true);

    }

}
