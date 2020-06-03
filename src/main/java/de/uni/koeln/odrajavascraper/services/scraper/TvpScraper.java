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
public class TvpScraper extends Scraper{


    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {
        try {
            Document doc = openURL("https://www.tvp.info/");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("news__box")) {
                try {
                    if(e.getElementsByTag("a").first().attr("href").startsWith("https")) continue;
                    links.add("https://www.tvp.info" + e.getElementsByTag("a").first().attr("href"));
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
            headline = doc.body().getElementsByTag("h1").first().text();
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
            textBody = doc.body().select("section[class='am-article article']").first().text();
        } catch (Exception e) {}

//        Topic
        String topic ="";
        try {
            topic = doc.body().select("a[class='nav__reference--active']").first().text();
        } catch (Exception e) {}

//        Creation Date
        String creationDate ="";
        try {
            creationDate = doc.body().select("span[class='date']").first().text();
        } catch (Exception e) {}

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://tvp.info/");
        article.setSourceName("tvp");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);
        article.setCreationDate(creationDate);

        return article;

    }
}
