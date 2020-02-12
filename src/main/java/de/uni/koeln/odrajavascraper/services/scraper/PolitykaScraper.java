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
public class PolitykaScraper extends Scraper {


    @Override
    public List<String> getNewsUrlList() {

        try {
            Document doc = openURL("https://www.polityka.pl/TygodnikPolityka");
            List<String> links = new ArrayList<>();

            for (Element e : doc.body().getElementsByTag("article").select("a[href]")) {
                //nur wenn Link auch auf polityka fuehrt und mit .read endet
                if(e.attr("href").startsWith("https://www.polityka.pl") && e.attr("href").endsWith(".read") ) {
                    links.add(e.attr("href"));
                }
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

        String headline = doc.body().getElementsByClass("cg_article_internet_title").text();
        String textBody = doc.body().getElementsByClass("cg_article_content").text();
        String author = doc.body().getElementsByClass("cg_article_author_name").get(0).text();
        String creationDate = doc.body().getElementsByClass("cg_article_date cg_date").text();
        String topic = doc.body().getElementsByClass("cg_article_section").text();

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://www.polityka.pl");
        article.setSourceName("polityka");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setCreationDate(creationDate);
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);

        return article;
    }

}