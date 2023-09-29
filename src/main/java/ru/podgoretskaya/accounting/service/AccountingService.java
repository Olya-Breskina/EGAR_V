package ru.podgoretskaya.accounting.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.podgoretskaya.accounting.dto.AccountingDTO;
import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.util.DayUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AccountingService {
    @Value("${medicalLessThanFiveYears}")
    private BigDecimal medicalLessThanFiveYears;
    @Value("${medicalFromFiveToEightYears}")
    private BigDecimal medicalFromFiveToEightYears;
    @Value("${medicalLessThanEightYears}")
    private BigDecimal medicalLessThanEightYears;
    @Value("${experienceLessThanFiveYears}")
    private Double experienceLessThanFiveYears;
    @Value("${experienceFromFiveToEightYears}")
    private Double experienceFromFiveToEightYears;
    @Value("${tax}")
    private BigDecimal tax;

    public AccountingDTO jsonCollecting(PersonDTO p) {
        AccountingDTO accountingDTO = new AccountingDTO();
        accountingDTO.setWorkDays(calculationWorkDay(p));
        accountingDTO.setSalaryOfWorkDays(calculationSalary(p));
        accountingDTO.setSalaryOfVacation(calculationSalaryOfVacation(p));
        accountingDTO.setSalaryOfDayOff(calculationSalaryOfVacation(p));
        accountingDTO.setDaysOfVacation(daysOfVacation(p));
        accountingDTO.setDaysOfDayOff(daysOfDayOff(p));
        accountingDTO.setSalaryGROSS(p.getSalary());
        accountingDTO.setSalaryGROSS(gross(p));
        accountingDTO.setTax(tax(p));
        accountingDTO.setSalaryOnHandy(salaryOnHandy(p));
        return accountingDTO;
    }

    private BigDecimal gross(PersonDTO p) {
        return calculationSalary(p).add(calculationSalaryOfVacation(p).add(calculationSalaryOfVacation(p)));
    }

    private BigDecimal tax(PersonDTO p) {
        return gross(p).multiply(tax);
    }

    private BigDecimal salaryOnHandy(PersonDTO p) {
        return gross(p).subtract(tax(p));
    }

    private BigDecimal calculationSalary(PersonDTO p) {// считает зп за отработанные дни
        BigDecimal salary = salaryOfDay(p);
        long allWorkDay = calculationWorkDay(p);
        return salary.multiply(BigDecimal.valueOf(allWorkDay));
    }

    private BigDecimal calculationSalaryOfSickDays(PersonDTO p) {
        BigDecimal salary = medianSalary(p);
        LocalDate previous = LocalDate.now().minusMonths(1);
        BigDecimal salaryOfSickDays;
        long sickDays = DayUtils.countOfDayOff(p, previous.getMonth());
        if (p.getWorkExperienceCurrent() < experienceLessThanFiveYears) {
            salaryOfSickDays = medicalLessThanFiveYears.multiply(BigDecimal.valueOf(sickDays)).multiply(salary);
        } else if ((p.getWorkExperienceCurrent() > experienceLessThanFiveYears) && (p.getWorkExperienceCurrent() < experienceFromFiveToEightYears)) {
            salaryOfSickDays = medicalFromFiveToEightYears.multiply(BigDecimal.valueOf(sickDays)).multiply(salary);
        } else {
            salaryOfSickDays = medicalLessThanEightYears.multiply(BigDecimal.valueOf(sickDays)).multiply(salary);
        }
        return salaryOfSickDays;
    }

    private BigDecimal calculationSalaryOfVacation(PersonDTO p) {
        BigDecimal salary = medianSalary(p);
        LocalDate previous = LocalDate.now().minusMonths(1);
        long vacationDays = DayUtils.countOfVacationsOrSickDays(p, previous.getMonth());
        return salary.multiply(BigDecimal.valueOf(vacationDays));
    }

    private BigDecimal calculationSalaryOfDayOff(PersonDTO p) {
        return null;
    }

    private long calculationWorkDay(PersonDTO p) {
        LocalDate previous = LocalDate.now().minusMonths(1); //предыдущий месяц
        int count = previous.getMonth().maxLength();
        long dayWork = count - DayUtils.countOfDayOff(p, previous.getMonth());
        dayWork = dayWork - DayUtils.countOfVacationsOrSickDays(p, previous.getMonth());
        return dayWork;
    }


    private long daysOfDayOff(PersonDTO p) {
        LocalDate previous = LocalDate.now().minusMonths(1);
        return DayUtils.countOfDayOff(p, previous.getMonth());
    }


    private long daysOfVacation(PersonDTO p) {
        LocalDate previous = LocalDate.now().minusMonths(1);
        return DayUtils.countOfVacationsOrSickDays(p, previous.getMonth());
    }

    private BigDecimal salaryOfDay(PersonDTO p) {//зп день
        LocalDate previous = LocalDate.now().minusMonths(1);
        int count = previous.getMonth().maxLength();
        return p.getSalary().divide(BigDecimal.valueOf(count), 3, RoundingMode.HALF_UP);
    }

    private BigDecimal medianSalary(PersonDTO p) {// средняя зп
        int previous = LocalDate.now().minusMonths(1).getDayOfYear();
        BigDecimal salaryOfYear = p.getSalary().multiply(BigDecimal.valueOf(12));
        return salaryOfYear.divide(BigDecimal.valueOf(previous), 3, RoundingMode.HALF_UP);

    }
}
