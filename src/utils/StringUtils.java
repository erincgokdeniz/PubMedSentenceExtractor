package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

	public static final String MAIN = "main";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String LEFT_OR_RIGHT = "left_or_right";
	
	private static final List<String> blackList = 
			 new ArrayList<String>(Arrays.asList("the", "an", "and", "or", "of", "is", "are", "in", "on", "with", "that"
					 , "by", "for", "as", "we", "to", "-rrb-", "-lrb-", "was", "were", "from", "these", "this",
					 "not", "but", "be", "at", "also", "into", "may", "have", "has", "than", "been", "such",
					 "other", "it", "more", "when", "its", "all", "there"));
	
	/**
	 *  @param key: terms coming from the sentences
	 * This method is used to check whether the term is in the blacklisted terms
	 * */
	public static boolean isInBlackList(String key){
		
		if (blackList.contains(key)){
			return true;
		}
		
		return false;
	}
}
