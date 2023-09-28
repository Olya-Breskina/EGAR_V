package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.DayOff;
import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.dto.SickDays;
import ru.podgoretskaya.accounting.dto.Vacation;

public interface AddDays {
    void addVacation(Vacation v, PersonDTO p);
    void addDayOff(DayOff d, PersonDTO p);
    void addSickDay(SickDays s, PersonDTO p);
}
