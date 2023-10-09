package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.EmployeeCardDTO;

public interface EmployeeCardService {
    EmployeeCardDTO validate(EmployeeCardDTO model);
}
