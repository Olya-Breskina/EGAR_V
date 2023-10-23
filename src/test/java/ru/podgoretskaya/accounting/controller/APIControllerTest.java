package ru.podgoretskaya.accounting.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.podgoretskaya.accounting.service.AccountingService;
import ru.podgoretskaya.accounting.service.EmployeeCardService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(APIController.class)
class APIControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AccountingService accountingService;
    @MockBean
    EmployeeCardService employeeCardService;

    @DisplayName("проверка первого запроса: карточка сотрудника в справочнике компании, все данные валидны")
    @Order(1)
    @Test
    void getCard() throws Exception {
        mockMvc.perform(post("/accounting/getCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "lastName": "Ivanov",
                                   "firstName": "Ivan",
                                   "middleName": " ",
                                   "birthdate": "2000-10-23",
                                   "position": "ANALYST",
                                   "department": "ACCOUNTING",
                                   "grade": "JUNIOR",
                                   "phoneNumber": "9485218612",
                                   "email": "iivan@mai.ru",
                                   "occupancy": "REMOTEWORK",
                                   "workingMode": "FROM_9_TO_18",
                                   "workExperienceCurrent": 2,
                                   "salary": 15000
                                 }
                                """))
                .andExpect(status().isOk());
    }

    @DisplayName("проверка второго запроса: расчет зп, когда данные валидны")
    @Order(2)
    @Test
    void getOffersPages() throws Exception {
        mockMvc.perform(post("/accounting/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "vacations": [
                                      {
                                        "start": "2023-09-01",
                                        "end": "2023-09-15"
                                      }
                                    ],
                                    "dayOff": [
                                      {
                                        "date": "2023-09-23"
                                      }
                                    ],
                                    "sickDays": [
                                      {
                                        "start": "2023-09-24",
                                        "end": "2023-09-26"
                                      }
                                    ],
                                    "workExperienceCurrent": 2,
                                    "salary": 15000
                                  }
                                """))
                .andExpect(status().isOk());
    }

}