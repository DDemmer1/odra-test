package de.uni.koeln.odrajavascraper.services.scraper;

import de.uni.koeln.odrajavascraper.entities.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FazScraper extends Scraper{


    @Override
    public List<String> getNewsUrlList() {

        try {
            Document doc = openURL("https://www.faz.net/aktuell/");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("tsr-Base_ContentLink").select("a[href]")) {
                if(e.attr("href").startsWith("https://www.faz.net"))
                    links.add(e.attr("href"));
            }
            return links;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    /**
     * Extracts information of a pikio.pl news article into a @{@link Article} object
     *
     * @param url The URL of the news article
     * @return An Article Object with the information from the HTML page according to the URL
     * @throws IOException
     */
    public Article scrape(String url) throws IOException {

        Document doc = openURL(url);

        String headline = doc.body().getElementsByClass("atc-HeadlineText").text();
        String textBody = doc.body().getElementsByClass("atc-TextParagraph").text();
        String author = doc.body().getElementsByClass("atc-MetaAuthor").text();
        String topic = doc.body().getElementsByClass("gh-LogoStage_Caption").text();

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://www.faz.net/aktuell/");
        article.setSource_name("faz");
        article.setTextBody(textBody);
        article.setCrawl_date(new Date());
        article.setCreation_date(null);
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);

        return article;
    }

}
