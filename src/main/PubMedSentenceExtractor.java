package main;

import java.util.List;
import java.util.Map;

import businessobjects.PatternObject;
import utils.FileUtils;

public class PubMedSentenceExtractor {

	public static void main(String[] args) {
		
		List<PatternObject> patterns = FileUtils.getListOfPatterns();
		List<String> moods = FileUtils.getListOfMoods();
		SentenceProcessor sentenceProcessor = new SentenceProcessor();
		Map<String, List<String>> typeToSentenceMap = sentenceProcessor.processSentences(patterns);
		MoodProcessor moodProcessor = new MoodProcessor();
		moodProcessor.searchCoOccurrence(moods, typeToSentenceMap);
	}
	
		
	
}

