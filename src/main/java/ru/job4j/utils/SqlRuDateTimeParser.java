package ru.job4j.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, Integer> MONTHS = Map.ofEntries(
            Map.entry("янв", 1),
            Map.entry("фев", 2),
            Map.entry("мар", 3),
            Map.entry("апр", 4),
            Map.entry("май", 5),
            Map.entry("июн", 6),
            Map.entry("июл", 7),
            Map.entry("авг", 8),
            Map.entry("сен", 9),
            Map.entry("окт", 10),
            Map.entry("ноя", 11),
            Map.entry("дек", 12)
    );

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime result;
        String[] dateTime = parse.split(", ");
        String[] elementsDate = dateTime[0].split(" ");
        String[] elementsTime = dateTime[1].split(":");
        if (elementsDate.length == 3) {
            result = getDateTime(
                    LocalDate.of(
                            Integer.parseInt(elementsDate[2]) + 2000,
                            MONTHS.get(elementsDate[1]),
                            Integer.parseInt(elementsDate[0])
                    ),
                    elementsTime
            );
        } else if ("сегодня".equals(dateTime[0])) {
            result = getDateTime(LocalDate.now(), elementsTime);
        } else {
            result = getDateTime(LocalDate.now().minusDays(1), elementsTime);
        }
        return result;
    }

    private LocalDateTime getDateTime(LocalDate date, String[] elementsTime) {
        return LocalDateTime.of(
                date,
                LocalTime.of(
                        Integer.parseInt(elementsTime[0]),
                        Integer.parseInt(elementsTime[1])
                )
        );
    }
}
