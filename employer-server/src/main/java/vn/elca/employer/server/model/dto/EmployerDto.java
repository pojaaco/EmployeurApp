package vn.elca.employer.server.model.dto;

import lombok.*;
import vn.elca.employer.common.Fund;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployerDto {
    private Long id;

    private Fund fund;

    private String number;

    private String name;

    private String numberIde;

    private String startDate;

    private String endDate;

    private Set<EmployeeDto> employees;
}