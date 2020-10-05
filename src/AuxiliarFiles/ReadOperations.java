/**
 * 
 */
package AuxiliarFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Several operations of reading files and folders
 * 
 * @example ReafFiles rf = new ReafFiles(); String[] files =
 *          rf.openDirectory("origem"); String filename = "origem/"+files[i];
 *          FileReader fileReader = new FileReader(filename); BufferedReader in
 *          = new BufferedReader(fileReader);
 * 
 * @author rsmal
 * @date 5/2/15
 * 
 */
public class ReadOperations {

	String[] files;

	/**
	 * open a directory "folder" and save in a String[] the name of the files
	 * within the directory
	 * 
	 * @example ReadOperations rf = new ReadOperations(); String[] files =
	 *          rf.openDirectory(sourcefolder);
	 * @return String[] files
	 * @param String
	 *            folder
	 * 
	 * @author rsmal
	 * @version 1.0
	 * @date 13/10/14
	 */
	public String[] openDirectory(String folder) {
		File diretory = new File(folder);
		files = diretory.list();
		return files;
	}

	/**
	 * Open a .txt file and save line do line to an ArrayList
	 * 
	 * @example ReadOperations rf = new ReadOperations(); String[] files =
	 *          rf.openDirectory(sourcefolder);
	 * @return ArrayList linesOfTheFile
	 * @param String
	 *            dictionary_file
	 * 
	 * @author rsmal
	 * @version 1.0
	 * @return
	 * @throws FileNotFoundException
	 * @date 03/02/15
	 */
	public ArrayList<String> openTxtFile(String dicFile)
			throws FileNotFoundException {
		ArrayList<String> linesOfTheFile = new ArrayList<String>();
		Scanner in = new Scanner(new FileReader(dicFile));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			linesOfTheFile.add(line);
		}
		in.close();
		return linesOfTheFile;
	}

	public int filesLenght(String[] files) {
		// TODO Auto-generated method stub
		return files.length;
	}

}