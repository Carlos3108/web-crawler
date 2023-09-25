package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
            System.out.println("Hello world!");
        String url = "https://carlos3108.github.io/portfolio/";
        crawl(1, url, new ArrayList<>());
    }

    private static void crawl(int level, String url, ArrayList<String> visited) {
        if (level <= 1) {
            Document doc = request(url, visited);
            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    if (!visited.contains(next_link)) {
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }

    private static Document request(String url, ArrayList<String> v) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if (con.response().statusCode() == 200) {
                logger.info("Link " + url);
                logger.info(doc.title());
                v.add(url);
                return doc;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}