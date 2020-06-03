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


//TODO fix scraper. Website thinks AbBlock is on while scraping


@Service
public class WyborczaScraper extends Scraper{
    @Override
    public List<String> getNewsUrlList() throws IOException, FeedException {
        try {
            Document doc = openURL("https://wyborcza.pl/0,0.html");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("newest-body-list-article-header")) {
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

        return null;
    }
}
