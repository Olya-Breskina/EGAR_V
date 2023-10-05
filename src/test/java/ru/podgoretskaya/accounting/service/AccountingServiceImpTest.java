package ru.podgoretskaya.accounting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ru.podgoretskaya.accounting.dto.AccountingDTO;
import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class AccountingServiceImpTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Spy
    AccountingServiceImp accountingServiceImp = new AccountingServiceImp(BigDecimal.valueOf(0.6), BigDecimal.valueOf(0.8), BigDecimal.valueOf(1), 5.0, 8.0, BigDecimal.valueOf(0.13));

    @BeforeEach
    void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @DisplayName("сравнение значений: предполагаемых и расчетных")
    @Test
    void jsonCollecting() throws IOException {
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/accountingService/testPersonDTO.json"), PersonDTO.class);
        AccountingDTO accountingDTO = objectMapper.readValue(new File("src/test/resources/accountingService/testAccountingDTO.json"), AccountingDTO.class);
        AccountingDTO accounting = accountingServiceImp.jsonCollecting(personDTO);

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

    @DisplayName("проверка расчета при наличии только больничного и большого стажа")
    @Test
    void jsonCollectingTwo() throws IOException {
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/accountingService/testPersonDTOWorkExperienceCurrent.json"), PersonDTO.class);
        AccountingDTO accounting = accountingServiceImp.jsonCollecting(personDTO);

        assertEquals(23, accounting.getWorkDays());
        assertEquals(6, accounting.getDaysOfSickDay());
        assertEquals("2331.9840", accounting.getSalaryOfSickDay().toString());
    }
}
