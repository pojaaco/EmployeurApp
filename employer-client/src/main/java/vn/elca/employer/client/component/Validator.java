package vn.elca.employer.client.component;

import javafx.scene.Node;
import vn.elca.employer.client.model.view.EmployeeView;
import vn.elca.employer.client.model.view.EmployerView;

public class Validator {
    public static boolean isValidNumber(String value) {
        if (value == null) return true;
        return value.matches("\\d{0,6}");
    }

    public static boolean isValidNumberIde(String value) {
        if (value == null || value.length() == 0) return true;
        if (value.length() == 1) return value.equals("C") || value.equals("A");
        if (value.length() == 2) return value.equals("CH") || value.equals("AD");
        if (value.length() == 3) return value.equals("CHE") || value.equals("ADM");
        if (value.length() == 4) return value.endsWith("-");

        String code = value.substring(4);

        if (code.matches("\\d{0,3}(\\.\\d{0,3})?(\\.\\d{0,3})?")) {
            String[] parts = code.split("\\.");
            if (parts.length == 0) return false;
            if (parts[0].length() > 3) return false;
            if (parts.length > 1 && parts[1].length() > 3) return false;
            if (parts.length > 2 && parts[2].length() > 3) return false;
            return true;
        }

        return false;
    }

    public static boolean validateEmployerView(Node pane, EmployerView employerView) {
        boolean isValid = true;

        if (employerView.getName() == null) {
            pane.lookup("#error_name").setVisible(true);
            isValid = false;
        }
        if (employerView.getNumberIde() == null
                || !employerView.getNumberIde().matches("(ADM|CHE)-\\d{3}\\.\\d{3}\\.\\d{3}")) {
            pane.lookup("#error_numberIde").setVisible(true);
            isValid = false;
        }
        if (employerView.getStartDate() == null
                || !employerView.getStartDate().matches("(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d{2}")) {
            pane.lookup("#error_startDate").setVisible(true);
            isValid = false;
        }
        if (employerView.getEndDate() != null
                && !employerView.getEndDate().matches("(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d{2}")) {
            pane.lookup("#error_endDate").setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    public static boolean validateEmployeeView(Node pane, EmployeeView employeeView) {
        return true;
    }
}
