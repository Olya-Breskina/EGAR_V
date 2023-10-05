package ru.podgoretskaya.accounting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ru.podgoretskaya.accounting.dto.EmployeeCardDTO;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class EmployeeCardServiceImpTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Spy
    EmployeeCardServiceImp employeeCardServiceImp = new EmployeeCardServiceImp(70);

    @BeforeEach
    void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @DisplayName("проверка, что не пустой ")
    @Order(1)
    @Test
    void save() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/employeeCardService/testEmployeeCardDTO.json"), EmployeeCardDTO.class);
        EmployeeCardDTO employeeCard = employeeCardServiceImp.save(employeeCardDTO);
        assertNotNull(employeeCard);

    }

    @DisplayName("проверка имени ")
    @Order(2)
    @Test
    void firstNameGood() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/employeeCardService/testEmployeeCardDTO.json"), EmployeeCardDTO.class);
        assertEquals("Gans", employeeCardDTO.getFirstName());

    }

    @DisplayName("проверка фамилии")
    @Order(3)
    @Test
    void lastNameBad() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/employeeCardService/testErrorLastName.json"), EmployeeCardDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.save(employeeCardDTO));
        assertEquals("проверьте ФИО ", validationException.getMessage());
    }

    @DisplayName("проверка ваозраста")
    @Order(4)
    @Test
    void lastAgeGood() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/employeeCardService/testEmployeeCardDTO.json"), EmployeeCardDTO.class);
        assertEquals("1998-10-03", employeeCardDTO.getBirthdate().toString());
    }

    @DisplayName("проверка возраста> мах")
    @Order(5)
    @Test
    void lastAgeBad() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/employeeCardService/testAgeMax.json"), EmployeeCardDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.save(employeeCardDTO));
        assertEquals("проверьте дату рождения ", validationException.getMessage());
    }

    @DisplayName("проверка не валидного email")
    @Order(6)
    @Test
    void emailBad() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/employeeCardService/testEmailBad.json"), EmployeeCardDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.save(employeeCardDTO));
        assertEquals("неверный email ", validationException.getMessage());
    }

    @DisplayName("проверка не валидного тел/номера")
    @Order(7)
    @Test
    void phoneNumberBad() throws IOException {
        EmployeeCardDTO employeeCardDTO = objectMapper.readValue(new File("src/test/resources/employeeCardService/testPhoneNumberBad.json"), EmployeeCardDTO.class);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> employeeCardServiceImp.save(employeeCardDTO));
        assertEquals("неверный телефонный номер: без 8, может выглядить 2055550125, 202 555 0125, 202.555.0125 или 202-555-0125", validationException.getMessage());
    }
}