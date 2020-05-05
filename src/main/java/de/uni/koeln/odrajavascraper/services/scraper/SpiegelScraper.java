package de.uni.koeln.odrajavascraper.services.scraper;


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
        Elements headlineTag = doc.body().getElementsByTag("h2");
        String headline = headlineTag.get(0).getElementsByTag("span").get(0).text() + " - " + headlineTag.get(0).getElementsByTag("span").get(1).text(); // falls keine headline vorhanden ist und nur der Bidnestrich da steht
        if (headline.contains("Spiegel Plus") || headline.contains("Die Bilder des Tages")) { // falls eine PayWall existiert wird kein Artikel gespeichert
            return null;
        }

        //TEXTBODY
        String textBody ="";
        Elements textElements = doc.body().getElementsByClass("clearfix lg:pt-32 md:pt-32 sm:pt-24 md:pb-48 lg:pb-48 sm:pb-32");
        if(textElements != null){
            textBody = textElements.text();
        } else {
            textBody = doc.body().getElementsByTag("p").text();
        }

        textBody = textBody.replaceAll(" Icon: Der Spiegel","");
        textBody = textBody.replaceAll(" Melden Sie sich an und diskutieren Sie mit","");
        if(textBody.length() < 10) return null;

        //AUTHOR
        String author = doc.body().getElementsByClass("text-black font-bold border-b border-shade-light hover:border-black").text();
        author = author.replaceAll("Von", "").replaceAll(" und ", ", "); // Autoren String wird gereinigt

        //TOPIC
        Elements topicContainer = doc.body().getElementsByClass("relative mr-auto ml-4 text-white overflow-hidden pr-16 font-sans text-3xl ml-6 sm:overflow-x-auto sm:text-l sm:leading-normal sm:ml-4 tracking-wide whitespace-no-wrap");
        String topic = topicContainer.size() > 0 ? topicContainer.get(0).text() : "";
        if(topic.equals("Video")){
            return null;
        }

        //CREATIONDATE
        String creationDate = doc.body().getElementsByTag("time").text();

        creationDate = creationDate.replaceAll("(?<=2020).*$","");
        creationDate = creationDate.replaceAll("Montag|Dienstag|Mittwoch|Donnerstag|Freitag|Samstag|Sonntag|,|\\s", "");


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
//                return links;
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
