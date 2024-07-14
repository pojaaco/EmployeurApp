package vn.elca.employer.client.model.view;

import lombok.*;

import java.lang.reflect.Field;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    public void setMember(String memberName, String memberValue) {
        Field field;
        try {
            field = EmployeeView.class.getDeclaredField(memberName);
            field.setAccessible(true);

            Class<?> fieldType = field.getType();
            if (long.class.isAssignableFrom(fieldType)) {
                field.setLong(this, Long.parseLong(memberValue));
            } else if (double.class.isAssignableFrom(fieldType)) {
                field.setDouble(this, Double.parseDouble(memberValue));
            } else {
                field.set(this, memberValue);
            }

            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException | NumberFormatException ignored) {
        }
    }
}
