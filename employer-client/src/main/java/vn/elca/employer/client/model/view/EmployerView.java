package vn.elca.employer.client.model.view;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployerView {
    private Long id;

    private String fund;

    private String number;

    private String name;

    private String numberIde;

    private String startDate;

    private String endDate;

    private Set<EmployeeView> employees;
}
