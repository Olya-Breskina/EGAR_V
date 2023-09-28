package ru.podgoretskaya.accounting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.dto.DayOff;
import ru.podgoretskaya.accounting.dto.SickDays;
import ru.podgoretskaya.accounting.dto.Vacation;
@Service
@Slf4j
@AllArgsConstructor
public class PersonService implements AddDays {

    @Override
    public void addVacation(Vacation v, PersonDTO p) {
        p.getVacations().add(v);
    }

    @Override
    public void addDayOff(DayOff d, PersonDTO p) {
        p.getDayOff().add(d);
    }

    @Override
    public void addSickDay(SickDays s,PersonDTO p){
        p.getSickDays().add((s));
    }
}
