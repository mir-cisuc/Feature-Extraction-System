package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class BackContentButtonListener implements ActionListener {
    MainInterface main_interface;
    ContentFeatures content_panel;
	
	public BackContentButtonListener(ContentFeatures content_panel) {
		this.content_panel = content_panel;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		content_panel.dispose();
		MainInterface main_frame = new MainInterface(content_panel.sourceFolder);
		main_frame.setTitle("Janela principal do programa");
		main_frame.setSize(475,225);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setVisible(true);	

    }

}
