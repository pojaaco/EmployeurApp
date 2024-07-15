package vn.elca.employer.client.model.view;

import lombok.*;
import vn.elca.employer.common.Fund;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployerView {
    private Long id;

    private Fund fund;

    private String number;

    private String name;

    private String numberIde;

    private String startDate;

    private String endDate;

    private Set<EmployeeView> employees;
}
