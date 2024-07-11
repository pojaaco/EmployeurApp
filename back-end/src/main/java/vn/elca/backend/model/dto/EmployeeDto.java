package vn.elca.backend.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;

    private String numberAVS;

    private String lastName;

    private String firstName;

    private String startingDate;

    private String endDate;

    private double amountOfAssuranceAVS;

    private double amountOfAssuranceAC;

    private double amountOfAssuranceAF;

    private Long employeurId;
}
