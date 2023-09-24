package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.PersonDTO;

public interface DayCalculation {
    long calculationWorkDay(PersonDTO p);

    long daysOfSickDays(PersonDTO p);

    long daysOfVacation(PersonDTO p);

}
