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
public class RzeczpospolitaScraper extends Scraper{
    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {
        try {
            Document doc = openURL("https://www.rp.pl/");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("teaser__parent")) {
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
        Document doc = openURL(url, Charset.availableCharsets().get("Windows-1250"));

//        HEADLINE
        String headline ="";
        try {
            headline = doc.body().getElementsByClass("article__title").text();
        } catch (Exception e) {}

//        AUTHOR
        String author ="";
        try {
            author = doc.body().getElementsByClass("article__author").text();
        } catch (Exception e) {}

//        TEXT BODY
        String textBody ="";
        try {
            textBody = doc.body().getElementsByClass("article__content").text();
        } catch (Exception e) {}

//        Topic
        String topic ="";
        try {
            topic = url.split("/")[3];
        } catch (Exception e) {}

//        Creation Date
        String creationDate ="";
        try {
            creationDate = doc.body().getElementsByClass("article__date").text().split(",")[0].replaceAll("Aktualizacja: ","");
        } catch (Exception e) {}

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://rp.pl/");
        article.setSourceName("rzeczpospolita");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);
        article.setCreationDate(creationDate);

        return article;
    }



}
