package vn.elca.backend.model.entity;

import lombok.*;
import vn.elca.backend.util.generator.NumberGenerator;
import vn.elca.common.Caisse;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CAISSE")
    private Caisse caisse;

    @Column(name = "NUMBER_COUNT", length = 50)
    @Pattern(regexp = "\\d{6}",
            message = "Number must be exactly 6 digits.")
    private String number;

    @Column(name = "NAME", length = 255)
    private String name;

    @Column(name = "NUMBER_IDE", length = 36)
    @Pattern(regexp = "CHE/ADM-\\d{3}\\.\\d{3}\\.\\d{3}",
            message = "numberIDE must be in the format CHE/ADM-xxx.xxx.xxx where x is a digit.")
    private String numberIDE;

    @Column(name = "STARTING_DATE")
    private LocalDate startingDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @OneToMany(mappedBy = "employeur", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setEmployeur(this);
    }

//    @PrePersist
//    private void generateNumber() {
//        if (this.number == null) {
//            this.number = NumberGenerator.generateNumber();
//        }
//    }
}
