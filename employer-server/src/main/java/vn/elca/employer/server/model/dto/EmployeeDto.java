package vn.elca.employer.server.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {
    private Long id;

    private String numberAvs;

    private String lastName;

    private String firstName;

    private String startDate;

    private String endDate;

    private BigDecimal avsAiApg;

    private BigDecimal ac;

    private BigDecimal af;

    private Long employerId;
}
