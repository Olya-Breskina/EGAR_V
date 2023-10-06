package ru.podgoretskaya.accounting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.podgoretskaya.accounting.dto.AccountingDTO;
import ru.podgoretskaya.accounting.dto.EmployeeCardDTO;
import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.service.AccountingService;
import ru.podgoretskaya.accounting.service.EmployeeCardService;

@RestController
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unused")
@Tag(name = "Учет времени ")
public class APIController {
    private final AccountingService accountingService;
    private final EmployeeCardService employeeCardService;

    @PostMapping(value = "/getCard")
    @Operation(summary = "заполнение карточки сотрудника для справочника")
    public ResponseEntity<EmployeeCardDTO> getCard(@Valid @RequestBody EmployeeCardDTO model) {
            log.info("вызов /getCard. Параметры: \"" + model.toString());
            return new ResponseEntity<>(employeeCardService.save(model), HttpStatus.OK);
    }

    @PostMapping(value = "/account")
    @Operation(summary = "расчет зп")
    public ResponseEntity<AccountingDTO> getOffersPages(@Valid @RequestBody PersonDTO model) {
        log.info("вызов /account. Параметры: \"" + model.toString());
        return new ResponseEntity(accountingService.jsonCollecting(model), HttpStatus.OK);
    }
}
