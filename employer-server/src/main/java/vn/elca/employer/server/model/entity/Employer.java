package vn.elca.employer.server.model.entity;

import lombok.*;
import vn.elca.employer.common.Caisse;

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
@Table(name = "Employer")
public class Employer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "caisse")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Caisse caisse;

    @Column(name = "number", length = 50)
    @NotNull
    private String number;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "number_ide", length = 36)
    @NotNull
    private String numberIDE;

    @Column(name = "starting_date")
    @NotNull
    private LocalDate startingDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setEmployer(this);
    }
}
