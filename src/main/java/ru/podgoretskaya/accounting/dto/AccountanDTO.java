package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Setter
@Getter
@ToString(exclude = "person")
public class AccountanDTO {
    private PersonDTO person;

    @Schema(description = "отработал в этом месяце")
    private long workDays;

    @Schema(description = "зарплата за отработанный месяц")
    private BigDecimal salaryOfWorkDays;

    @Schema(description = "был в отпуске х дней")
    private long daysOfVacation;

    @Schema(description = "отпускные")
    private BigDecimal salaryOfVacation;

    @Schema(description = "был на больничном х дней")
    private long daysOfSickDays;

    @Schema(description = "больничные")
    private BigDecimal salaryOfSickDays;


}
