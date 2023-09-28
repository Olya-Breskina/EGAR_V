package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Schema(description = "отгул")
@ToString

public class DayOff {
    private LocalDate date;
}
