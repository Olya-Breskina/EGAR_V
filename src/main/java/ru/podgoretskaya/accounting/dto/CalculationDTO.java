package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "данные для бухгалтерии")
public class CalculationDTO {
    @Schema(description = "отпуск")
    private List<Vacation> vacations = new ArrayList<>();
    @Schema(description = "отгул")
    private List<DayOff> dayOff = new ArrayList<>();
    @Schema(description = "больничный")
    private List<SickDays> sickDays = new ArrayList<>();
    @Schema(description = "стаж")
    @NonNull
    private Double workExperienceCurrent;
    @Schema(description = "зарплата")
    @NonNull
    private BigDecimal salary;
}
