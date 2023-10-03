package ru.podgoretskaya.accounting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.podgoretskaya.accounting.dto.enums.DepartmentEnum;
import ru.podgoretskaya.accounting.dto.enums.PositionEnum;
import ru.podgoretskaya.accounting.dto.enums.OccupancyEnum;
import ru.podgoretskaya.accounting.dto.enums.WorkingModeEnum;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "карточка сотрудника в справочнике компании")
public class EmployeeCardDTO {
    @Schema(description = "фамилия")
    @Size(min = 2, max = 30)
    @NotBlank
    private String lastName;

    @Schema(description = "имя")
    @Size(min = 2, max = 30)
    @NotBlank
    private String firstName;

    @Schema(description = "дата рождения")
    private LocalDate birthdate;

    @Schema(description = "должность")
    private PositionEnum position;

    @Schema(description = "отдел")
    private DepartmentEnum department;

    @Schema(description = "номер телефона (без 8, может выглядить 2055550125, 202 555 0125, 202.555.0125 или 202-555-0125")
    @NotBlank
    private String phoneNumber;

    @Schema(description = "электронный адрес")
    @NotBlank
    private String email;

    @Schema(description = "занятость (место работы)")
    private OccupancyEnum occupancy;

    @Schema(description = "режим работы")
    private WorkingModeEnum workingMode;
}
