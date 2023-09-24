package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.math.BigDecimal;

public interface CalculationSalary {
    BigDecimal calculationSalary(PersonDTO p);

    BigDecimal calculationSalaryOfSickDays(PersonDTO p);

    BigDecimal calculationSalaryOfVacation(PersonDTO p);
}
