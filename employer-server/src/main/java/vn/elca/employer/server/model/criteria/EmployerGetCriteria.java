package vn.elca.employer.server.model.criteria;

import lombok.*;
import vn.elca.employer.common.Caisse;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerGetCriteria {
    private Caisse caisse;

    private String number;

    private String name;

    private String numberIDE;

    private LocalDate startingDate;

    private LocalDate endDate;
}
