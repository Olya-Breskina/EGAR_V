package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "отпуск")
@ToString(exclude = "person")
public class Vacation {
    private final LocalDate start;
    private final LocalDate end;
    private PersonDTO person;
}
