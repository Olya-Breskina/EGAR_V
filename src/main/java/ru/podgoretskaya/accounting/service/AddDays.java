package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.SickDays;
import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.dto.Vacation;

public interface AddDays {
    void addVacation(Vacation v, PersonDTO p);
    void addSickDays(SickDays sickDays, PersonDTO p);
}
