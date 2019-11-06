package de.uni.koeln.odrajavascraper.scraper;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import de.uni.koeln.odrajavascraper.entities.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SpiegelScraper extends Scraper {

    @Override
    public Article scrape(String url) throws IOException {

        Document doc = openURL(url);

        //HEADLINE
        String headline = doc.body().getElementsByClass("headline-intro").text() + " - " + doc.body().getElementsByClass("headline").text();
        headline = headline.equals(" - ") ? "" : headline; // falls keine headline vorhanden ist und nur der Bidnestrich da steht
        if (headline.contains("Exklusiv für Abonnenten")) { // falls eine PayWall existiert wird kein Artikel gespeichert
            return null;
        }

        //TEXTBODY
        String textBody = doc.body().getElementsByTag("p").text();

        //AUTHOR
        String author = doc.body().getElementsByClass("author").text();
        author = author.replaceAll("Von", "").replaceAll(" und ", ", "); // Autoren String wird gereinigt

        //TOPIC
        Elements topicContainer = doc.body().getElementsByClass("current-channel-name");
        String topic = topicContainer.size() > 0 ? topicContainer.get(0).text() : "";
        if(topic.equals("Video")){
            return null;
        }

        //CREATIONDATE
        String creationDate = doc.body().getElementsByClass("article-function-date").text();


        Article article = new Article();
        article.setLink(url);
        article.setHeadline(headline);
        article.setSource("https://www.spiegel.de");
        article.setSourceName("spiegel");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setCreationDate(creationDate);
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);


        return article;
    }


    @Override
    public List<String> getNewsUrlList() {
        try {
            URL feedUrl = new URL("https://www.spiegel.de/schlagzeilen/tops/index.rss");
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            List<SyndEntry> entries = feed.getEntries();
            List<String> links = new ArrayList<>();
            for (SyndEntry entry : entries) {
                links.add(entry.getLink());
            }
            return links;

        } catch (FeedException fe) {
            fe.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
