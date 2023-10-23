package ru.podgoretskaya.accounting.service;

import ru.podgoretskaya.accounting.dto.AccountingDTO;
import ru.podgoretskaya.accounting.dto.CalculationDTO;

public interface AccountingService {
    AccountingDTO jsonCollecting(CalculationDTO p);
}
