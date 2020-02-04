package de.uni.koeln.odrajavascraper.services.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.rometools.rome.io.FeedException;

import de.uni.koeln.odrajavascraper.entities.Article;

@Service
public class EpochTimesScraper extends Scraper {

	public EpochTimesScraper() {

	}

	@Override
	public List<String> getNewsUrlList() throws IOException, FeedException {
			try {
	            Document doc = openURL("https://www.epochtimes.de/");
	            List<String> links = new ArrayList<>();
	            for (Element e : doc.body().getElementsByClass("article")) {
	            	System.out.println(e);
	            	String linkText = e.getElementsByTag("a").get(0).attr("href");
	            	if(!(linkText.contains("video"))) {
	                    links.add(linkText);
	                    System.out.println(linkText);
	            	}
	            }
	            return links;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			return null;
		}

	@Override
	public Article scrape(String url) throws IOException, FeedException {
		
		Document doc = openURL(url);
		 
		 //HEADLINE
		 
		 String headline = doc.body().getElementsByTag("h2").get(0).text();
		 
		 //TEXTBODY
		 
		 String textBody = doc.body().getElementsByTag("p").text();
				
		 
		 //AUTHOR
		 
		 String author = doc.body().getElementsByClass("author").text();
		 
		 //TOPIC
		 
		 String topic = doc.body().getElementsByClass("rubrik").text();
		 
		// String topic = doc.body().getElementsByAttribute("category tag").attr("category tag");
		 
		 //CREATIONDATE
		 
		 String creationDate = doc.body().getElementsByClass("publish-date").text();
		
		   Article article = new Article();
	        article.setHeadline(headline);
	        article.setSource("https://www.epochtimes.de/");
	        article.setSource_name("epoch");
	        article.setTextBody(textBody);
	        article.setCrawl_date(new Date());
	        article.setCreation_date(creationDate);
	        article.setAuthor(author);
	        article.setLink(url);
	        article.setTopic(topic); 
		
		return article;
	}

}
