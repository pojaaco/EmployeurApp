package vn.elca.backend.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.elca.common.Caisse;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeurSearchCriteria {
    private Caisse caisse;

    private String number;

    private String name;

    private String numberIDE;

    private LocalDate startingDate;

    private LocalDate endDate;
}
