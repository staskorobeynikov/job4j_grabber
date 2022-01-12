package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.model.Post;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final SqlRuDateTimeParser parser;

    public SqlRuParse(SqlRuDateTimeParser parser) {
        this.parser = parser;
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> rsl = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect(link + "/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element child = td.parent().child(1);
                if (
                        child.text().toLowerCase().contains("java")
                        && !child.text().toLowerCase().contains("javascript")
                ) {
                    rsl.add(detail(child.getAllElements().attr("href")));
                }
            }
        }
        return rsl;
    }

    public Post detail(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements description = doc.select(".msgBody");
        Elements date = doc.select(".msgFooter");
        Elements title = doc.select(".messageHeader");
        return Post.of(
                0,
                parseElement(title),
                url,
                description.get(1).text(),
                parser.parse(parseElement(date))
        );
    }

    private String parseElement(Elements rows) {
        return rows.get(0).text().split(" \\[", 2)[0];
    }
}
