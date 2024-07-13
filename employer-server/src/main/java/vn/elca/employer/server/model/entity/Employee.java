package vn.elca.employer.server.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_avs", length = 36)
    @NotNull
    private String numberAVS;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "starting_date")
    @NotNull
    private LocalDate startingDate;

    @Column(name = "end_date")
    @NotNull
    private LocalDate endDate;

    @Column(name = "amount_of_assurance_avs_ai_apg")
    @NotNull
    private double amountOfAssuranceAVS;

    @Column(name = "amount_of_assurance_ac")
    @NotNull
    private double amountOfAssuranceAC;

    @Column(name = "amount_of_assurance_af")
    @NotNull
    private double amountOfAssuranceAF;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;
}
