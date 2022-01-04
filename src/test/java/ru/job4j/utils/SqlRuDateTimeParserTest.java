package ru.job4j.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SqlRuDateTimeParserTest {
    @Test
    public void whenFullDateWithTime() {
        String parse = "25 июн 18, 21:56";
        LocalDateTime expected = LocalDateTime.of(2018, 6, 25, 21, 56);
        LocalDateTime result = new SqlRuDateTimeParser().parse(parse);
        assertThat(result, is(expected));
    }

    @Test
    public void whenTodayWithTime() {
        String parse = "сегодня, 2:30";
        LocalDateTime expected = LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 30));
        LocalDateTime result = new SqlRuDateTimeParser().parse(parse);
        assertThat(result, is(expected));
    }

    @Test
    public void whenYesterdayWithTime() {
        String parse = "вчера, 19:23";
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(19, 23));
        LocalDateTime result = new SqlRuDateTimeParser().parse(parse);
        assertThat(result, is(expected));
    }
}