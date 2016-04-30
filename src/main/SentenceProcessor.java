package main;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import businessobjects.PatternObject;
import businessobjects.PubMedArticle;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.DocumentPreprocessor;
import service.XMLService;
import utils.FileUtils;
import utils.StringUtils;
/**
 * @author gokdeniz
 * This class is used to process the sentences to find the relevant ones
 * */
public class SentenceProcessor {

	protected List<String> sentences = null;
	protected Map<String, Integer> frequencyMap = null;
	protected Map<String, List<String>> typeToSentenceMap = null;
	XMLService xmlService = null;
	
	public SentenceProcessor(){
		sentences = new ArrayList<String>();
		frequencyMap = new HashMap<String, Integer>();
		typeToSentenceMap = new HashMap<String, List<String>>();
		typeToSentenceMap.put(StringUtils.MAIN, new ArrayList<String>());
		typeToSentenceMap.put(StringUtils.LEFT, new ArrayList<String>());
		typeToSentenceMap.put(StringUtils.RIGHT, new ArrayList<String>());
		typeToSentenceMap.put(StringUtils.LEFT_OR_RIGHT, new ArrayList<String>());
		
		xmlService = new XMLService();
		xmlService.init();	
	}

	/**
	 * This method gets the list of abstracts and search for each pattern in the sentences
	 * @param patterns : List of patterns that defines a brain region
	 * @return List<String> : type and the sentences for each type
	 * */
	public Map<String, List<String>> processSentences(List<PatternObject> patterns){
	
		Pattern p = null;
		Matcher matcher = null;
		String line = null;
		String abstractText = null;
		String sentenceDetail = null;
		int numberOfSentences = 0;
		
		// Retrieve the all abstracts from pubmed xml
		List<PubMedArticle> pubMedList = xmlService.getArticles(); 

		// For each publication scan all the patterns..
		for (PubMedArticle pubMedArticle : pubMedList){
			
			abstractText = pubMedArticle.getTitle() + ". " + pubMedArticle.getAbstractText();

			if (abstractText != null){
	
				for (List<HasWord> sentence : new DocumentPreprocessor(new StringReader(abstractText))){
					line = Sentence.listToString(sentence);
					if (line != null && !line.equalsIgnoreCase("null")){
						numberOfSentences++;
					}
					
					for (PatternObject patternObject:patterns){
						p = Pattern.compile(patternObject.getPatternText());
				    	matcher = p.matcher(line);
				    	
				    	if (matcher.find()){
				    		
				    		sentenceDetail = pubMedArticle.getPmid() + "|" + line;
							typeToSentenceMap.get(patternObject.getType()).add(sentenceDetail);
							
				    	} else {
				    		// If the core term does not exist, no need to check for right and left
				    		if (patternObject.getType().equalsIgnoreCase(StringUtils.MAIN)) break;
				    	}

					}
											
			    }
			}
		}

		System.out.println("Number of abstracts : " + pubMedList.size());
		System.out.println("Total number of sentences : " + numberOfSentences);
		System.out.println("Sentences with Amygdala : " + (typeToSentenceMap.get(StringUtils.MAIN)).size());
		System.out.println("Left : " + (typeToSentenceMap.get(StringUtils.LEFT)).size());
		System.out.println("Right : " + (typeToSentenceMap.get(StringUtils.RIGHT)).size());
		System.out.println("left_or_right : " + (typeToSentenceMap.get(StringUtils.LEFT_OR_RIGHT)).size());
		
		
		FileUtils.writeSentencesToFile(typeToSentenceMap.get(StringUtils.MAIN), StringUtils.MAIN);
		FileUtils.writeSentencesToFile(typeToSentenceMap.get(StringUtils.LEFT), StringUtils.LEFT);
		FileUtils.writeSentencesToFile(typeToSentenceMap.get(StringUtils.RIGHT), StringUtils.RIGHT);
		FileUtils.writeSentencesToFile(typeToSentenceMap.get(StringUtils.LEFT_OR_RIGHT), StringUtils.LEFT_OR_RIGHT);

		return typeToSentenceMap;
	}

	/**
	 * This method gets the terms of a sentence and adds them to a frequency map
	 * @param line : Sentence to be tokenized for calculating frequency for each term
	 * */
	private void addFrequency(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		while (tokenizer.hasMoreElements()){
			String key = tokenizer.nextElement().toString().toLowerCase();
			if (!StringUtils.isInBlackList(key)){
				if (key.trim().length()>1){
					if (frequencyMap.containsKey(key)){
						Integer frequency = frequencyMap.get(key);
						frequencyMap.put(key, frequency + 1); 
					} else {
						frequencyMap.put(key, 1);
					}	
				}
			}
		}
	}
	
}
