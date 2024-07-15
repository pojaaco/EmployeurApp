package vn.elca.employer.server.model.criteria;

import lombok.*;
import vn.elca.employer.common.Fund;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerGetCriteria {
    private Long id;

    private Fund fund;

    private String number;

    private String name;

    private String numberIde;

    private LocalDate startDate;

    private LocalDate endDate;
}
