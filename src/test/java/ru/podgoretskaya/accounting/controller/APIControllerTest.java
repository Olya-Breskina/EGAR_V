package ru.podgoretskaya.accounting.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import ru.podgoretskaya.accounting.service.AccountingService;
import ru.podgoretskaya.accounting.service.EmployeeCardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
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
        mockMvc.perform(post("/getCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "lastName": "Mww",
                                  "firstName": "Gans",
                                  "birthdate": "1998-10-03",
                                  "position": "ANALYST",
                                  "department": "FOS",
                                  "phoneNumber": "987 456 1230",
                                  "email": "qwert@mqil.ru",
                                  "occupancy": "HYBRIDWORK",
                                  "workingMode": "ALL_DAY_ALL_NIGHT"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @DisplayName("проверка первого запроса: карточка сотрудника в справочнике компании, когда данные не валидны")
    @Order(2)
    @Test
    void getCardTwo() throws Exception {
        when(employeeCardService.save(any())).thenThrow(new HttpMessageNotReadableException("ошибка заполнения формы"));
        mockMvc.perform(post("/getCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "lastName": "Mww",
                                  "firstName": "Gans",
                                  "birthdate": "1998-10-03",
                                  "position": "ANALYST",
                                  "department": "FOS",
                                  "phoneNumber": "(987) 456 1230",
                                  "email": "qwert@mqil.ru",
                                  "occupancy": "HYBRIDWORK",
                                  "workingMode": "ALL_DAY_ALL_NIGHT"
                                }
                                """))
                .andExpect(status().isBadRequest());
        verify(employeeCardService,times(1)).save(any());
    }
    @DisplayName("проверка второго запроса: расчет зп, когда данные валидны")
    @Order(3)
    @Test
    void getOffersPages() throws Exception {
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "vacations": [
                                     {
                                       "start": "2023-09-01",
                                       "end": "2023-09-12"
                                     }
                                   ],
                                   "dayOff": [
                                     {
                                       "date": "2023-09-19"
                                     }
                                   ],
                                   "sickDays": [
                                     {
                                       "start": "2023-09-20",
                                       "end": "2023-09-25"
                                     }
                                   ],
                                   "lastName": "gq",
                                   "firstName": "ng",
                                   "middleName": "g",
                                   "birthdate": "2000-10-03",
                                   "position": "ANALYST",
                                   "department": "ACCOUNTING",
                                   "grade": "JUNIOR",
                                   "phoneNumber": "string",
                                   "email": "string",
                                   "occupancy": "REMOTEWORK",
                                   "workingMode": "FROM_9_TO_18",
                                   "workExperienceCurrent": 1,
                                   "salary": 10000
                                 }
                                """))
                .andExpect(status().isOk());
    }

    @DisplayName("проверка второго запроса: расчет зп, когда данные не валидны")
    @Order(3)
    @Test
    void getOffersPagesTwo() throws Exception {
        when(accountingService.jsonCollecting(any())).thenThrow(new HttpMessageNotReadableException("ошибка заполнения формы"));
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "vacations": [
                                     {
                                       "start": "2023-09-01",
                                       "end": "2023-09-12"
                                     }
                                   ],
                                   "dayOff": [
                                     {
                                       "date": "2023-09-19"
                                     }
                                   ],
                                   "sickDays": [
                                     {
                                       "start": "2023-09-20",
                                       "end": "2023-09-25"
                                     }
                                   ],
                                   "lastName": "gq",
                                   "firstName": "ng",
                                   "middleName": "g",
                                   "birthdate": "2000-10-03",
                                   "position": "ANALYST",
                                   "department": "ACCOUNTING",
                                   "grade": "JUNIOR",
                                   "phoneNumber": "string",
                                   "email": "string",
                                   "occupancy": "REMOTEWORK",
                                   "workingMode": "FROM_9_TO_18",
                                   "workExperienceCurrent": 1
                                 }
                                """))
                .andExpect(status().isBadRequest());
        verify(accountingService,times(1)).jsonCollecting(any());;
    }
}