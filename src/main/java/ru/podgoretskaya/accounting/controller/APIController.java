package ru.podgoretskaya.accounting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.podgoretskaya.accounting.dto.AccountingDTO;
import ru.podgoretskaya.accounting.dto.PersonDTO;
import ru.podgoretskaya.accounting.service.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unused")
@Tag(name = "Учет времени ")
public class APIController {
    private final AccountingService accountingService;
    private final PersonService personService;



   @PostMapping(value = "/account")
   @Operation(summary = "расчет зп")
   public ResponseEntity<AccountingDTO> getOffersPages(@Valid @RequestBody PersonDTO model) {
       log.info("вызов /account. Параметры: \"" + model.toString());
           return new ResponseEntity(accountingService.jsonCollecting(model), HttpStatus.OK);
   }


//    @GetMapping(value = "/personSickDays")
//    @Operation(summary = "добавление больничного")
//    public ResponseEntity<PersonDTO> getOPages(@Valid @RequestBody SickDays model,PersonDTO personModel) {
//        log.info("вызов /personSickDays. Параметры: \"" + model.toString());
//        return new ResponseEntity(personService.addSickDays(model,personModel), HttpStatus.OK) ;
//    }

}
