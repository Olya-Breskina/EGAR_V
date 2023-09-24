package ru.podgoretskaya.accounting.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.dto.SickDays;
import ru.podgoretskaya.accounting.dto.Vacation;
@Service
@Slf4j
@AllArgsConstructor
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
