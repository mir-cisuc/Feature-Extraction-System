/**
 * 
 */
package StanfordPosTagger;

import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


/**
 * @author rsmal
 *
 */
public class PosTags {
		
	public MaxentTagger initialize(String locTagger) throws ClassNotFoundException, IOException {
		MaxentTagger tagger = new MaxentTagger(locTagger);
		return tagger;
	}
	
	public String tagString(MaxentTagger tagger, String str) {
		String tagged = tagger.tagString(str);
		return tagged;
	}
	
	public String convertToOnlyTags(String sentence) {
		
		String[] data = sentence.split(" ");
				
		String tagged="";
		
		for (int i=0; i < data.length; i++) {
			int a = data[i].indexOf('/');
			int b = data[i].length();
			String tags = data[i].substring(a + 1, b);
			tagged = tagged + " " + tags; 
					
		}
		//System.out.println(tagged);
		return tagged;
	}
	
	
}
