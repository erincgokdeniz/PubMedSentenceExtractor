package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.FileUtils;
import utils.StringUtils;


/**
 * @author gokdeniz
 * This class extracts the sentences that contains brain regions with the moods..
 * */
public class MoodProcessor {

	/**
	 * This method gets the list of sentences containing brain regions and checks the cooccurrence of a mood
	 * @param moods : This is the list of moods coming from configuration file
	 * @param typeToSentenceType : map of sentences for each type ("main"/"left"/"right"/"left_or_right")
	 * */
	public void searchCoOccurrence(List<String> moods,Map<String, List<String>> typeToSentenceMap){
		
		List<String> listOfAmygdala = new ArrayList<String>();
		List<String> listOfLeftAmygdala = new ArrayList<String>();
		List<String> listOfRightAmygdala = new ArrayList<String>();
		List<String> listOfLeftOrRightAmygdala = new ArrayList<String>();

		
		for(String mood:moods){
			System.out.println(mood.substring(4,mood.length()) + "***************************");
			listOfAmygdala = retrieveSentencesWithMood(typeToSentenceMap.get(StringUtils.MAIN), mood);
			listOfLeftAmygdala = retrieveSentencesWithMood(typeToSentenceMap.get(StringUtils.LEFT), mood);
			listOfRightAmygdala = retrieveSentencesWithMood(typeToSentenceMap.get(StringUtils.RIGHT), mood);
			listOfLeftOrRightAmygdala = retrieveSentencesWithMood(typeToSentenceMap.get(StringUtils.LEFT_OR_RIGHT), mood);
		
			FileUtils.writeSentencesToFile(listOfAmygdala, mood.substring(4,mood.length()) + "_" + StringUtils.MAIN);
			FileUtils.writeSentencesToFile(listOfLeftAmygdala, mood.substring(4,mood.length()) + "_" + StringUtils.LEFT);
			FileUtils.writeSentencesToFile(listOfRightAmygdala, mood.substring(4,mood.length()) + "_" + StringUtils.RIGHT );
			FileUtils.writeSentencesToFile(listOfLeftOrRightAmygdala, mood.substring(4,mood.length()) + "_" + StringUtils.LEFT_OR_RIGHT);
			
			System.out.println(mood.substring(4,mood.length()) + " Main : " + listOfAmygdala.size());
			System.out.println(mood.substring(4,mood.length()) + " Left : " + listOfLeftAmygdala.size());
			System.out.println(mood.substring(4,mood.length()) + " Right : " + listOfRightAmygdala.size());
			System.out.println(mood.substring(4,mood.length()) + " L or R : " + listOfLeftOrRightAmygdala.size());
		}
		
	}

	/**
	 * This method checks the patterns of moods in the given sentences
	 * @param sentences : List of sentences that may contain moods
	 * @param mood : Regular expression of a mood
	 * @return : List<String> : sentences containing the mood
	 * */
	private List<String> retrieveSentencesWithMood(List<String> sentences, String mood) {
		Pattern p;
		Matcher matcher;
		List<String> sentencesWithMood = new ArrayList<>();
		for(String sentence:sentences){
			
			p = Pattern.compile(mood);
			matcher = p.matcher(sentence);
			
			if (matcher.find()){
				sentencesWithMood.add(sentence);
			}
		}
		return sentencesWithMood;
	}
	
}
