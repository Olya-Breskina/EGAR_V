package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.podgoretskaya.accounting.dto.enams.GradeEnam;
import ru.podgoretskaya.accounting.dto.enams.PositionEnam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PersonDTO  {
    @Schema(description = "фамилия")
    @Size(min = 2, max = 30)
    private String lastName;

    @Schema(description = "имя")
    @Size(min = 2, max = 30)
    private String firstName;

    @Schema(description = "отчество, если есть")
    private String middleName;

    @Schema(description = "должность")
    private PositionEnam position;

    @Schema(description = "категория")
    private GradeEnam grade;

    @Schema(description = "стаж")
    @NonNull
    private Double workExperienceCurrent;

    @Schema(description = "зарплата")
    @NonNull
    private BigDecimal salary;

    @Schema(description = "отпуск")
    private final List<Vacation> vacations = new ArrayList<>();

    @Schema(description = "отгул")
    private final List<SickDays> sickDays = new ArrayList<>();

}
