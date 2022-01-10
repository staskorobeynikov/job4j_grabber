package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.model.Post;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.IOException;

public class SqlRuParse {

    private final SqlRuDateTimeParser parser = new SqlRuDateTimeParser();

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

    public static void main(String[] args) throws IOException {
        Post post = new SqlRuParse().detail("https://www.sql.ru/forum/1341097/web-programmist-uroven-middle");
        System.out.println(post);
    }
}
