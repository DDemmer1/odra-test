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
public class GazetaprawnaScraper extends Scraper{


    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {
        try {
            Document doc = openURL("https://www.gazetaprawna.pl/");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().select("div[class='modul-ajax-content']").first().select("div[class='next-to-data']")) {
                try {
                    links.add(e.getElementsByTag("a").first().attr("href"));
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
            headline = doc.body().getElementsByTag("h1").get(1).text();
        } catch (Exception e) {

        }

//        AUTHOR
        String author ="";
        try {
            author = doc.body().select("span[class='name']").first().text();
        } catch (Exception e) {}

//        TEXT BODY
        String textBody ="";
        try {
            textBody = doc.body().select("div[class='articleBody']").first().text();
        } catch (Exception e) {}

//        Topic
        String topic ="";
        try {
            topic = url.split("/")[3];
        } catch (Exception e) {}

//        Creation Date
        String creationDate ="";
        try {
            creationDate = doc.body().getElementsByClass("date").first().text().split(",")[0];
        } catch (Exception e) {}

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://www.gazetaprawna.pl/");
        article.setSourceName("gazetaprawna");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);
        article.setCreationDate(creationDate);

        return article;
    }
}
