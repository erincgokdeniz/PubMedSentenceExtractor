package main;

import java.util.List;
import java.util.Map;

import businessobjects.PatternObject;
import utils.FileUtils;

/**
 * @author gokdeniz
 * This class is the base class for Sentence Extractor
 * */
public class PubMedSentenceExtractor {

	/** 
	 * type 
	 * 	- main : Sentences with brain region (i.e. amygdala)
	 * 	- left : Sentences with brain region and "left" term (left \w{0,4} amygdala) 
	 * 	- right: Sentences with brain region and "right" term (right \w{0,4} amygdala) 
	 * 	- left or right : Sentences with brain region and "left" or "right" terms 
	 * (left|right \w{0,4} amygdala) 
	 */
	
	public static void main(String[] args) {
		
		// Retrieve the list of patterns for each type of brain region retrieval
		List<PatternObject> patterns = FileUtils.getListOfPatterns();
		
		// Retrieve the sentences that contain the brain region for each type 
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		Map<String, List<String>> typeToSentenceMap = sentenceProcessor.processSentences(patterns);
		
		// Retrieve the list of patterns of the moods 
		List<String> moods = FileUtils.getListOfMoods();
		
		// Find the sentences with mood and brain region
		MoodProcessor moodProcessor = new MoodProcessor();
		moodProcessor.searchCoOccurrence(moods, typeToSentenceMap);
	}
	
		
	
}

