package cc.mallet.pipe;

import java.util.HashSet;
import java.util.ArrayList;
import java.io.*;

import AuxiliarFiles.snowball.SnowballStemmer;
import AuxiliarFiles.snowball.ext.englishStemmer;

import cc.mallet.types.FeatureSequenceWithBigrams;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

/**
 * Convert the text in each token in the token sequence in the data field to
 * lower case.
 * 
 * @author Andrew McCallum <a
 *         href="mailto:mccallum@cs.umass.edu">mccallum@cs.umass.edu</a>
 */

public class TokenSequenceStemming extends Pipe implements Serializable {

	
	public Instance pipe(Instance carrier) {
		SnowballStemmer stemmer = new englishStemmer();
		TokenSequence in = (TokenSequence) carrier.getData();

		for (Token token : in) {
			stemmer.setCurrent(token.getText());
			stemmer.stem();
			token.setText(stemmer.getCurrent());
		}

		return carrier;
	}
		
		
		
	// <at> Override public Instance pipe(Instance carrier) {
	// SnowballStemmer stemmer = new englishStemmer();
	// TokenSequence in = (TokenSequence) carrier.getData();
	//
	// for (Token token : in) {
	// stemmer.setCurrent(token.getText());
	// stemmer.stem();
	// token.setText(stemmer.getCurrent());
	// }
	//
	// return carrier;
	// }
	
	
	
//	public Instance pipe(Instance carrier) {
//		TokenSequence ts = (TokenSequence) carrier.getData();
//
//		for (int i = 0; i < ts.size(); i++) {
//			Token t = ts.get(i);
//			t.setText(t.getText().toLowerCase());
//		}
//		return carrier;
//	}

	// Serialization

	private static final long serialVersionUID = 1;
	private static final int CURRENT_SERIAL_VERSION = 0;

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeInt(CURRENT_SERIAL_VERSION);
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		int version = in.readInt();
	}

}