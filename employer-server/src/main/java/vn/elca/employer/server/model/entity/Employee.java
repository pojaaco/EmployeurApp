package vn.elca.employer.server.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "EmployeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NumberAvs", length = 36)
    @NotNull
    private String numberAvs;

    @Column(name = "LastName")
    @NotNull
    private String lastName;

    @Column(name = "FirstName")
    @NotNull
    private String firstName;

    @Column(name = "StartDate")
    @NotNull
    private LocalDate startDate;

    @Column(name = "EndDate")
    @NotNull
    private LocalDate endDate;

    @Column(name = "AvsAiApg", precision = 17, scale = 2)
    @NotNull
    private BigDecimal avsAiApg;

    @Column(name = "Ac", precision = 17, scale = 2)
    @NotNull
    private BigDecimal ac;

    @Column(name = "Af", precision = 17, scale = 2)
    @NotNull
    private BigDecimal af;

    @ManyToOne
    @JoinColumn(name = "EmployerID")
    private Employer employer;
}
