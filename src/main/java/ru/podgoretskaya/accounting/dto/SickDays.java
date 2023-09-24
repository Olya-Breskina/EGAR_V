package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "отгул")
@ToString(exclude = "person")
public class SickDays  {
    private final LocalDate date;
    private PersonDTO person;
}
