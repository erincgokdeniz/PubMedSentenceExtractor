package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import businessobjects.PatternObject;
import businessobjects.PubMedArticle;


public class FileUtils {

	private static final String publicationsPath = "./abstracts/";
	private static final String sentencesPath = "./output/";
	private static final String patternsPath = "./toolkit/patterns";
	private static final String moodsPath = "./toolkit/moods";
	private static final String fileExtension = ".txt";
	private static final String frequencyFileExtension = "_frequency.txt";
	public static final String abstractsPath = "./toolkit/pubmed_result.xml";
	
	/**
	 *  @param type : brain region term
	 *  @return List<PubMedObject> : List of abstracts as PubMedObjects
	 * This method is used to retrieve the list of PubMed abstracts 
	 * */
	public static List<PubMedArticle> getFiles(){
			
		List<PubMedArticle> pubMedList = new ArrayList<PubMedArticle>();
		
    	try {
			File folder = new File(publicationsPath);
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			        
			    	String fileName = listOfFiles[i].getName();
			    	 
			    	PubMedArticle pubMed = new PubMedArticle();
			    	pubMed.setPmid(fileName.replace(fileExtension, ""));
			    	
			    	Path path = FileSystems.getDefault().getPath(publicationsPath, fileName);
			        String abstractText = new String (Files.readAllBytes(path), StandardCharsets.UTF_8 );
			        pubMed.setAbstractText(abstractText);
									
					pubMedList.add(pubMed);
			      } 
			    }
		} catch(IOException ie){
			ie.printStackTrace();
		}	
	
    	return pubMedList;
	}
	
	/**
	 * @param sentences : List of sentences containing a brain region term
	 * @param type : brain region term
	 * This method is used to write each sentence that contains a brain region to a file
	 * */
	public static void writeSentencesToFile(List<String> sentences, String type){
		File outputFile;
		BufferedWriter writer = null;

			try {
				
				outputFile = new File(sentencesPath + type + fileExtension);
				writer = new BufferedWriter(new FileWriter(outputFile, false));
				
				
				for(String line : sentences){
	            	
					writer.append(line + "\n\n");
	        		
			    }

				writer.close();
				
			} catch (IOException ie){
				ie.printStackTrace();
			} 
		
	}
	
	/**
	 * @param frequencyMap : map of terms and their number of occurrences
	 * @param type : brain region term
	 * This method is used to write frequency of the terms for a given brain region 
	 * */
	public static void writeFrequenciesToFile(Map<String, Integer> frequencyMap, String type){
		File outputFile;
		BufferedWriter writer = null;

			try {
				
				outputFile = new File(sentencesPath + type + frequencyFileExtension);
				writer = new BufferedWriter(new FileWriter(outputFile, false));
				
				
				for(String key : frequencyMap.keySet()){
	            
					writer.append(key + "|" + frequencyMap.get(key) + "\n");	
					
			    }

				writer.close();
				
			} catch (IOException ie){
				ie.printStackTrace();
			} 
		
	}
	
	public static List<PatternObject> getListOfPatterns(){
		
		List<PatternObject> patternList = new ArrayList<PatternObject>();
		
		File inputFile;
		String item;
		PatternObject pattern;
		
		try {
			inputFile = new File(patternsPath);
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			while ((item = reader.readLine()) != null){
				
				String[] patternLine = item.split(";");
				
				pattern = new PatternObject();
				
				pattern.setType(patternLine[0]);
				pattern.setPatternText(patternLine[1]);
				
				patternList.add(pattern);
			}
			
			reader.close();
			
		} catch (IOException ie){
			ie.printStackTrace();
		}
		
		return patternList;
	
}

public static List<String> getListOfMoods(){
		
		List<String> moodList = new ArrayList<String>();
		
		File inputFile;
		String item;
		
		try {
			inputFile = new File(moodsPath);
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			while ((item = reader.readLine()) != null){
				moodList.add(item);
			}
			
			reader.close();
			
		} catch (IOException ie){
			ie.printStackTrace();
		}
		
		return moodList;
	
	}
	
}

