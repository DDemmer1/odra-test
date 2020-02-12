package de.uni.koeln.odrajavascraper.entities;


import java.util.Date;

/**
 * Einfache Datenklasse um die Artikel zu speichern
 */
public class Article {
    private String headline;
    private String textBody;
    private String source;
    private String sourceName;
    private String author;
    private String topic;
    private String link;
    private Date crawlDate;
    private String creationDate;


    public Article(){

    }


    public Article(String headline, String textBody, String source, String sourceName, String author, String topic, String link, Date crawlDate, String creationDate) {
        this.headline = headline;
        this.textBody = textBody;
        this.source = source;
        this.sourceName = sourceName;
        this.author = author;
        this.topic = topic;
        this.link = link;
        this.crawlDate = crawlDate;
        this.creationDate = creationDate;
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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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

    public Date getCrawlDate() {
        return crawlDate;
    }

    public void setCrawlDate(Date crawlDate) {
        this.crawlDate = crawlDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
