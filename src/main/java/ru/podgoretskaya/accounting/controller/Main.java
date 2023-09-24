package ru.podgoretskaya.accounting.controller;

import ru.podgoretskaya.accounting.dto.AccountanDTO;
import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.dto.SickDays;
import ru.podgoretskaya.accounting.dto.Vacation;
import ru.podgoretskaya.accounting.service.AccountanService;
import ru.podgoretskaya.accounting.service.PersonService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        PersonDTO GansDTO = new PersonDTO();
        GansDTO.setFirstName("Gans");
        GansDTO.setLastName("Male");
        GansDTO.setSalary(BigDecimal.valueOf(15000));
        GansDTO.setWorkExperienceCurrent(15.0);
        Vacation GansVacation = new Vacation(LocalDate.parse("2023-08-01"), LocalDate.parse("2023-08-15"));
        SickDays GansSickDays = new SickDays(LocalDate.parse("2023-08-20"));
        PersonService GansService = new PersonService();
        GansService.addVacation(GansVacation, GansDTO);
        GansService.addSickDays(GansSickDays, GansDTO);
        System.out.println(GansDTO);

        AccountanDTO  accountanDTO = new AccountanDTO();
        AccountanService accountanService = new AccountanService();
        accountanDTO.setWorkDays(accountanService.calculationWorkDay(GansDTO));
        accountanDTO.setSalaryOfWorkDays(accountanService.calculationSalary(GansDTO));
        accountanDTO.setSalaryOfVacation(accountanService.calculationSalaryOfVacation(GansDTO));
        accountanDTO.setSalaryOfSickDays(accountanService.calculationSalaryOfSickDays(GansDTO));
        accountanDTO.setDaysOfVacation(accountanService.daysOfVacation(GansDTO));
        accountanDTO.setDaysOfSickDays(accountanService.daysOfSickDays(GansDTO));
        System.out.println(accountanDTO);
    }
}
