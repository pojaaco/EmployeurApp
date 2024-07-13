package vn.elca.employer.client.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerView {
    private Long id;

    private String caisse;

    private String number;

    private String name;

    private String numberIDE;

    private String startingDate;

    private String endDate;

    private Set<EmployeeView> employees;
}
