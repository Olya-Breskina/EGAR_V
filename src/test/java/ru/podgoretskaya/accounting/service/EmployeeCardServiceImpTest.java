package ru.podgoretskaya.accounting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

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
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestEmployeeCardDTO.json"), EmployeeCardDTO.class);
        EmployeeCardDTO employeeCard = employeeCardServiceImp.validate(employeeCardDTO);
        assertNotNull(employeeCard);
        assertEquals(employeeCard,employeeCardDTO);

    }

    @Test
    void lastNameBad() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestErrorLastName.json"), EmployeeCardDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.validate(employeeCardDTO));
        assertEquals("проверьте ФИО ", validationException.getMessage());
    }

    @Test
    void lastAgeBad() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestAgeMax.json"), EmployeeCardDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.validate(employeeCardDTO));
        assertEquals("проверьте дату рождения ", validationException.getMessage());
    }

    @Test
    void emailBad() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestEmailBad.json"), EmployeeCardDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.validate(employeeCardDTO));
        assertEquals("неверный email ", validationException.getMessage());
    }

    @Test
    void phoneNumberBad() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/service.employee.card/TestPhoneNumberBad.json"), EmployeeCardDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.validate(employeeCardDTO));
        assertEquals("неверный телефонный номер: без 8, может выглядить 2055550125, 202 555 0125, 202.555.0125 или 202-555-0125", validationException.getMessage());
    }
}