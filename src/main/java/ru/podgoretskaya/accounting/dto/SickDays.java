package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "больничный")

public class SickDays {
    private LocalDate start;
    private LocalDate end;
}
