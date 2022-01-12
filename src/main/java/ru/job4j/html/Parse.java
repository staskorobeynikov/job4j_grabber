package ru.job4j.html;

import ru.job4j.model.Post;

import java.io.IOException;
import java.util.List;

public interface Parse {
    List<Post> list(String link) throws IOException;

    Post detail(String link) throws IOException;
}
