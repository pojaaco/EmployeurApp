package vn.elca.employer.client.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeView {
    private Long id;

    private String numberAVS;

    private String lastName;

    private String firstName;

    private String startingDate;

    private String endDate;

    private double amountOfAssuranceAVS;

    private double amountOfAssuranceAC;

    private double amountOfAssuranceAF;

    private Long employerId;
}
