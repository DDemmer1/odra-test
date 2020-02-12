package de.uni.koeln.odrajavascraper.controller;


import com.rometools.rome.io.FeedException;
import de.uni.koeln.odrajavascraper.entities.Article;
import de.uni.koeln.odrajavascraper.services.scraper.*;
import de.uni.koeln.odrajavascraper.services.util.ActuatorService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Kontrolliert welche Daten bei welchem Pfad aufruf ausgegeben werden
 */
@RestController
public class MainController {

    @Autowired
    ActuatorService actuatorService;

    @Autowired
    EpochTimesScraper epochTimesScraper;

    @Autowired
    FazScraper fazScraper;

    @Autowired
    OkoScraper okoScraper;

    @Autowired
    PikioScraper pikioScraper;

    @Autowired
    PolitykaScraper politykaScraper;

    @Autowired
    PrzekrojScraper przekrojScraper;

    @Autowired
    SpiegelScraper spiegelScraper;

    @Autowired
    ZeitScraper zeitScraper;


    private List<Article> scrape(Scraper scraper) {
        try {
            List<Article> articleList = new ArrayList<>();
            for (String link : scraper.getNewsUrlList()) {
                Article article = scraper.scrape(link);
                if (article != null) {
                    articleList.add(article);
                }
            }
            return articleList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }


    @GetMapping(value = "/epoch")
    public List<Article> epoch() throws IOException {
        return scrape(epochTimesScraper);
    }

    @GetMapping(value = "/faz")
    public List<Article> faz() throws IOException {
        return scrape(fazScraper);
    }

    @GetMapping(value = "/oko")
    public List<Article> oko() throws IOException {
        return scrape(okoScraper);
    }

    @GetMapping(value = "/pikio")
    public List<Article> pikio() throws IOException {
        return scrape(pikioScraper);
    }

    @GetMapping(value = "/polityka")
    public List<Article> polityka() throws IOException {
        return scrape(politykaScraper);
    }

    @GetMapping(value = "/przekroy")
    public List<Article> przekroy() throws IOException {
        return scrape(przekrojScraper);
    }

    @GetMapping(value = "/spiegel")
    public List<Article> spiegel() throws IOException {
        return scrape(spiegelScraper);
    }

    @GetMapping(value = "/zeit")
    public List<Article> zeit() throws IOException {
        return scrape(zeitScraper);
    }


    @GetMapping(value = "/")
    public List<String> mappings() throws IOException, JSONException {
        return actuatorService.getMappings();
    }

}
