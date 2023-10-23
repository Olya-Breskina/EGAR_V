package ru.podgoretskaya.accounting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ru.podgoretskaya.accounting.dto.PersonDTO;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class EmployeeCardServiceImpTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Spy
    EmployeeCardService employeeCardServiceImp = new EmployeeCardServiceImp(70);

    @BeforeEach
    void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void save() throws IOException {
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestPersonDTO.json"), PersonDTO.class);
        PersonDTO personDTO1 = employeeCardServiceImp.validate(personDTO);
        assertNotNull(personDTO);
        assertEquals(personDTO,personDTO1);

    }

    @Test
    void lastNameBad() throws IOException {
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestErrorLastName.json"), PersonDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.validate( personDTO));
        assertEquals("проверьте ФИО ", validationException.getMessage());
    }

    @Test
    void lastAgeBad() throws IOException {
        PersonDTO personDTO  = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestAgeMax.json"),  PersonDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.validate(personDTO));
        assertEquals("проверьте дату рождения ", validationException.getMessage());
    }

    @Test
    void emailBad() throws IOException {
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestEmailBad.json"),  PersonDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.validate(personDTO));
        assertEquals("неверный email ", validationException.getMessage());
    }

    @Test
    void phoneNumberBad() throws IOException {
        PersonDTO personDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestPhoneNumberBad.json"),  PersonDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.validate(personDTO));
        assertEquals("неверный телефонный номер: без 8, может выглядеть 2055550125, 202 555 0125, 202.555.0125 или 202-555-0125", validationException.getMessage());
    }
}