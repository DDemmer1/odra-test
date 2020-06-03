package de.uni.koeln.odrajavascraper.services.scraper;

import com.rometools.rome.io.FeedException;
import de.uni.koeln.odrajavascraper.entities.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GazetaScraper extends Scraper {

    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {

        try {
            Document doc = openURL("https://wiadomosci.gazeta.pl/wiadomosci/0,0.html", Charset.availableCharsets().get("ISO-8859-2"));
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("entry")) {
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

        Document doc = openURL(url, Charset.availableCharsets().get("ISO-8859-2"));


//        HEADLINE
        String headline ="";
        try {
            headline = doc.body().getElementById("article_title").text();
        } catch (Exception e) {

        }


//        AUTHOR
        String author ="";
        try {
            author = doc.body().getElementsByClass("article_author").text();
        } catch (Exception e) {}

//        TEXT BODY
        String textBody ="";
        try {
            textBody = doc.body().getElementsByClass("art_paragraph").text();
        } catch (Exception e) {}

//        Topic
        String topic ="";
        try {
            topic = doc.body().select("li[class='active']").text();
        } catch (Exception e) {}

//        Creation Date
        String creationDate ="";
        try {
            creationDate = doc.body().getElementsByClass("article_date").text();
        } catch (Exception e) {}

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://wiadomosci.gazeta.pl");
        article.setSourceName("gazeta");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);
        article.setCreationDate(creationDate);

        return article;
    }
}
