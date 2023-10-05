package ru.podgoretskaya.accounting.util;

import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

public class DayUtils {
    DayUtils() {
    }

    public static long countOfDayOff(PersonDTO p, Month month) {
        return p.getDayOff().stream()
                .filter(d -> month.equals(d.getDate().getMonth())).count();
    }

    public static long countOfVacations(PersonDTO p, Month month) {
        return p.getVacations().stream()
                .filter(d -> month.equals(d.getStart().getMonth()) || month.equals(d.getEnd().getMonth()))
                .map(v -> {
                    LocalDate startDay = month.equals(v.getStart().getMonth()) ? v.getStart() : LocalDate.now().withDayOfMonth(1);
                    LocalDate endDay = month.equals(v.getEnd().getMonth()) ? v.getEnd() : LocalDate.now().withDayOfMonth(month.maxLength());
                    Period between = Period.between(startDay, endDay);
                    return Math.abs(between.getDays());
                })
                .reduce(1, Integer::sum);
    }
    public static long countOfSickDays(PersonDTO p, Month month) {
        return p.getSickDays().stream()
                .filter(d -> month.equals(d.getStart().getMonth()) || month.equals(d.getEnd().getMonth()))
                .map(v -> {
                    LocalDate startDay = month.equals(v.getStart().getMonth()) ? v.getStart() : LocalDate.now().withDayOfMonth(1);
                    LocalDate endDay = month.equals(v.getEnd().getMonth()) ? v.getEnd() : LocalDate.now().withDayOfMonth(month.maxLength());
                    Period between = Period.between(startDay, endDay);
                    return Math.abs(between.getDays());
                })
                .reduce(1, Integer::sum);
    }
}
