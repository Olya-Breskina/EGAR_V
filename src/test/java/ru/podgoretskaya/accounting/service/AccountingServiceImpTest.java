package ru.podgoretskaya.accounting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ru.podgoretskaya.accounting.dto.AccountingDTO;
import ru.podgoretskaya.accounting.dto.CalculationDTO;
import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class AccountingServiceImpTest {
    static final BigDecimal medicalLessThanFiveYears = BigDecimal.valueOf(0.6);
    static final BigDecimal medicalFromFiveToEightYears = BigDecimal.valueOf(0.8);
    static final BigDecimal medicalLessThanEightYears = BigDecimal.valueOf(1);
    static final Double experienceLessThanFiveYears = 5.0;
    static final Double experienceFromFiveToEightYears = 8.0;
    static final BigDecimal tax = BigDecimal.valueOf(0.13);
    ObjectMapper objectMapper = new ObjectMapper();
    @Spy
    AccountingService accountingServiceImp = new AccountingServiceImp(medicalLessThanFiveYears, medicalFromFiveToEightYears, medicalLessThanEightYears,
            experienceLessThanFiveYears, experienceFromFiveToEightYears, tax);

    @BeforeEach
    void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void jsonCollecting() throws IOException {
        CalculationDTO calculationDTO = objectMapper.readValue(new File("src/test/resources/service.accounting/TestCalculation.json"), CalculationDTO.class);
        AccountingDTO accountingDTO = objectMapper.readValue(new File("src/test/resources/service.accounting/TestAccountingDTO.json"), AccountingDTO.class);
        AccountingDTO accounting = accountingServiceImp.jsonCollecting(calculationDTO);

        assertEquals(accountingDTO.getWorkDays(), accounting.getWorkDays());
        assertEquals(accountingDTO.getSalaryOfWorkDays(), accounting.getSalaryOfWorkDays());

        assertEquals(accountingDTO.getDaysOfVacation(), accounting.getDaysOfVacation());
        assertEquals(accountingDTO.getSalaryOfVacation(), accounting.getSalaryOfVacation());

        assertEquals(accountingDTO.getDaysOfDayOff(), accounting.getDaysOfDayOff());
        assertEquals(accountingDTO.getSalaryOfDayOff(), accounting.getSalaryOfDayOff());

        assertEquals(accountingDTO.getDaysOfSickDay(), accounting.getDaysOfSickDay());
        assertEquals(accountingDTO.getSalaryOfSickDay(), accounting.getSalaryOfSickDay());

        assertEquals(accountingDTO.getSalaryGROSS(), accounting.getSalaryGROSS());
        assertEquals(accountingDTO.getTax(), accounting.getTax());
        assertEquals(accountingDTO.getSalaryOnHandy(), accounting.getSalaryOnHandy());
    }

    @Test
    void jsonCollectingTwo() throws IOException {
        CalculationDTO calculationDTO = objectMapper.readValue(new File("src/test/resources/service.accounting/TestCalculationDTOWorkExperienceCurrent.json"), CalculationDTO.class);
        AccountingDTO accounting = accountingServiceImp.jsonCollecting(calculationDTO);

        assertEquals(9, accounting.getWorkDays());
        assertEquals(4, accounting.getDaysOfSickDay());
        assertEquals("2706.768", accounting.getSalaryOfSickDay().toString());
    }
}
