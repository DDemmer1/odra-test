package de.uni.koeln.odrajavascraper.entities;


import java.util.Date;

/**
 * Einfache Datenklasse um die Artikel zu speichern
 */
public class Article {
    private String headline;
    private String textBody;
    private String source;
    private String source_name;
    private String author;
    private String topic;
    private String link;
    private Date crawl_date;
    private String creation_date;


    public Article(){

    }


    public Article(String headline, String textBody, String source, String source_name, String author, String topic, String link, Date crawl_date, String creation_date) {
        this.headline = headline;
        this.textBody = textBody;
        this.source = source;
        this.source_name = source_name;
        this.author = author;
        this.topic = topic;
        this.link = link;
        this.crawl_date = crawl_date;
        this.creation_date = creation_date;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCrawl_date() {
        return crawl_date;
    }

    public void setCrawl_date(Date crawl_date) {
        this.crawl_date = crawl_date;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
