package de.uni.koeln.odrajavascraper.services.scraper;

import com.rometools.rome.io.FeedException;
import de.uni.koeln.odrajavascraper.entities.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public abstract class Scraper {

    /**
     * Opens the URL and returns a parsed JSoup Document
     *
     * @param urlStr the url to open
     * @return A parsed JSoup Document with the content of the HTML page
     * @throws IOException
     */
    public Document openURL(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        con.addRequestProperty("Cookie", "zonconsent=2020-05-07T07:11:50.526Z");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        String siteContent = content.toString();
        return Jsoup.parse(siteContent);
    }


    /**
     * Scrapes all URL's of the current news articles
     * @return A list of urls
     */
    public abstract List<String> getNewsUrlList() throws IOException, FeedException;


    /**
     * Extracts information of a news article into a @{@link Article} object
     *
     * @param url The URL of the news article
     * @return An Article Object with the information from the HTML page according to the URL
     * @throws IOException
     */
    public abstract Article scrape(String url) throws IOException, FeedException;


}
