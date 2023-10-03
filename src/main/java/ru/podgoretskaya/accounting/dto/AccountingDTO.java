package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Setter
@Getter
@ToString(exclude = "person")
@Schema(description = "расчетка сотрудника")
public class AccountingDTO {
    private PersonDTO person;

    @Schema(description = "отработал в этом месяце")
    private long workDays;

    @Schema(description = "зарплата за отработанный месяц")
    private BigDecimal salaryOfWorkDays;

    @Schema(description = "был в отпуске х дней")
    private long daysOfVacation;

    @Schema(description = "отпускные")
    private BigDecimal salaryOfVacation;

    @Schema(description = "был в отгуле х дней")
    private long daysOfDayOff;

    @Schema(description = "оплата отгула")
    private BigDecimal salaryOfDayOff;

    @Schema(description = "был на больничном х дней")
    private long daysOfSickDay;

    @Schema(description = "больничные")
    private BigDecimal salaryOfSickDay;

    @Schema(description = "сумма грязными")
    private BigDecimal salaryGROSS;

    @Schema(description = "подоходный налог  - 13%")
    private BigDecimal tax;

    @Schema(description = "сумма чистыми")
    private BigDecimal salaryOnHandy;
}
