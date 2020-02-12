package de.uni.koeln.odrajavascraper.services.scraper;

import de.uni.koeln.odrajavascraper.entities.Article;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OkoScraper extends Scraper {

    @Override
    public List<String> getNewsUrlList() {
        try {
            URL feedUrl = new URL("https://oko.press/feed");
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

    @Override
    public Article scrape(String url) throws IOException, FeedException {

        Document doc = openURL(url);

        // HEADLINE
        String headline = doc.body().getElementsByClass("line-1").text().toString();

        // TEXTBODY
        String textBody = doc.body().getElementsByClass("excerpt").text().toString()
                + doc.body().getElementsByClass("entry-content").text().toString();

        // AUTHOR
        String author = doc.getElementsByClass("meta-section__autor").text().toString();

        // DATE
        String creationDate = doc.getElementsByTag("time").first().text().toString();

        // TOPIC TODO
//		String topic = doc.getElementsByClass("subcategory-title").text().toString();

        Article article = new Article();

        article.setLink(url);
        article.setHeadline(headline);
        article.setSource("https://oko.press/");
        article.setSourceName("oko");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setCreationDate(creationDate);
        article.setAuthor(author);
        article.setLink(url);
//        article.setTopic(topic);

        return article;
    }

}