package ru.podgoretskaya.accounting.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ru.podgoretskaya.accounting.dto.CalculationDTO;
import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class DayUtilsTest {
    static final Month month = Month.SEPTEMBER;
    ObjectMapper objectMapper = new ObjectMapper();
    CalculationDTO calculationDTO;


    @BeforeEach
    void beforeAll() throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        calculationDTO = objectMapper.readValue(new File("src/test/resources/util/TestPersonDTO.json"), CalculationDTO.class);

    }

    @Test
    void countOfDayOff() throws IOException {
        long l = DayUtils.countOfDayOff(calculationDTO, month);
        assertEquals(1, l);
    }

    @Test
    void countOfVacations() throws IOException {
        long l = DayUtils.countOfVacations(calculationDTO, month);
        assertEquals(16, l);
    }

    @Test
    void countOfSickDays() throws IOException {
        long l = DayUtils.countOfSickDays(calculationDTO, month);
        assertEquals(4, l);
    }
}