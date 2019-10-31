package odra.test.odratest;


import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {

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


}
