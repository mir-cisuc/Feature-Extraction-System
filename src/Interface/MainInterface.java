package Interface;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainInterface extends JFrame{
	private JButton semantic_button, stylistic_button;
	JPanel panel;
	
	public MainInterface() {
		super();
		semantic_button = new JButton("Features Semanticas");
		stylistic_button = new JButton("Features Estilisticas");
		
		semantic_button.setBounds(25,20,160,25);
		stylistic_button.setBounds(200,20,160,25);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.add(semantic_button);
		panel.add(stylistic_button);
		semantic_button.addActionListener(new SemanticButtonListener(this));
		stylistic_button.addActionListener(new StylisticButtonListener(this));
		
		this.add(panel);
	}
	
	public static void main(String[] args) {
		MainInterface main_frame = new MainInterface();
		main_frame.setTitle("Janela principal do programa");
		main_frame.setSize(400,100);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setVisible(true);	

	}
	

	

}
