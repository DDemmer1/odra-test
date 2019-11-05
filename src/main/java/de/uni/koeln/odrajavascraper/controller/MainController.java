package de.uni.koeln.odrajavascraper.controller;


import de.uni.koeln.odrajavascraper.entities.Article;
import de.uni.koeln.odrajavascraper.scraper.PikioScraper;
import de.uni.koeln.odrajavascraper.scraper.SpiegelScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Kontrolliert welche Daten bei welchem Pfad aufruf ausgegeben werden
 */
@RestController
public class MainController {

    @Autowired
    PikioScraper pikioScraper;

    @Autowired
    SpiegelScraper spiegelScraper;

    @GetMapping(value = "/pikio")
    public List<Article> index() throws IOException {

        List<Article> articleList = new ArrayList<>();

        for (String link : pikioScraper.getNewsUrlList()) {
            articleList.add(pikioScraper.scrape(link));
        }

        return articleList;

    }


    @GetMapping(value = "/spiegel")
    public List<Article> spiegel() throws IOException {

        List<Article> articleList = new ArrayList<>();

        for (String link : spiegelScraper.getNewsUrlList()) {
            articleList.add(spiegelScraper.scrape(link));
        }

        return articleList;

    }


    @GetMapping(value = "/")
    public String empty() throws IOException {


        return "<h1>No route found. Please go to /spiegel or /pikio</h1>";

    }


}
