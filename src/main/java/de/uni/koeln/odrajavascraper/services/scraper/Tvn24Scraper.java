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
public class Tvn24Scraper extends Scraper{
    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {
        try {
            Document doc = openURL("https://tvn24.pl/najnowsze/");
            List<String> links = new ArrayList<>();
            Element infiniteScroll = doc.body().getElementsByClass("infinite-scroll").first();

            for (Element e : infiniteScroll.getElementsByClass("link__content")) {
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
            headline = doc.body().getElementsByClass("heading").first().text();
        } catch (Exception e) {

        }


//        AUTHOR
        String author ="";
        try {
            author = doc.body().select("dd[class='article-top-bar__meta-item']").first().text();
        } catch (Exception e) {}

//        TEXT BODY
        String textBody ="";
        try {
            textBody = doc.body().getElementsByClass("article-element").text();
        } catch (Exception e) {}

//        Topic
        String topic ="";
        try {
            topic = url.split("/")[3];
        } catch (Exception e) {}

//        Creation Date
        String creationDate ="";
        try {
            creationDate = doc.body().getElementsByTag("time").first().attr("datetime");
        } catch (Exception e) {}

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://tvn24.pl/");
        article.setSourceName("tvn24");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);
        article.setCreationDate(creationDate);

        return article;

    }
}
