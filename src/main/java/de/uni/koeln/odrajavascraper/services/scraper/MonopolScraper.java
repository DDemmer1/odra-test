package de.uni.koeln.odrajavascraper.services.scraper;

import com.rometools.rome.io.FeedException;
import de.uni.koeln.odrajavascraper.entities.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MonopolScraper extends Scraper {
    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {
        try {
            Document doc = openURL("https://www.monopol-magazin.de/");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("view-newsticker").select("a[href]")) {
                links.add("https://www.monopol-magazin.de" + e.attr("href"));
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
        Article article = new Article();

        //Source
        article.setSource("https://www.monopol-magazin.de");

        //Source Name
        article.setSourceName("monopol");

        //Crawl Date
        article.setCrawlDate(new Date());

        //Link
        article.setLink(url);

        //Headline
        String headline = doc.body().getElementsByClass("article__title").text();
        article.setHeadline(headline);

        //Text
        String text = doc.body().getElementsByClass("paragraph paragraph--type--text paragraph--view-mode--default").text();
        article.setTextBody(text);

        //Creation date
        String creationDate = doc.body().getElementsByClass("article__meta").get(0).getElementsByTag("p").get(1).text();
        article.setCreationDate(creationDate.replace("Datum ", ""));

        //Topic
        String topic = doc.body().getElementsByClass("btn btn-sm btn-outline-primary rounded-pill").text();
        article.setTopic(topic);

        //Author
        String author = doc.body().getElementsByClass("article__meta").get(0).getElementsByTag("p").get(0).text();
        article.setAuthor(author.replaceAll("Text ", ""));


        return article;
    }
}
