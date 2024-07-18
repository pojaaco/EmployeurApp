package vn.elca.employer.client.component;

import javafx.scene.Node;

public class Validator {
    private static final String CORRECT_NUMBER_FORMAT = "\\d{0,6}";
    private static final String CORRECT_DATE_FORMAT = "\\d{0,2}(\\.\\d{0,2})?(\\.\\d{0,4})?";
    private static final String DATE_DELIMINATOR = "\\.";
    private static final String CORRECT_PREFIX_NUMBER_IDE_FORMAT = "^(CHE-|ADM-|ADM|CHE|AD|CH|A|C)$";
    private static final String CORRECT_FULL_NUMBER_IDE_FORMAT = "^(CHE|ADM)-\\d{1,3}(\\.\\d{1,3})?(\\.\\d{0,3})?";
    private static final String NUMBER_IDE_DELIMINATOR = "\\.";
    private static final String CSS_RED_BORDER = "red-border";

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
        if (value.length() < 5) {
            if (!value.matches(CORRECT_PREFIX_NUMBER_IDE_FORMAT)) {
                return false;
            }
        } else {
            if (!value.matches(CORRECT_FULL_NUMBER_IDE_FORMAT)) {
                return false;
            }
            String code = value.substring(4);
            String[] parts = code.split(NUMBER_IDE_DELIMINATOR);
            for (String str : parts) {
                if (str.length() > 3) return false;
            }
        }
        return true;
    }

    public static boolean checkCondition(boolean condition, Node errorLabel, Node inputField) {
        if (!condition) {
            if (errorLabel != null) {
                errorLabel.setVisible(true);
            }
            if (inputField != null) {
                inputField.getStyleClass().add(CSS_RED_BORDER);
            }
            return false;
        }
        return true;
    }
}
