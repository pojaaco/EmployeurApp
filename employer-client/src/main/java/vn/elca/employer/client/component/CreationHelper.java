package vn.elca.employer.client.component;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.jacpfx.rcp.components.toolBar.JACPToolBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.client.converter.EnumStringConverterFactory;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.common.ConstantContainer;
import vn.elca.employer.common.Fund;

import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class CreationHelper {
    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    public final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ConstantContainer.DATE_FORMAT);

    public void createFundComboBox(ComboBox<Fund> fundComboBox) {
        List<Fund> fundValue = Arrays.stream(Fund.values())
                .filter(e -> e != Fund.UNRECOGNIZED)
                .collect(Collectors.toList());
        fundComboBox.setMaxWidth(Double.MAX_VALUE);
        fundComboBox.getItems().setAll(fundValue);
        fundComboBox.setValue(Fund.FUND_CANTONAL);
        fundComboBox.setConverter(EnumStringConverterFactory.getConverter(Fund.class, observableResourceFactory));

        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
            fundComboBox.getItems().setAll(fundValue); // Refresh Combo Box
        }));
    }

    public void createDatePicker(DatePicker datePicker) {
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        datePicker.setPromptText(observableResourceFactory.getResources().getString("Prompt.Format.Date"));

        datePicker.getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}(\\.\\d{0,2})?(\\.\\d{0,4})?") || !isValidDate(newValue)) {
                datePicker.getEditor().setText(oldValue);
            }
        }));

        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
            datePicker.setChronology(null); // Reset Chronology
            datePicker.setChronology(Chronology.ofLocale(observableResourceFactory.getResources().getLocale()));
            datePicker.setPromptText(observableResourceFactory.getResources().getString("Prompt.Format.Date"));
        }));
    }

    private boolean isValidDate(String text) {
        if (text.matches("\\d{0,2}(\\.\\d{0,2})?(\\.\\d{0,4})?")) {
            String[] parts = text.split("\\.");
            if (parts.length > 3) return false;
            if (parts.length > 0 && parts[0].length() > 2) return false; // Day part
            if (parts.length > 1 && parts[1].length() > 2) return false; // Month part
            if (parts.length > 2 && parts[2].length() > 4) return false; // Year part
            return true;
        }
        return false;
    }

    public void createValidatedTextField(TextField textField, Predicate<String> predicate, String promptKey) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!predicate.test(newValue)) {
                textField.setText(oldValue);
            }
        });

        if (promptKey != null) {
            textField.setPromptText(observableResourceFactory.getResources().getString("Prompt." + promptKey));
            observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
                textField.setPromptText(observableResourceFactory.getResources().getString("Prompt." + promptKey));
            }));
        }
    }

    public void addLanguageSwitcher(JACPToolBar toolBar) { // TODO: Try to find another way to avoid duplicated code
        HBox switcher = new HBox();
        switcher.setSpacing(8);
        Label lblEn = new Label("EN");
        lblEn.setOnMouseClicked(event -> observableResourceFactory.switchResourceByLanguage(ObservableResourceFactory.Language.EN));
        Label lblFr = new Label("FR");
        lblFr.setOnMouseClicked(event -> observableResourceFactory.switchResourceByLanguage(ObservableResourceFactory.Language.FR));
        switcher.getChildren().addAll(lblEn, new Label("|"), lblFr);
        toolBar.addAllOnEnd(switcher);
    }
}
