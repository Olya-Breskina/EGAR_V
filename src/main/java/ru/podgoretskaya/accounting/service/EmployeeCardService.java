package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.PersonDTO;

public interface EmployeeCardService {
    PersonDTO  validate(PersonDTO model);
}
