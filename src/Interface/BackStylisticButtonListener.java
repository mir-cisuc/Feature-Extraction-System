package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class BackStylisticButtonListener implements ActionListener {
    MainInterface main_interface;
    StylisticFeatures stylistic_panel;
	
	public BackStylisticButtonListener(StylisticFeatures stylistic_panel) {
		this.stylistic_panel = stylistic_panel;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        stylistic_panel.dispose();
		MainInterface main_frame = new MainInterface(stylistic_panel.sourceFolder);
		main_frame.setTitle("Janela principal do programa");
		main_frame.setSize(475,175);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setVisible(true);	

    }

}
