package vn.elca.backend.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMBER_AVS", length = 36)
    @Pattern(regexp = "756\\.\\d{4}\\.\\d{4}\\.\\d{2}",
            message = "numberAVS must be in the format 756.xxxx.xxxx.xx where x is digit.")
    private String numberAVS;

    @Column(name = "LAST_NAME", length = 255)
    private String lastName;

    @Column(name = "FIRST_NAME", length = 255)
    private String firstName;

    @Column(name = "STARTING_DATE")
    private LocalDate startingDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "AMOUNT_OF_ASSURANCE_AVS")
    @Digits(integer = 17, fraction = 2)
    private double amountOfAssuranceAVS;

    @Column(name = "AMOUNT_OF_ASSURANCE_AC")
    @Digits(integer = 17, fraction = 2)
    private double amountOfAssuranceAC;

    @Column(name = "AMOUNT_OF_ASSURANCE_AF")
    @Digits(integer = 17, fraction = 2)
    private double amountOfAssuranceAF;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEUR_ID")
    private Employeur employeur;
}
