package ru.podgoretskaya.accounting.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class DayUtilsTest {
    ObjectMapper objectMapper = new ObjectMapper();
    DayUtils dayUtils = new DayUtils();
    @BeforeEach
    void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @DisplayName("подсчет количества отгулов в месяце")
    @Test
    void countOfDayOff() throws IOException {
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/util/testPersonDTO.json"), PersonDTO.class);
        Month month= Month.SEPTEMBER;
        long l = dayUtils.countOfDayOff(personDTO, month);
        assertEquals(2,l);
    }

    @Test
    void countOfVacations() throws IOException {
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/util/testPersonDTO.json"), PersonDTO.class);
        Month month= Month.SEPTEMBER;
        long l = dayUtils. countOfVacations(personDTO, month);
        assertEquals(13,l);
    }

    @Test
    void countOfSickDays()  throws IOException{
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/util/testPersonDTO.json"), PersonDTO.class);
        Month month= Month.SEPTEMBER;
        long l = dayUtils. countOfSickDays(personDTO, month);
        assertEquals(6,l);
    }
}