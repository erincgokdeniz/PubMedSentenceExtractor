package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import businessobjects.ArticleSet;
import businessobjects.PubMedArticle;

public class SAXHandler extends DefaultHandler{

	Map<String, ArticleSet> documentMap = new HashMap<String, ArticleSet>();
	List<PubMedArticle> articles = new ArrayList<PubMedArticle>();
	PubMedArticle pubmedArticle = null;

    boolean bArticle	= false;
    boolean bAbstract	= false;
    boolean bPmid		= false;
    boolean bTitle		= false;
	String abstractTextData = null;
    
	private Stack<String> elementStack = new Stack<String>();
    private Stack<Object> objectStack  = new Stack<Object>();
	
    @Override
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {

		this.elementStack.push(qName);
		
		if (PUBMED_ARTICLE.equalsIgnoreCase(qName)) {
		
			pubmedArticle = new PubMedArticle();
			this.objectStack.push(pubmedArticle);
			bArticle = true;
			
		} else if (ARTICLE_ID.equalsIgnoreCase(qName)) {
			if (attributes.getValue("IdType").equalsIgnoreCase("pubmed")){
				bPmid = true;	
			}		
		} else if (ARTICLE_TITLE.equalsIgnoreCase(qName)) {		
			bTitle = true;	
		} else if (ABSTRACT_TEXT.equalsIgnoreCase(qName)) {	
			bAbstract = true;
		}
	}
	
    @Override
	public void endElement(String uri, String localName,
			String qName) throws SAXException {
		
		this.elementStack.pop();
 
            if(PUBMED_ARTICLE.equals(qName)){
            	
            	articles.add(pubmedArticle);

            } else if (ABSTRACT_TEXT.equalsIgnoreCase(qName)) {	
    			bAbstract = false;
    		}
	}
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
 
        if (bArticle) {
            bArticle = false;
        } else if (bTitle) {
            pubmedArticle.setTitle(new String(ch, start, length));
            bTitle = false;
        } else if (bPmid) {
        	pubmedArticle.setPmid(new String(ch, start, length));
        	bPmid = false;
        } else if (bAbstract) {
        	abstractTextData = pubmedArticle.getAbstractText();
        	if (abstractTextData != null){
        		pubmedArticle.setAbstractText(abstractTextData + " "+ new String(ch, start, length));
        	} else {
        		pubmedArticle.setAbstractText(new String(ch, start, length));	
        	}

        }
    }
	
	private final String PUBMED_ARTICLE	= "PubmedArticle";
	private final String ABSTRACT_TEXT	= "AbstractText";
	private final String ARTICLE_ID		= "ArticleId";
	private final String ARTICLE_TITLE	= "ArticleTitle";

	
	public List<PubMedArticle> getArticles(){
		return articles;
	}
	
}
