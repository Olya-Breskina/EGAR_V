package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.podgoretskaya.accounting.dto.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "карточка сотрудника в  бухгалтерии")
public class PersonDTO {
    @Schema(description = "фамилия")
    @Size(min = 2, max = 30)
    private String lastName;
    @Schema(description = "имя")
    @Size(min = 2, max = 30)
    private String firstName;
    @Schema(description = "отчество, если есть")
    private String middleName;
    @Schema(description = "дата рождения")
    private LocalDate birthdate;
    @Schema(description = "должность")
    private PositionEnum position;
    @Schema(description = "отдел")
    private DepartmentEnum department;
    @Schema(description = "категория")
    private GradeEnum grade;
    @Schema(description = "номер телефона")
    private String phoneNumber;
    @Schema(description = "электронный адрес")
    private String email;
    @Schema(description = "занятость (место работы)")
    private OccupancyEnum occupancy;
    @Schema(description = "режим работы")
    private WorkingModeEnum workingMode;
    @Schema(description = "стаж")
    @NonNull
    private Double workExperienceCurrent;
    @Schema(description = "зарплата")
    @NonNull
    private BigDecimal salary;

}
