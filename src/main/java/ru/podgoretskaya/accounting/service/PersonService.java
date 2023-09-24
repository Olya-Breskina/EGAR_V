package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.dto.SickDays;
import ru.podgoretskaya.accounting.dto.Vacation;

public class PersonService implements AddDays {
    @Override
    public void addVacation(Vacation v, PersonDTO p) {
        p.getVacations().add(v);
        v.setPerson(p);
    }

    @Override
    public void addSickDays(SickDays sickDays, PersonDTO p) {
        p.getSickDays().add(sickDays);
        sickDays.setPerson(p);
    }
}
