package vn.elca.employer.server.model.entity;

import lombok.*;
import vn.elca.employer.common.Fund;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Employer")
public class Employer {
    @Id
    @Column(name = "EmployerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Fund")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Fund fund;

    @Column(name = "Number", length = 50)
    @NotNull
    private String number;

    @Column(name = "Name")
    @NotNull
    private String name;

    @Column(name = "NumberIde", length = 36)
    @NotNull
    private String numberIde;

    @Column(name = "StartDate")
    @NotNull
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setEmployer(this);
    }
}
