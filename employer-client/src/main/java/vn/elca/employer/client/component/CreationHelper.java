package vn.elca.employer.client.component;

import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.jacpfx.rcp.components.toolBar.JACPToolBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.elca.employer.client.converter.EnumStringConverterFactory;
import vn.elca.employer.client.language.ObservableResourceFactory;
import vn.elca.employer.common.CommonConstants;
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
    private static final String BUNDLE_PROMPT = "Prompt";
    private static final String BUNDLE_PROMPT_FORMAT_DATE = "Prompt.Format.Date";
    private static final String BUNDLE_EN_SWITCHER = "Label.Switcher.english";
    private static final String BUNDLE_FR_SWITCHER = "Label.Switcher.french";
    private static final String CSS_CUSTOM_FONT = "custom-font";
    private static final String CSS_BLUE_TEXT = "blue-text";
    private static final String CSS_CLICKABLE = "clickable";
    private static final String CSS_BOLD_TEXT = "bold-text";
    private static final double SPACE_BETWEEN_SWITCHER = 8.0;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    public final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(CommonConstants.DATE_FORMAT);

    public void createFundComboBox(ComboBox<Fund> fundComboBox) {
        List<Fund> fundValue = Arrays.stream(Fund.values())
                .filter(e -> e != Fund.UNRECOGNIZED)
                .collect(Collectors.toList());
        fundComboBox.setMaxWidth(Double.MAX_VALUE);
        fundComboBox.setMaxHeight(Double.MAX_VALUE);
        fundComboBox.getItems().setAll(fundValue);
        fundComboBox.setValue(Fund.FUND_CANTONAL);
        fundComboBox.setConverter(EnumStringConverterFactory.getConverter(Fund.class, observableResourceFactory));

        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
            fundComboBox.getItems().setAll(fundValue); // Refresh Combo Box
        }));
    }

    public void createDatePicker(DatePicker datePicker) {
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setMaxHeight(Double.MAX_VALUE);
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
        datePicker.setPromptText(observableResourceFactory.getResources().getString(BUNDLE_PROMPT_FORMAT_DATE));

        datePicker.getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!Validator.isValidTypeDate(newValue)) {
                datePicker.getEditor().setText(oldValue);
            }
        }));

        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
            datePicker.setChronology(null); // Reset Chronology
            datePicker.setChronology(Chronology.ofLocale(observableResourceFactory.getResources().getLocale()));
            datePicker.setPromptText(observableResourceFactory.getResources().getString(BUNDLE_PROMPT_FORMAT_DATE));
        }));
    }

    public void createValidatedTextField(TextField textField, Predicate<String> predicate, String promptKey) {
        textField.setMaxWidth(Double.MAX_VALUE);
        textField.setMaxHeight(Double.MAX_VALUE);

        if (predicate != null) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!predicate.test(newValue)) {
                    textField.setText(oldValue);
                }
            });
        }

        if (promptKey != null) {
            textField.setPromptText(observableResourceFactory.getResources().getString(BUNDLE_PROMPT + "." + promptKey));
            observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
                textField.setPromptText(observableResourceFactory.getResources().getString(BUNDLE_PROMPT + "." + promptKey));
            }));
        }
    }

    public void addLanguageSwitcher(JACPToolBar toolBar) {
        HBox switcher = new HBox();
        switcher.setSpacing(SPACE_BETWEEN_SWITCHER);
        Label lblFr = new Label();
        lblFr.getStyleClass().addAll(CSS_CUSTOM_FONT, CSS_CLICKABLE, CSS_BOLD_TEXT);
        lblFr.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_FR_SWITCHER));
        lblFr.setOnMouseClicked(event -> observableResourceFactory.switchResourceByLanguage(ObservableResourceFactory.Language.FR));
        Label lblEn = new Label();
        lblEn.getStyleClass().addAll(CSS_CUSTOM_FONT, CSS_BLUE_TEXT, CSS_CLICKABLE, CSS_BOLD_TEXT);
        lblEn.textProperty().bind(observableResourceFactory.getStringBinding(BUNDLE_EN_SWITCHER));
        lblEn.setOnMouseClicked(event -> observableResourceFactory.switchResourceByLanguage(ObservableResourceFactory.Language.EN));

        observableResourceFactory.resourcesProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.getLocale() == ObservableResourceFactory.Language.EN.getLocale()) {
                lblEn.getStyleClass().remove(CSS_BLUE_TEXT);
                lblFr.getStyleClass().add(CSS_BLUE_TEXT);
            } else {
                lblEn.getStyleClass().add(CSS_BLUE_TEXT);
                lblFr.getStyleClass().remove(CSS_BLUE_TEXT);
            }
        }));

        switcher.getChildren().addAll(lblFr, new Separator(Orientation.VERTICAL), lblEn);
        toolBar.addAllOnEnd(switcher);
    }
}
