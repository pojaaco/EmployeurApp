package vn.elca.backend.model.dto;

import lombok.*;
import vn.elca.common.Caisse;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeurDto {
    private Long id;

    private Caisse caisse;

    private String number;

    private String name;

    private String numberIDE;

    private String startingDate;

    private String endDate;

    private Set<EmployeeDto> employees;
}