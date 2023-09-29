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
    @NotBlank
    private LocalDate birthdate;

    @Schema(description = "должность")
   // @NotBlank
    private PositionEnum position;

    @Schema(description = "отдел")
 //   @NotBlank
    private DepartmentEnum department;

    @Schema(description = "номер телефона")
    @NotBlank
    private String phoneNumber;

    @Schema(description = "электронный адрес")
    @NotBlank
    private String email;

    @Schema(description = "занятость (место работы)")
  //  @NotBlank
    private OccupancyEnum occupancy;

    @Schema(description = "режим работы")
   // @NotBlank
    private WorkingModeEnum workingMode;
}
