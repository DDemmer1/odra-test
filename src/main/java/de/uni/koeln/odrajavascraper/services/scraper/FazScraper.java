package de.uni.koeln.odrajavascraper.services.scraper;

import de.uni.koeln.odrajavascraper.entities.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

        //HEADLINE
        String headline = doc.body().getElementsByClass("atc-HeadlineText").text();
        String subHeading = doc.body().getElementsByClass("atc-HeadlineEmphasis").text();
        if(headline == null || headline == ""){
            headline = doc.body().select("h2[class='h1']").text();
        } else {
            headline = subHeading + " " +headline;
            headline = headline.replaceAll(":","-");
        }

        // TEXT BODY
        String textBody = doc.body().getElementsByClass("atc-TextParagraph").text();
        if(textBody.length() < 10 || headline.length() < 1){
            return null;
        }

        //AUTHOR
        String author = doc.body().getElementsByClass("atc-MetaAuthor").text();

        //TOPIC
        String topic = "";
        Elements topicElements = doc.body().getElementsByClass("gh-Hoverable-is-active");
        if(topicElements.size() > 0){
            topic = topicElements.get(0).getElementsByClass("gh-Ressort_LinkPlaceholder").text();
        }

        // CREATION DATE
        String creationDate ="";
        if(doc.body().getElementsByTag("time").size() > 0){
            creationDate = doc.body().getElementsByTag("time").get(0).attr("title");
            creationDate = creationDate.replaceAll("(?<=2020).*$","");
        }

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://www.faz.net/aktuell/");
        article.setSourceName("faz");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);
        article.setCreationDate(creationDate);

        return article;
    }

}
