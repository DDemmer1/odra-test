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
public class PrzekrojScraper extends Scraper {

	public PrzekrojScraper() {
		
	}

	@Override
	public List<String> getNewsUrlList() throws IOException, FeedException {
		
		
		try {
            Document doc = openURL("https://przekroj.pl");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("tile__link")) {
            	String linkText = e.attr("href");
            	if(!(linkText.contains("regulamin-newslettera") || linkText.contains("media") 
            			|| linkText.contains("kwartalnik") || linkText.contains("rozrywka") || linkText.equals("/en")
            			|| linkText.contains("rysunki") || linkText.equals("")
            			)) {
                    links.add("https://przekroj.pl" + linkText);
                    System.out.println("https://przekroj.pl" + e.attr("href"));
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
		 
		 String headline = doc.body().getElementsByClass("tophead__title").text();
		 
		 //TEXTBODY
		 
		 String textBody = doc.body().getElementsByClass("article__text").text();
				
		 
		 //AUTHOR
		 
		 String author = doc.body().getElementsByClass("hero__author").text();
		 
		 //TOPIC
		 
		 String topic = doc.body().getElementsByClass("hero__category").text();
		 
		 //CREATIONDATE
		 
		 String creationDate = doc.body().getElementsByAttribute("datetime").attr("datetime");
		
		   Article article = new Article();
	        article.setHeadline(headline);
	        article.setSource("https://przekroj.pl");
	        article.setSourceName("przekroj");
	        article.setTextBody(textBody);
	        article.setCrawlDate(new Date());
	        article.setCreationDate(creationDate);
	        article.setAuthor(author);
	        article.setLink(url);
	        article.setTopic(topic); 
		
		return article;
	}

}
