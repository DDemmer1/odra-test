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
public class PikioScraper extends Scraper {


    @Override
    public List<String> getNewsUrlList() {

        try {
            Document doc = openURL("https://pikio.pl");
            List<String> links = new ArrayList<>();
            for (Element e : doc.body().getElementsByClass("news-item")) {
                links.add(e.getElementsByTag("a").attr("href"));
            }
            return links;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Article scrape(String url) throws IOException {

        Document doc = openURL(url);

        //HEADLINE
        Elements headings = doc.body().getElementsByClass("page-heading");
        String headline = headings.size() > 0 ? headings.get(1).text() : "";

        //TEXTBODY
        Elements articleContainers = doc.body().getElementsByClass("article-container");
        String textBody = articleContainers.size() > 0 ? articleContainers.get(0).text() : "";

        //AUTHOR
        Elements articleAuthors = doc.body().getElementsByClass("article-author");
        String author = articleAuthors.size() > 0 ? articleAuthors.get(0).getElementsByTag("a").text() : "";

        //TOPIC
        Elements breadcrumbs = doc.body().getElementsByClass("breadcrumbs");
        Elements aTags = breadcrumbs.size() > 0 ? breadcrumbs.get(0).getElementsByTag("a") : null;
        String topic = aTags == null || aTags.size() > 0 ? aTags.get(1).text() : "";

        //CREATION DATE
        Elements times = doc.body().getElementsByTag("time");
        String creationDate = times.size() > 0 ? times.get(0).attr("datetime") : "";

        Article article = new Article();
        article.setHeadline(headline);
        article.setSource("https://pikio.pl");
        article.setSource_name("pikio");
        article.setTextBody(textBody);
        article.setCrawl_date(new Date());
        article.setCreation_date(creationDate);
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);

        return article;
    }

}
