package de.uni.koeln.odrajavascraper.services.scraper;


import com.rometools.rome.io.FeedException;
import de.uni.koeln.odrajavascraper.entities.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PolskieradioScraper extends Scraper{
    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {

        try {
            Document doc = openURL("https://www.polskieradio.pl/");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("mosaic-tile")) {
                try {
                    if(e.attr("href").isEmpty()) continue;
                    if(!e.attr("href").startsWith("https")){
                        links.add("https://www.polskieradio24.pl" + e.attr("href"));
                    } else {
                        links.add(e.attr("href"));
                    }
                } catch (NullPointerException npe) {
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

//        HEADLINE
        String headline ="";
        try {
            headline = doc.body().select("h1[class='title']").text();
        } catch (Exception e) {}


//        AUTHOR
        String author ="";
        try {
            author = doc.body().select("dd[class='article-top-bar__meta-item']").first().text();
        } catch (Exception e) {}

//        TEXT BODY
        String textBody ="";
        try {
            textBody = doc.body().select("div[class='content']").first().getElementsByTag("p").text();
        } catch (Exception e) {}

//        Topic
        String topic ="";
        try {
            topic = doc.body().select("a[class='sub current']").text();
        } catch (Exception e) {}

//        Creation Date
        String creationDate ="";
        try {
            creationDate = doc.body().select("span[id='datetime2']").text();
        } catch (Exception e) {}

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://www.polskieradio.pl/");
        article.setSourceName("polskieradio");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);
        article.setCreationDate(creationDate);

        return article;
    }
}
