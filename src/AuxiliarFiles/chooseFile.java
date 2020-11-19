import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class chooseFile {
	public static void main(String [] args) {	
		boolean flag = true;
		String sourceFile = null;
	
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Escolha o ficheiro: ");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		while(flag) {
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				if (jfc.getSelectedFile().isFile()) {
					//System.out.println("You selected the directory: " + jfc.getSelectedFile());
					String filename = jfc.getSelectedFile().getName();
					String extension = filename.substring(filename.lastIndexOf("."),filename.length());
					if (extension.equals(".txt")){
						sourceFile = jfc.getSelectedFile().getAbsolutePath();
						flag = false;
					}
					else {
						JOptionPane.showMessageDialog(null, "Por favor escolha um ficheiro .txt", "Aviso", JOptionPane.PLAIN_MESSAGE);	
					}
				}
			}
			else if (returnValue == JFileChooser.CANCEL_OPTION) {
				flag = false;
			}
		}
		System.out.println(sourceFile);
	}
}
