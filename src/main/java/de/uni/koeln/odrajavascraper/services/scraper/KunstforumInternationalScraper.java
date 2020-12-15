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
public class KunstforumInternationalScraper extends Scraper{
    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {
        try {
            Document doc = openURL("https://www.kunstforum.de/");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementById("newsCarousel").select("a[href]")) {
                if(e.attr("href").contains("/nachrichten/")){
                    links.add("https://www.kunstforum.de" + e.attr("href"));

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
        Article article = new Article();

        //Source
        article.setSource("https://www.kunstforum.de");

        //Source Name
        article.setSourceName("kunstforum");

        //Crawl Date
        article.setCrawlDate(new Date());

        //Link
        article.setLink(url);


        //Headline
        String headline = doc.body().getElementsByClass("article_h1").text();
        article.setHeadline(headline);

        //Text
        String text = doc.body().getElementsByClass("maincontent").get(0).getElementsByTag("p").get(3).text();
        article.setTextBody(text);

        //Creation date
        String creationDate = doc.body().getElementsByClass("nachricht-meta").text();
        article.setCreationDate(creationDate.replaceAll("\\·[^·]*$", ""));

//        //Author
        article.setAuthor("");



        return article;
    }
}
