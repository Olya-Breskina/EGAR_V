package ru.podgoretskaya.accounting.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
public class AccountingService implements CalculationSalary, DayCalculation {
//    @Value("${medicalLessThanFiveYears}")
//    private BigDecimal medicalLessThanFiveYears;
//    @Value("${medicalFromFiveToEightYears}")
//    private BigDecimal medicalFromFiveToEightYears;
//    @Value("${medicalLessThanEightYears}")
//    private BigDecimal medicalLessThanEightYears;
//    @Value("${experienceLessThanFiveYears}")
//    private Double experienceLessThanFiveYears;
//    @Value("${experienceFromFiveToEightYears}")
//    private Double experienceFromFiveToEightYears;
private BigDecimal medicalLessThanFiveYears= BigDecimal.valueOf(0.6);
    private BigDecimal medicalFromFiveToEightYears= BigDecimal.valueOf(0.8);
    private BigDecimal medicalLessThanEightYears= BigDecimal.valueOf(1);
    private Double experienceLessThanFiveYears=5.0;
    private Double experienceFromFiveToEightYears=8.0;

    public AccountingDTO jsonCollecting(PersonDTO p) {
        AccountingDTO accountingDTO = new AccountingDTO();
        accountingDTO.setWorkDays(calculationWorkDay(p));
        accountingDTO.setSalaryOfWorkDays(calculationSalary(p));
        accountingDTO.setSalaryOfVacation(calculationSalaryOfVacation(p));
        accountingDTO.setSalaryOfDayOff(calculationSalaryOfSickDays(p));
        accountingDTO.setDaysOfVacation(daysOfVacation(p));
        accountingDTO.setDaysOfDayOff(daysOfSickDays(p));
        System.out.println(accountingDTO);
        return accountingDTO;
    }

    @Override
    public BigDecimal calculationSalary(PersonDTO p) {// считает зп за отработанные дни
        BigDecimal salary = salaryOfDay(p);
        long allWorkDay = calculationWorkDay(p);
        return salary.multiply(BigDecimal.valueOf(allWorkDay));
    }

    @Override
    public BigDecimal calculationSalaryOfSickDays(PersonDTO p) {
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

    @Override
    public BigDecimal calculationSalaryOfVacation(PersonDTO p) {
        BigDecimal salary = medianSalary(p);
        LocalDate previous = LocalDate.now().minusMonths(1);
        long vacationDays = DayUtils.countOfVacationsOrSickDays(p, previous.getMonth());
        return salary.multiply(BigDecimal.valueOf(vacationDays));
    }

    @Override
    public long calculationWorkDay(PersonDTO p) {
        LocalDate previous = LocalDate.now().minusMonths(1); //предыдущий месяц
        int count = previous.getMonth().maxLength();
        long dayWork = count - DayUtils.countOfDayOff(p, previous.getMonth());
        dayWork = dayWork - DayUtils.countOfVacationsOrSickDays(p, previous.getMonth());
        return dayWork;
    }

    @Override
    public long daysOfSickDays(PersonDTO p) {
        LocalDate previous = LocalDate.now().minusMonths(1);
        return DayUtils.countOfDayOff(p, previous.getMonth());
    }

    @Override
    public long daysOfVacation(PersonDTO p) {
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
