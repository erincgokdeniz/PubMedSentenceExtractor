package service;


import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import businessobjects.PubMedArticle;
import utils.FileUtils;

public class XMLService {

	private List<PubMedArticle> articles;
	
	public void init(){
		try{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler handler   = new SAXHandler();
	        saxParser.parse(FileUtils.abstractsPath, handler);	
	        
	        articles = handler.getArticles();

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public List<PubMedArticle> getArticles(){
		return articles;
	}
}
