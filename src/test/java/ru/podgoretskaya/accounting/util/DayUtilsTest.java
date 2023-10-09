package ru.podgoretskaya.accounting.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class DayUtilsTest {
    static final Month month = Month.SEPTEMBER;
    ObjectMapper objectMapper = new ObjectMapper();
    PersonDTO personDTO;


    @BeforeEach
    void beforeAll() throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        personDTO = objectMapper.readValue(new File("src/test/resources/util/TestPersonDTO.json"), PersonDTO.class);

    }

    @Test
    void countOfDayOff() throws IOException {
        long l = DayUtils.countOfDayOff(personDTO, month);
        assertEquals(2, l);
    }

    @Test
    void countOfVacations() throws IOException {
        long l = DayUtils.countOfVacations(personDTO, month);
        assertEquals(13, l);
    }

    @Test
    void countOfSickDays() throws IOException {
        long l = DayUtils.countOfSickDays(personDTO, month);
        assertEquals(6, l);
    }
}