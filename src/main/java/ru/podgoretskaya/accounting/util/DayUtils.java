package ru.podgoretskaya.accounting.util;

import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

public class DayUtils {
    DayUtils() {
    }

    public static long countOfDayOff(PersonDTO p, Month month) {
        //todo это не больничный, это отгул (он оплачивается по средней зп)
        return p.getDayOff().stream()
                .filter(d -> month.equals(d.getDate().getMonth())).count();
    }

    public static long countOfVacationsOrSickDays(PersonDTO p, Month month) {
        return p.getVacations().stream()
                .filter(d -> month.equals(d.getStart().getMonth()) || month.equals(d.getEnd().getMonth()))
                .map(v -> {
                    LocalDate startDay = month.equals(v.getStart().getMonth()) ? v.getStart() : LocalDate.now().withDayOfMonth(1);
                    LocalDate endDay = month.equals(v.getEnd().getMonth()) ? v.getEnd() : LocalDate.now().withDayOfMonth(month.maxLength());
                    Period between = Period.between(startDay, endDay);
                    return Math.abs(between.getDays());
                })
                .reduce(0, Integer::sum);
    }
}
