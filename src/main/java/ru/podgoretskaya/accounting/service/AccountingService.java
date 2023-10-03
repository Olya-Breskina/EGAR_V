package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.AccountingDTO;
import ru.podgoretskaya.accounting.dto.PersonDTO;

public interface AccountingService {
    AccountingDTO jsonCollecting(PersonDTO p);
}
