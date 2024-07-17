package vn.elca.employer.client.component;

import javafx.scene.Node;

public class Validator {
    private static final String CORRECT_NUMBER_FORMAT = "\\d{0,6}";
    private static final String CORRECT_DATE_FORMAT = "\\d{0,2}(\\.\\d{0,2})?(\\.\\d{0,4})?";
    private static final String DATE_DELIMINATOR = "\\.";

    public static boolean isValidTypedNumber(String value) {
        if (value == null) return true;
        return value.matches(CORRECT_NUMBER_FORMAT);
    }

    public static boolean isValidTypeDate(String text) {
        if (text.matches(CORRECT_DATE_FORMAT)) {
            String[] parts = text.split(DATE_DELIMINATOR);
            if (parts.length > 3) return false;
            if (parts.length > 0 && parts[0].length() > 2) return false; // Day part
            if (parts.length > 1 && parts[1].length() > 2) return false; // Month part
            if (parts.length > 2 && parts[2].length() > 4) return false; // Year part
            return true;
        }
        return false;
    }

    public static boolean isValidTypedNumberIde(String value) {
        if (value == null || value.length() == 0) return true;
        // TODO try to refactor with constant
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

    public static boolean checkCondition(boolean condition, Node errorLabel, Node inputField) {
        if (!condition) {
            if (errorLabel != null) {
                errorLabel.setVisible(true);
            }
            if (inputField != null) {
                // TODO Empty here
            }
            return false;
        }
        return true;
    }
}
