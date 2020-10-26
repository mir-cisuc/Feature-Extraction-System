package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ContentButtonListener implements ActionListener {
	MainInterface main_interface;
	public ContentButtonListener(MainInterface main_interface) {
		this.main_interface = main_interface;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		main_interface.dispose();
		ContentFeatures frame_content = null;
		frame_content = new ContentFeatures(main_interface.sourceFolder);
		frame_content.setTitle("Seleçao de features de conteudo");
		frame_content.setSize(400,220);
		frame_content.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_content.setVisible(true);	
	}

}
