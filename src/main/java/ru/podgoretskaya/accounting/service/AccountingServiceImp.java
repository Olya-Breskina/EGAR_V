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
public class AccountingServiceImp implements AccountingService {
    @Value("${accounting.medical-less-than-five-years}")
    private BigDecimal medicalLessThanFiveYears;
    @Value("${accounting.medical-from-five-to-eight-years}")
    private BigDecimal medicalFromFiveToEightYears;
    @Value("${accounting.medical-less-than-eight-years}")
    private BigDecimal medicalLessThanEightYears;
    @Value("${accounting.experience-less-than-five-years}")
    private Double experienceLessThanFiveYears;
    @Value("${accounting.experience-from-five-to-eight-years}")
    private Double experienceFromFiveToEightYears;
    @Value("${accounting.tax}")
    private BigDecimal tax;
    @Override
    public AccountingDTO jsonCollecting(PersonDTO p) {
        AccountingDTO accountingDTO = new AccountingDTO();
        accountingDTO.setWorkDays(calculationWorkDay(p));
        accountingDTO.setSalaryOfWorkDays(calculationSalary(p));
        accountingDTO.setDaysOfVacation(daysOfVacation(p));
        accountingDTO.setSalaryOfVacation(calculationSalaryOfVacation(p));
        accountingDTO.setDaysOfDayOff(daysOfDayOff(p));
        accountingDTO.setSalaryOfDayOff(calculationSalaryOfDayOff(p));
        accountingDTO.setDaysOfSickDay(daysOfSickDays(p));
        accountingDTO.setSalaryOfSickDay(calculationSalaryOfSickDays(p));
        accountingDTO.setSalaryGROSS(p.getSalary());
        accountingDTO.setSalaryGROSS(gross(p));
        accountingDTO.setTax(tax(p));
        accountingDTO.setSalaryOnHandy(salaryOnHandy(p));
        return accountingDTO;
    }

    private BigDecimal gross(PersonDTO p) {// грязь
        return calculationSalary(p).add(calculationSalaryOfSickDays(p).add(calculationSalaryOfVacation(p).add(calculationSalaryOfDayOff(p))));
    }

    private BigDecimal tax(PersonDTO p) {//-13%
        return gross(p).multiply(tax);
    }

    private BigDecimal salaryOnHandy(PersonDTO p) {//чистые
        return gross(p).subtract(tax(p));
    }

    private BigDecimal calculationSalary(PersonDTO p) {// считает зп за отработанные дни
        return salaryOfDay(p).multiply(BigDecimal.valueOf(calculationWorkDay(p)));
    }

    private BigDecimal calculationSalaryOfSickDays(PersonDTO p) {//больничный
        BigDecimal salary = medianSalary(p);
        if (p.getWorkExperienceCurrent() < experienceLessThanFiveYears) {
            salary = medianSalary(p).multiply(BigDecimal.valueOf(daysOfSickDays(p)).multiply(medicalLessThanFiveYears));
        } else if ((p.getWorkExperienceCurrent() > experienceLessThanFiveYears) && (p.getWorkExperienceCurrent() < experienceFromFiveToEightYears)) {
            salary = medianSalary(p).multiply(BigDecimal.valueOf(daysOfSickDays(p)).multiply(medicalFromFiveToEightYears));
        } else {
            salary = medianSalary(p).multiply(BigDecimal.valueOf(daysOfSickDays(p)).multiply(medicalLessThanEightYears));
        }
        return salary;
    }

    private BigDecimal calculationSalaryOfVacation(PersonDTO p) {//отпускные
        return salaryOfDay(p).multiply(BigDecimal.valueOf(daysOfVacation(p)));
    }

    private BigDecimal calculationSalaryOfDayOff(PersonDTO p) {//отгул
        return medianSalary(p).multiply(BigDecimal.valueOf(daysOfDayOff(p)));
    }

    private long calculationWorkDay(PersonDTO p) {
        LocalDate previous = LocalDate.now().minusMonths(1); //предыдущий месяц
        int count = previous.getMonth().maxLength();
        return (count - daysOfDayOff(p) - daysOfSickDays(p) - daysOfVacation(p));
    }

    private long daysOfDayOff(PersonDTO p) {
        LocalDate previous = LocalDate.now().minusMonths(1);
        return DayUtils.countOfDayOff(p, previous.getMonth());
    }

    private long daysOfSickDays(PersonDTO p) {// сколько было больничный
        LocalDate previous = LocalDate.now().minusMonths(1);
        return DayUtils.countOfSickDays(p, previous.getMonth());
    }

    private long daysOfVacation(PersonDTO p) {// сколько было отпуска
        LocalDate previous = LocalDate.now().minusMonths(1);
        return DayUtils.countOfVacations(p, previous.getMonth());
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
